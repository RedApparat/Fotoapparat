package io.fotoapparat.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LogcatLogger;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.FlashSelectors.flash;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.focusMode;
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
                .focusMode(firstAvailable(
                        focusMode(FocusMode.CONTINUOUS_FOCUS),
                        focusMode(FocusMode.AUTO),
                        focusMode(FocusMode.FIXED)
                ))
                .flash(firstAvailable(
                        flash(Flash.AUTO_RED_EYE),
                        flash(Flash.AUTO),
                        flash(Flash.SINGLE)
                ))
                .logger(new LogcatLogger())
                .build();

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        fotoapparat
                .takePicture()
                .toBitmap()
                .whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
                    @Override
                    public void onResult(BitmapPhoto result) {
                        ImageView imageView = (ImageView) findViewById(R.id.result);

                        imageView.setImageBitmap(result.bitmap);
                    }
                });
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
