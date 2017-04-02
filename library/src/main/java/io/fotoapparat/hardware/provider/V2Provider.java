package io.fotoapparat.hardware.provider;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.v2.Camera2;
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
		return new Camera2(context);
	}
}
