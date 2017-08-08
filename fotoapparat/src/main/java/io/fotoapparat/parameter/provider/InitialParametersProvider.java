package io.fotoapparat.parameter.provider;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.factory.ParametersFactory;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.selector.Selectors;

import static io.fotoapparat.parameter.Parameters.combineParameters;
import static io.fotoapparat.parameter.selector.AspectRatioSelectors.aspectRatio;
import static java.util.Arrays.asList;

/**
 * Provides initial {@link Parameters} for {@link CameraDevice}.
 */
public class InitialParametersProvider {

    private final ParametersFactory parametersFactory;
    private final InitialParametersValidator parametersValidator;
    private final CapabilitiesOperator capabilitiesOperator;
    private final SelectorFunction<Size> photoSizeSelector;
    private final SelectorFunction<Size> previewSizeSelector;
    private final SelectorFunction<FocusMode> focusModeSelector;
    private final SelectorFunction<Flash> flashSelector;

    public InitialParametersProvider(ParametersFactory parametersFactory,
                                     CapabilitiesOperator capabilitiesOperator,
                                     SelectorFunction<Size> photoSizeSelector,
                                     SelectorFunction<Size> previewSizeSelector,
                                     SelectorFunction<FocusMode> focusModeSelector,
                                     SelectorFunction<Flash> flashSelector,
                                     InitialParametersValidator parametersValidator) {
        this.parametersFactory = parametersFactory;
        this.capabilitiesOperator = capabilitiesOperator;
        this.photoSizeSelector = photoSizeSelector;
        this.previewSizeSelector = previewSizeSelector;
        this.focusModeSelector = focusModeSelector;
        this.flashSelector = flashSelector;
        this.parametersValidator = parametersValidator;
    }

    /**
     * @return function which selects a valid preview size based on current picture size.
     */
    static SelectorFunction<Size> validPreviewSizeSelector(Size photoSize,
                                                           SelectorFunction<Size> original) {
        return Selectors
                .firstAvailable(
                        previewWithSameAspectRatio(photoSize, original),
                        original
                );
    }

    private static SelectorFunction<Size> previewWithSameAspectRatio(Size photoSize,
                                                                     SelectorFunction<Size> original) {
        return aspectRatio(
                photoSize.getAspectRatio(),
                original
        );
    }

    /**
     * @return {@link Parameters} which will be used by {@link CameraDevice} on start-up.
     */
    public Parameters initialParameters() {
        Capabilities capabilities = capabilitiesOperator.getCapabilities();

        Parameters parameters = combineParameters(asList(
                pictureSizeParameters(capabilities),
                previewSizeParameters(capabilities),
                focusModeParameters(capabilities),
                flashModeParameters(capabilities)
        ));

        parametersValidator.validate(parameters);

        return parameters;
    }

    private Parameters flashModeParameters(Capabilities capabilities) {
        return parametersFactory.selectFlashMode(
                capabilities,
                flashSelector
        );
    }

    private Parameters focusModeParameters(Capabilities capabilities) {
        return parametersFactory.selectFocusMode(
                capabilities,
                focusModeSelector
        );
    }

    private Parameters previewSizeParameters(Capabilities capabilities) {
        return parametersFactory.selectPreviewSize(
                capabilities,
                validPreviewSizeSelector(
                        photoSize(capabilities),
                        previewSizeSelector
                )
        );
    }

    private Parameters pictureSizeParameters(Capabilities capabilities) {
        return parametersFactory.selectPictureSize(
                capabilities,
                photoSizeSelector
        );
    }

    private Size photoSize(Capabilities capabilities) {
        return photoSizeSelector.select(
                capabilities.supportedPictureSizes()
        );
    }

}
