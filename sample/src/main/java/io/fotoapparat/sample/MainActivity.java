package io.fotoapparat.sample;

import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.hardware.v2.Camera2;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.LensPositionSelectors.front;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class MainActivity extends AppCompatActivity {

	private Fotoapparat fotoapparat;
	private CameraView cameraView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cameraView = (CameraView) findViewById(R.id.camera_view);

		fotoapparat = Fotoapparat
				.with(this)
				.into(cameraView)
				.photoSize(biggestSize())
				.lensPosition(front())
				.build();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			try {
				camera();
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@SuppressWarnings("MissingPermission")
	private void camera() throws CameraAccessException {

		HandlerThread name = new HandlerThread("name");
		name.start();
		new Handler(name.getLooper())
				.post(new Runnable() {
					@Override
					public void run() {
						Camera2 camera = new Camera2(MainActivity.this);
						camera.open(null);

						cameraView.attachCamera(camera);
						camera.startPreview();
//						camera.takePicture();
					}
				});
	}

	@Override
	protected void onStart() {
		super.onStart();

		fotoapparat.start();
	}

	@Override
	protected void onStop() {
		super.onStop();

		fotoapparat.stop();
	}
}
