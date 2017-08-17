package io.fotoapparat.parameter.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.selector.Selectors;

import static io.fotoapparat.parameter.selector.AspectRatioSelectors.aspectRatio;

/**
 * Provides initial {@link Parameters} for {@link CameraDevice}.
 */
public class InitialParametersProvider {

    private final InitialParametersValidator parametersValidator;
    private final CapabilitiesOperator capabilitiesOperator;
    private final SelectorFunction<Size> photoSizeSelector;
    private final SelectorFunction<Size> previewSizeSelector;
    private final SelectorFunction<FocusMode> focusModeSelector;
    private final SelectorFunction<Flash> flashSelector;
    private final SelectorFunction<Range<Integer>> previewFpsRangeSelector;

    public InitialParametersProvider(CapabilitiesOperator capabilitiesOperator,
                                     SelectorFunction<Size> photoSizeSelector,
                                     SelectorFunction<Size> previewSizeSelector,
                                     SelectorFunction<FocusMode> focusModeSelector,
                                     SelectorFunction<Flash> flashSelector,
                                     SelectorFunction<Range<Integer>> previewFpsRangeSelector,
                                     InitialParametersValidator parametersValidator) {
        this.capabilitiesOperator = capabilitiesOperator;
        this.photoSizeSelector = photoSizeSelector;
        this.previewSizeSelector = previewSizeSelector;
        this.focusModeSelector = focusModeSelector;
        this.flashSelector = flashSelector;
        this.previewFpsRangeSelector = previewFpsRangeSelector;
        this.parametersValidator = parametersValidator;
    }

    /**
     * @return {@link Parameters} which will be used by {@link CameraDevice} on start-up.
     */
    public Parameters initialParameters() {
        Capabilities capabilities = capabilitiesOperator.getCapabilities();

        Parameters parameters = new Parameters();

        putPictureSize(capabilities, parameters);
        putPreviewSize(capabilities, parameters);
        putFocusMode(capabilities, parameters);
        putFlash(capabilities, parameters);
        putPreviewFpsRange(capabilities, parameters);

        parametersValidator.validate(parameters);

        return parameters;
    }

    private void putPreviewSize(Capabilities capabilities, Parameters parameters) {
        Size photoSize = photoSize(capabilities);

        parameters.putValue(
                Parameters.Type.PREVIEW_SIZE,
                Selectors
                        .firstAvailable(
                                previewWithSameAspectRatio(photoSize),
                                previewSizeSelector
                        )
                        .select(capabilities.supportedPreviewSizes())
        );
    }

    private SelectorFunction<Size> previewWithSameAspectRatio(Size photoSize) {
        return aspectRatio(
                photoSize.getAspectRatio(),
                previewSizeSelector
        );
    }

    private void putPictureSize(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.PICTURE_SIZE,
                photoSize(capabilities)
        );
    }

    private Size photoSize(Capabilities capabilities) {
        return photoSizeSelector.select(
                capabilities.supportedPictureSizes()
        );
    }

    private void putFocusMode(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.FOCUS_MODE,
                focusModeSelector.select(
                        capabilities.supportedFocusModes()
                )
        );
    }

    private void putFlash(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.FLASH,
                flashSelector.select(
                        capabilities.supportedFlashModes()
                )
        );
    }

    private void putPreviewFpsRange(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.PREVIEW_FPS_RANGE,
                previewFpsRangeSelector.select(
                        capabilities.supportedPreviewFpsRanges()
                )
        );
    }

}
