package io.fotoapparat.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LogcatLogger;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.front;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class MainActivity extends AppCompatActivity {

	private Fotoapparat fotoapparat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CameraView cameraView = (CameraView) findViewById(R.id.camera_view);

		fotoapparat = Fotoapparat
				.with(this)
				.into(cameraView)
				.photoSize(biggestSize())
				.lensPosition(firstAvailable(
						front(),
						back()
				))
				.logger(new LogcatLogger())
				.build();
	}

	@Override
	protected void onResume() {
		super.onResume();

		fotoapparat.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

		fotoapparat.stop();
	}

}
