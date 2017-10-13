package io.fotoapparat.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import io.fotoapparat.R;

public class FocusMarkerLayout extends FrameLayout {

    private View mFocusMarkerView;

    public FocusMarkerLayout(@NonNull Context context) {
        this(context, null);
    }

    public FocusMarkerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_focus_marker_layout, this);

        mFocusMarkerView = findViewById(R.id.focus_marker_view);

        mFocusMarkerView.setAlpha(0);
    }

    public void showAt(float touchX, float touchY) {
        int x = (int) (touchX - mFocusMarkerView.getWidth() / 2);
        int y = (int) (touchY - mFocusMarkerView.getHeight() / 2);

        mFocusMarkerView.setTranslationX(x);
        mFocusMarkerView.setTranslationY(y);

        mFocusMarkerView.animate().setListener(null).cancel();

        mFocusMarkerView.setScaleX(0.8f);
        mFocusMarkerView.setScaleY(0.8f);
        mFocusMarkerView.setAlpha(1f);

        mFocusMarkerView.animate()
                .scaleX(1.2f).scaleY(1.2f).setStartDelay(0).setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mFocusMarkerView.animate().scaleX(1f).scaleY(1f).setDuration(200)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            mFocusMarkerView.animate().alpha(0).setStartDelay(750).setDuration(800).setListener(null).start();
                                        }
                                    }).start();
                        }
                }).start();
    }
}
