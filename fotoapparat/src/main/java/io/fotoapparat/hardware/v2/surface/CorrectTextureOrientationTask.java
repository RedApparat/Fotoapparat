package io.fotoapparat.hardware.v2.surface;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.view.TextureView;

/**
 * Corrects the landscape orientation of the {@link TextureView}.
 */
class CorrectTextureOrientationTask implements Runnable {

    private final TextureView textureView;
    private final int screenOrientation;
    private final float width;
    private final float height;

    CorrectTextureOrientationTask(TextureView textureView, int screenOrientation) {
        this(textureView, screenOrientation, textureView.getWidth(), textureView.getHeight());
    }

    CorrectTextureOrientationTask(TextureView textureView,
                                  int screenOrientation,
                                  float width,
                                  float height) {
        this.textureView = textureView;
        this.screenOrientation = screenOrientation;
        this.width = width;
        this.height = height;
    }

    private static void correctRotatedDimensions(Matrix matrix,
                                                 float width,
                                                 float height,
                                                 float centerHorizontal,
                                                 float centerVertical) {
        float rotationCorrectionScaleHeight = height / width;
        float rotationCorrectionScaleWidth = width / height;

        matrix.postScale(
                rotationCorrectionScaleHeight,
                rotationCorrectionScaleWidth,
                centerHorizontal,
                centerVertical
        );
    }

    private static void correctRotation(Matrix matrix,
                                        int screenOrientation,
                                        float centerHorizontal,
                                        float centerVertical) {
        matrix.postRotate(360 - screenOrientation, centerHorizontal, centerVertical);
    }

    @Override
    public void run() {
        if (width == 0 || height == 0) {
            return;
        }

        final Matrix matrix = new Matrix();

        float centerHorizontal = width / 2;
        float centerVertical = height / 2;

        if (screenOrientation % 180 == 90) {
            correctRotatedDimensions(matrix, width, height, centerHorizontal, centerVertical);
        }

        correctRotation(matrix, screenOrientation, centerHorizontal, centerVertical);

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        textureView.setTransform(matrix);
                    }
                });
    }
}
