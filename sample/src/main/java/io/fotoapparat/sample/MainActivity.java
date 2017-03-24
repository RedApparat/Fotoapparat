package io.fotoapparat.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.fotoapparat.Fotoapparat;

import static io.fotoapparat.parameter.selector.LensPositionSelectors.front;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class MainActivity extends AppCompatActivity {

	private Fotoapparat fotoapparat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fotoapparat = Fotoapparat
				.with(this)
				.photoSize(biggestSize())
				.lensPosition(front())
				.build();
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
