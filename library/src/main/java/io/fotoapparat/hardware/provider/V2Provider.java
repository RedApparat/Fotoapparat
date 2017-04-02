package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.hardware.v2.CameraSelector;
import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.capabilities.SizeCapability;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.log.Logger;

/**
 * Always provides {@link Camera2}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class V2Provider implements CameraProvider {

	private Context context;

	// TODO: 31/03/17 try remove context?
	public V2Provider(Context context) {
		this.context = context;
	}

	@Override
	public CameraDevice get(Logger logger) {

		CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

		CameraSelector cameraSelector = new CameraSelector(manager);

		CameraCapabilities cameraCapabilities = new CameraCapabilities(manager);

		SizeCapability sizeCapability = new SizeCapability(cameraCapabilities);
		SurfaceReader surfaceReader = new SurfaceReader(sizeCapability);

		CameraConnection cameraConnection = new CameraConnection(manager, cameraCapabilities);

		PictureCaptor pictureCaptor = new PictureCaptor(surfaceReader, cameraConnection);

		io.fotoapparat.hardware.v2.CameraManager cameraManager = new io.fotoapparat.hardware.v2.CameraManager(
				cameraSelector,
				cameraConnection,
				surfaceReader,
				pictureCaptor
		);

		return new Camera2(cameraManager);
	}
}
