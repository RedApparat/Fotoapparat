package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.capabilities.SizeCapability;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.captor.routine.CapturingRoutine;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.parameters.ParametersManager;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;
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

		ParametersManager parametersManager = new ParametersManager();

		CameraSelector cameraSelector = new CameraSelector(manager);
		Characteristics characteristics = new Characteristics(manager);

		CameraConnection cameraConnection = new CameraConnection(manager,
				cameraSelector,
				characteristics
		);

		SizeCapability sizeCapability = new SizeCapability(characteristics);
		OrientationManager orientationManager = new OrientationManager(characteristics);
		CapabilitiesFactory capabilitiesFactory = new CapabilitiesFactory(characteristics);

		SurfaceReader surfaceReader = new SurfaceReader(sizeCapability);

		TextureManager textureManager = new TextureManager(orientationManager);

		CaptureRequestFactory captureRequestFactory = new CaptureRequestFactory(
				cameraConnection,
				surfaceReader,
				textureManager,
				parametersManager,
				characteristics
		);

		SessionManager sessionManager = new SessionManager(
				surfaceReader,
				cameraConnection,
				captureRequestFactory,
				textureManager
		);

		CapturingRoutine capturingRoutine = new CapturingRoutine(
				captureRequestFactory,
				surfaceReader
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
				capabilitiesFactory,
				pictureCaptor
		);
	}
}
