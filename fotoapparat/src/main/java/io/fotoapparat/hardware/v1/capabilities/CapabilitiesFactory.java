package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.Camera1;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;

/**
 * {@link Capabilities} of {@link Camera1}.
 */
@SuppressWarnings("deprecation")
public class CapabilitiesFactory {

    /**
     * @return {@link Capabilities} from given camera parameters.
     */
    public Capabilities fromParameters(Camera.Parameters parameters) {
        return new Capabilities(
                extractPictureSizes(parameters),
                extractPreviewSizes(parameters),
                extractFocusModes(parameters),
                extractFlashModes(parameters),
                extractPreviewFpsRanges(parameters)
        );
    }

    private Set<Size> extractPreviewSizes(Camera.Parameters parameters) {
        return mapSizes(parameters.getSupportedPreviewSizes());
    }

    private Set<Size> extractPictureSizes(Camera.Parameters parameters) {
        return mapSizes(parameters.getSupportedPictureSizes());
    }

    private Set<Size> mapSizes(Collection<Camera.Size> sizes) {
        HashSet<Size> result = new HashSet<>();

        for (Camera.Size size : sizes) {
            result.add(new Size(
                    size.width,
                    size.height
            ));
        }

        return result;
    }

    private Set<Flash> extractFlashModes(Camera.Parameters parameters) {
        HashSet<Flash> result = new HashSet<>();

        for (String flashMode : supportedFlashModes(parameters)) {
            result.add(
                    FlashCapability.toFlash(flashMode)
            );
        }

        return result;
    }

    @NonNull
    private List<String> supportedFlashModes(Camera.Parameters parameters) {
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        return supportedFlashModes != null
                ? supportedFlashModes
                : Collections.singletonList(Camera.Parameters.FLASH_MODE_OFF);
    }

    private Set<FocusMode> extractFocusModes(Camera.Parameters parameters) {
        HashSet<FocusMode> result = new HashSet<>();

        for (String focusMode : parameters.getSupportedFocusModes()) {
            result.add(
                    FocusCapability.toFocusMode(focusMode)
            );
        }

        result.add(FocusMode.FIXED);
        return result;
    }

    private Set<Range<Integer>> extractPreviewFpsRanges(Camera.Parameters parameters) {
        List<int[]> fpsRanges = parameters.getSupportedPreviewFpsRange();
        if (fpsRanges == null) {
            return Collections.emptySet();
        }

        Set<Range<Integer>> wrappedFpsRanges = new HashSet<>(fpsRanges.size());
        for (int[] range : fpsRanges) {
            wrappedFpsRanges.add(Ranges.range(
                    range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX],
                    range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]
            ));
        }
        return wrappedFpsRanges;
    }

}
