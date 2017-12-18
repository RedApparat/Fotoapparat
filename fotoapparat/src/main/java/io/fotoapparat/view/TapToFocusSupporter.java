package io.fotoapparat.view;

import android.graphics.Rect;

public interface TapToFocusSupporter {
    void enableTapToFocus(FocusCallback callback);
    void disableTapToFocus();

    interface FocusCallback {
        void onManualFocus(Rect cameraViewRect, float focusX, float focusY);
    }
}
