package io.fotoapparat.parameter.provider;

import java.util.Collection;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.factory.ParametersFactory;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.Selectors;

import static io.fotoapparat.parameter.Parameters.combineParameters;
import static io.fotoapparat.parameter.selector.AspectRatioSelectors.aspectRatio;
import static java.util.Arrays.asList;

/**
 * Provides initial {@link Parameters} for {@link CameraDevice}.
 */
public class InitialParametersProvider {

    private final InitialParametersValidator parametersValidator;
    private final CapabilitiesOperator capabilitiesOperator;
    private final SelectorFunction<Collection<Size>, Size> photoSizeSelector;
    private final SelectorFunction<Collection<Size>, Size> previewSizeSelector;
    private final SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector;
    private final SelectorFunction<Collection<Flash>, Flash> flashSelector;
    private final SelectorFunction<Collection<Range<Integer>>, Range<Integer>> previewFpsRangeSelector;
    private final SelectorFunction<Range<Integer>, Integer> sensorSensitivitySelector;
    private final Integer jpegQuality;

    public InitialParametersProvider(CapabilitiesOperator capabilitiesOperator,
                                     SelectorFunction<Collection<Size>, Size> photoSizeSelector,
                                     SelectorFunction<Collection<Size>, Size> previewSizeSelector,
                                     SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector,
                                     SelectorFunction<Collection<Flash>, Flash> flashSelector,
                                     SelectorFunction<Collection<Range<Integer>>, Range<Integer>> previewFpsRangeSelector,
                                     SelectorFunction<Range<Integer>, Integer> sensorSensitivitySelector,
                                     Integer jpegQuality,
                                     InitialParametersValidator parametersValidator) {
        this.capabilitiesOperator = capabilitiesOperator;
        this.photoSizeSelector = photoSizeSelector;
        this.previewSizeSelector = previewSizeSelector;
        this.focusModeSelector = focusModeSelector;
        this.flashSelector = flashSelector;
        this.previewFpsRangeSelector = previewFpsRangeSelector;
        this.sensorSensitivitySelector = sensorSensitivitySelector;
        this.jpegQuality = jpegQuality;
        this.parametersValidator = parametersValidator;
    }

    /**
     * @return function which selects a valid preview size based on current picture size.
     */
    static SelectorFunction<Collection<Size>, Size> validPreviewSizeSelector(Size photoSize,
                                                                             SelectorFunction<Collection<Size>, Size> original) {
        return Selectors
                .firstAvailable(
                        previewWithSameAspectRatio(photoSize, original),
                        original
                );
    }

    private static SelectorFunction<Collection<Size>, Size> previewWithSameAspectRatio(Size photoSize,
                                                                                       SelectorFunction<Collection<Size>, Size> original) {
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
                flashModeParameters(capabilities),
                previewFpsRange(capabilities),
                sensorSensitivity(capabilities),
                jpegQuality()
        ));

        parametersValidator.validate(parameters);

        return parameters;
    }

    private Parameters flashModeParameters(Capabilities capabilities) {
        return ParametersFactory.selectFlashMode(
                capabilities,
                flashSelector
        );
    }

    private Parameters focusModeParameters(Capabilities capabilities) {
        return ParametersFactory.selectFocusMode(
                capabilities,
                focusModeSelector
        );
    }

    private SelectorFunction<Collection<Size>, Size> previewWithSameAspectRatio(Size photoSize) {
        return aspectRatio(
                photoSize.getAspectRatio(),
                previewSizeSelector
        );
    }

    private Parameters previewSizeParameters(Capabilities capabilities) {
        return ParametersFactory.selectPreviewSize(
                capabilities,
                validPreviewSizeSelector(
                        photoSize(capabilities),
                        previewSizeSelector
                )
        );
    }

    private Parameters pictureSizeParameters(Capabilities capabilities) {
        return ParametersFactory.selectPictureSize(
                capabilities,
                photoSizeSelector
        );
    }

    private Size photoSize(Capabilities capabilities) {
        return photoSizeSelector.select(
                capabilities.supportedPictureSizes()
        );
    }

    private Parameters previewFpsRange(Capabilities capabilities) {
        return ParametersFactory.selectPreviewFpsRange(
                capabilities,
                previewFpsRangeSelector
        );
    }

    private Parameters sensorSensitivity(Capabilities capabilities) {
        return ParametersFactory.selectSensorSensitivity(
                capabilities,
                sensorSensitivitySelector
        );
    }

    private Parameters jpegQuality() {
        return ParametersFactory.selectJpegQuality(
                jpegQuality
        );
    }

}
