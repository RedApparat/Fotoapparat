package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.captor.CapturingRoutine;
import io.fotoapparat.hardware.v2.captor.FocusRoutine;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.hardware.v2.parameters.RendererParametersProvider;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.stream.PreviewStream2;
import io.fotoapparat.hardware.v2.surface.ContinuousSurfaceReader;
import io.fotoapparat.hardware.v2.surface.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.log.Logger;

/**
 * Always provides {@link Camera2}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class V2Provider implements CameraProvider {

	private final CameraManager manager;

	public V2Provider(Context context) {
		manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
	}

	@Override
	public CameraDevice get(Logger logger) {

		CameraSelector cameraSelector = new CameraSelector(manager);
		Characteristics characteristics = new Characteristics(manager);

		CameraConnection cameraConnection = new CameraConnection(manager,
				cameraSelector,
				characteristics
		);

		OrientationManager orientationManager = new OrientationManager(characteristics);

		ParametersProvider parametersProvider = new ParametersProvider();

		TextureManager textureManager = new TextureManager(
				orientationManager,
				parametersProvider
		);

		StillSurfaceReader stillSurfaceReader = new StillSurfaceReader(parametersProvider);
		ContinuousSurfaceReader continuousSurfaceReader = new ContinuousSurfaceReader(
				parametersProvider
		);

		CaptureRequestFactory captureRequestFactory = new CaptureRequestFactory(
				cameraConnection,
				stillSurfaceReader,
				continuousSurfaceReader,
				textureManager,
				parametersProvider,
				characteristics
		);

		SessionManager sessionManager = new SessionManager(
				stillSurfaceReader,
				continuousSurfaceReader,
				cameraConnection,
				captureRequestFactory,
				textureManager
		);

		CapabilitiesFactory capabilitiesOperator = new CapabilitiesFactory(characteristics);

		CapturingRoutine capturingRoutine = new CapturingRoutine(
				captureRequestFactory,
				stillSurfaceReader,
				sessionManager,
				orientationManager
		);

		PreviewStream2 previewStream = new PreviewStream2(
				continuousSurfaceReader,
				parametersProvider
		);

		RendererParametersProvider rendererParametersOperator = new RendererParametersProvider(
				parametersProvider,
				orientationManager
		);

		FocusRoutine focusRoutine = new FocusRoutine(
				captureRequestFactory,
				sessionManager
		);

		return new Camera2(
				logger,
				cameraConnection,
				sessionManager,
				textureManager,
				orientationManager,
				parametersProvider,
				capabilitiesOperator,
				capturingRoutine,
				previewStream,
				rendererParametersOperator,
				focusRoutine
		);
	}
}
