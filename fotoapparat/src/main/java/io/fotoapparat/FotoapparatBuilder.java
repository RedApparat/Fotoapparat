package io.fotoapparat;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import io.fotoapparat.error.CameraErrorCallback;
import java.util.Collection;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.log.Logger;
import io.fotoapparat.log.Loggers;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.selector.FlashSelectors;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.selector.Selectors;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.view.CameraRenderer;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.hardware.provider.CameraProviders.v1;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.external;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.front;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

    Context context;
    CameraProvider cameraProvider = v1();
    CameraRenderer renderer;

    SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector = firstAvailable(
            back(),
            front(),
            external()
    );
    SelectorFunction<Collection<Size>, Size> photoSizeSelector = biggestSize();
    SelectorFunction<Collection<Size>, Size> previewSizeSelector = biggestSize();
    SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector = firstAvailable(
            continuousFocus(),
            autoFocus(),
            fixed()
    );
    SelectorFunction<Collection<Flash>, Flash> flashSelector = FlashSelectors.off();
    SelectorFunction<Collection<Range<Integer>>, Range<Integer>> previewFpsRangeSelector = Selectors.nothing();
    SelectorFunction<Range<Integer>, Integer> sensorSensitivitySelector = Selectors.nothing();

    Integer jpegQuality;

    ScaleType scaleType = ScaleType.CENTER_CROP;

    FrameProcessor frameProcessor = null;

    Logger logger = Loggers.none();

    CameraErrorCallback cameraErrorCallback = CameraErrorCallback.NULL;

    FotoapparatBuilder(@NonNull Context context) {
        this.context = context;
    }

    /**
     * @param cameraProvider decides which {@link CameraDevice} to use.
     */
    public FotoapparatBuilder cameraProvider(@NonNull CameraProvider cameraProvider) {
        this.cameraProvider = cameraProvider;
        return this;
    }

    /**
     * @param selector selects size of the photo (in pixels) from list of available sizes.
     */
    public FotoapparatBuilder photoSize(@NonNull SelectorFunction<Collection<Size>, Size> selector) {
        photoSizeSelector = selector;
        return this;
    }

    /**
     * @param selector selects size of preview stream (in pixels) from list of available sizes.
     */
    public FotoapparatBuilder previewSize(@NonNull SelectorFunction<Collection<Size>, Size> selector) {
        previewSizeSelector = selector;
        return this;
    }

    /**
     * @param scaleType of preview inside the view.
     */
    public FotoapparatBuilder previewScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    /**
     * @param selector selects focus mode from list of available modes.
     */
    public FotoapparatBuilder focusMode(@NonNull SelectorFunction<Collection<FocusMode>, FocusMode> selector) {
        focusModeSelector = selector;
        return this;
    }

    /**
     * @param selector selects flash mode from list of available modes.
     */
    public FotoapparatBuilder flash(@NonNull SelectorFunction<Collection<Flash>, Flash> selector) {
        flashSelector = selector;
        return this;
    }

    /**
     * @param selector camera sensor position from list of available positions.
     */
    public FotoapparatBuilder lensPosition(@NonNull SelectorFunction<Collection<LensPosition>, LensPosition> selector) {
        lensPositionSelector = selector;
        return this;
    }

    /**
     * @param selector selects preview FPS range from list of available ranges.
     */
    public FotoapparatBuilder previewFpsRange(@NonNull SelectorFunction<Collection<Range<Integer>>, Range<Integer>> selector) {
        previewFpsRangeSelector = selector;
        return this;
    }

    /**
     * @param selector selects ISO value from range of available values.
     */
    public FotoapparatBuilder sensorSensitivity(@NonNull SelectorFunction<Range<Integer>, Integer> selector) {
        sensorSensitivitySelector = selector;
        return this;
    }

    /**
     * @param jpegQuality of the picture (1-100)
     */
    public FotoapparatBuilder jpegQuality(@IntRange(from=0,to=100) @NonNull Integer jpegQuality) {
        this.jpegQuality = jpegQuality;
        return this;
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessor
     */
    public FotoapparatBuilder frameProcessor(@NonNull FrameProcessor frameProcessor) {
        this.frameProcessor = frameProcessor;
        return this;
    }

    /**
     * @param logger logger which will print logs. No logger is set by default.
     * @see Loggers
     */
    public FotoapparatBuilder logger(@NonNull Logger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * @param callback which will be notified when camera error happens in Fotoapparat.
     * @see CameraErrorCallback
     */
    public FotoapparatBuilder cameraErrorCallback(@NonNull CameraErrorCallback callback) {
        this.cameraErrorCallback = callback;
        return this;
    }

    /**
     * @param renderer view which will draw the stream from the camera.
     * @see CameraView
     */
    public FotoapparatBuilder into(@NonNull CameraRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    /**
     * @return set up instance of {@link Fotoapparat}.
     * @throws IllegalStateException if some mandatory parameters are not specified.
     */
    public Fotoapparat build() {
        validate();

        return Fotoapparat.create(this);
    }

    private void validate() {
        if (cameraProvider == null) {
            throw new IllegalStateException("CameraProvider is mandatory.");
        }

        if (renderer == null) {
            throw new IllegalStateException("CameraRenderer is mandatory.");
        }

        if (lensPositionSelector == null) {
            throw new IllegalStateException("LensPosition selector is mandatory.");
        }

        if (photoSizeSelector == null) {
            throw new IllegalStateException("Photo size selector is mandatory.");
        }
    }

}
