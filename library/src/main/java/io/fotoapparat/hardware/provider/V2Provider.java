package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.captor.routine.CapturingRoutine;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersManager;
import io.fotoapparat.hardware.v2.parameters.SizeProvider;
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

	// TODO: 31/03/17 try remove context?
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

		ParametersManager parametersManager = new ParametersManager();

		SizeProvider sizeProvider = new SizeProvider(parametersManager);

		TextureManager textureManager = new TextureManager(
				orientationManager,
				sizeProvider
		);

		StillSurfaceReader stillSurfaceReader = new StillSurfaceReader(sizeProvider);
		ContinuousSurfaceReader continuousSurfaceReader = new ContinuousSurfaceReader(sizeProvider);

		CaptureRequestFactory captureRequestFactory = new CaptureRequestFactory(
				cameraConnection,
				stillSurfaceReader,
				continuousSurfaceReader,
				textureManager,
				parametersManager,
				characteristics
		);

		SessionManager sessionManager = new SessionManager(
				stillSurfaceReader,
				continuousSurfaceReader,
				cameraConnection,
				captureRequestFactory,
				textureManager
		);

		CapturingRoutine capturingRoutine = new CapturingRoutine(
				captureRequestFactory,
				stillSurfaceReader
		);

		PictureCaptor pictureCaptor = new PictureCaptor(
				sessionManager,
				capturingRoutine,
				orientationManager
		);

		return new Camera2(
				cameraConnection,
				sessionManager,
				textureManager,
				orientationManager,
				parametersManager,
				new CapabilitiesFactory(characteristics),
				pictureCaptor,
				new PreviewStream2(continuousSurfaceReader)
		);
	}
}
