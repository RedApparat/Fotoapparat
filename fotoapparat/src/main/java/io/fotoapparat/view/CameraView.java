package io.fotoapparat.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.ScaleType;

/**
 * Displays stream from camera.
 */
public class CameraView extends FrameLayout implements CameraRenderer, TapToFocusSupporter {

    private TextureRendererView rendererView;
    private FocusMarkerLayout focusMarkerLayout;

    private boolean isTapToFocusEnabled = false;

    public CameraView(@NonNull Context context) {
        super(context);

        init();
    }

    public CameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CameraView(@NonNull Context context,
                      @Nullable AttributeSet attrs,
                      @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraView(@NonNull Context context,
                      @Nullable AttributeSet attrs,
                      @AttrRes int defStyleAttr,
                      @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        rendererView = new TextureRendererView(getContext());
        addView(rendererView);

        focusMarkerLayout = new FocusMarkerLayout(getContext());
        focusMarkerLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && isTapToFocusEnabled) {
                    focusMarkerLayout.showAt(motionEvent.getX(), motionEvent.getY());
                    rendererView.dispatchTouchEvent(motionEvent);
                    return true;
                }
                return false;
            }
        });
        addView(focusMarkerLayout);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        rendererView.setScaleType(scaleType);
    }

    @Override
    public void attachCamera(CameraDevice camera) {
        rendererView.attachCamera(camera);
    }

    @Override
    public void enableTapToFocus(FocusCallback callback) {
        isTapToFocusEnabled = true;
        rendererView.enableTapToFocus(callback);
    }

    @Override
    public void disableTapToFocus() {
        rendererView.disableTapToFocus();
        isTapToFocusEnabled = false;
    }
}
