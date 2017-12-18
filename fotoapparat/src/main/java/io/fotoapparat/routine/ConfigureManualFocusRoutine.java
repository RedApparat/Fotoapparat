package io.fotoapparat.routine;

import io.fotoapparat.view.TapToFocusSupporter;

public class ConfigureManualFocusRoutine {

    private final TapToFocusSupporter tapToFocusSupporter;
    private final TapToFocusSupporter.FocusCallback tapToFocusCallback;

    public ConfigureManualFocusRoutine(TapToFocusSupporter tapToFocusSupporter,
                                       TapToFocusSupporter.FocusCallback tapToFocusCallback) {
        this.tapToFocusSupporter = tapToFocusSupporter;
        this.tapToFocusCallback = tapToFocusCallback;
    }

    public void setEnabled(boolean isEnabled) {
        if (isEnabled) {
            tapToFocusSupporter.enableTapToFocus(tapToFocusCallback);
        } else {
            tapToFocusSupporter.disableTapToFocus();
        }
    }
}
