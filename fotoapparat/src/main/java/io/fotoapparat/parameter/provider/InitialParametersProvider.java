package io.fotoapparat.parameter.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.Size;
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
    private final SelectorFunction<ScaleType> previewScaleTypeSelector;
    private final SelectorFunction<FocusMode> focusModeSelector;
    private final SelectorFunction<Flash> flashSelector;

    public InitialParametersProvider(CapabilitiesOperator capabilitiesOperator,
                                     SelectorFunction<Size> photoSizeSelector,
                                     SelectorFunction<Size> previewSizeSelector,
                                     SelectorFunction<ScaleType> previewScaleTypeSelector,
                                     SelectorFunction<FocusMode> focusModeSelector,
                                     SelectorFunction<Flash> flashSelector,
                                     InitialParametersValidator parametersValidator) {
        this.capabilitiesOperator = capabilitiesOperator;
        this.photoSizeSelector = photoSizeSelector;
        this.previewSizeSelector = previewSizeSelector;
        this.previewScaleTypeSelector = previewScaleTypeSelector;
        this.focusModeSelector = focusModeSelector;
        this.flashSelector = flashSelector;
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
        putPreviewScaleType(capabilities, parameters);
        putFocusMode(capabilities, parameters);
        putFlash(capabilities, parameters);

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

    private void putPreviewScaleType(Capabilities capabilities, Parameters parameters) {
        parameters.putValue(
                Parameters.Type.PREVIEW_SCALE_TYPE,
                previewScaleType(capabilities)
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

    private ScaleType previewScaleType(Capabilities capabilities) {
        return previewScaleTypeSelector.select(
                capabilities.supportedPreviewScaleTypes()
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

}
