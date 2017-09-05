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
import io.fotoapparat.hardware.v1.CameraParametersDecorator;
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
    public Capabilities fromParameters(CameraParametersDecorator parametersProvider) {
        return new Capabilities(
                extractPictureSizes(parametersProvider),
                extractPreviewSizes(parametersProvider),
                extractFocusModes(parametersProvider),
                extractFlashModes(parametersProvider),
                extractPreviewFpsRanges(parametersProvider),
                extractSensorSensitivityRange(parametersProvider),
                parametersProvider.isZoomSupported()
        );
    }

    private Set<Size> extractPreviewSizes(CameraParametersDecorator parametersProvider) {
        return mapSizes(
                parametersProvider.getSupportedPreviewSizes()
        );
    }

    private Set<Size> extractPictureSizes(CameraParametersDecorator parametersProvider) {
        return mapSizes(
                parametersProvider.getSupportedPictureSizes()
        );
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

    private Set<Flash> extractFlashModes(CameraParametersDecorator parameters) {
        HashSet<Flash> result = new HashSet<>();

        for (String flashMode : supportedFlashModes(parameters)) {
            result.add(
                    FlashCapability.toFlash(flashMode)
            );
        }

        return result;
    }

    @NonNull
    private List<String> supportedFlashModes(CameraParametersDecorator parametersProvider) {
        List<String> supportedFlashModes = parametersProvider.getSupportedFlashModes();
        return supportedFlashModes != null
                ? supportedFlashModes
                : Collections.singletonList(Camera.Parameters.FLASH_MODE_OFF);
    }

    private Set<FocusMode> extractFocusModes(CameraParametersDecorator parametersProvider) {
        HashSet<FocusMode> result = new HashSet<>();

        for (String focusMode : parametersProvider.getSupportedFocusModes()) {
            result.add(
                    FocusCapability.toFocusMode(focusMode)
            );
        }

        result.add(FocusMode.FIXED);
        return result;
    }

    private Set<Range<Integer>> extractPreviewFpsRanges(CameraParametersDecorator parametersProvider) {
        List<int[]> fpsRanges = parametersProvider.getSupportedPreviewFpsRange();

        if (fpsRanges == null) {
            return Collections.emptySet();
        }

        Set<Range<Integer>> wrappedFpsRanges = new HashSet<>(fpsRanges.size());
        for (int[] range : fpsRanges) {
            wrappedFpsRanges.add(Ranges.continuousRange(
                    range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX],
                    range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]
            ));
        }
        return wrappedFpsRanges;
    }

    @NonNull
	private Range<Integer> extractSensorSensitivityRange(CameraParametersDecorator parametersProvider) {
		final Set<Integer> isoValuesSet = parametersProvider.getSensorSensitivityValues();
        return Ranges.discreteRange(isoValuesSet);
	}

}
