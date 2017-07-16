package io.fotoapparat;

import android.content.Context;
import android.support.annotation.NonNull;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.log.Logger;
import io.fotoapparat.log.Loggers;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.FlashSelectors;
import io.fotoapparat.parameter.selector.SelectorFunction;
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
import static io.fotoapparat.parameter.selector.ScaleTypeSelectors.centerCropped;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

    Context context;
    CameraProvider cameraProvider = v1();
    CameraRenderer renderer;

    SelectorFunction<LensPosition> lensPositionSelector = firstAvailable(
            back(),
            front(),
            external()
    );
    SelectorFunction<Size> photoSizeSelector = biggestSize();
    SelectorFunction<Size> previewSizeSelector = biggestSize();
    SelectorFunction<FocusMode> focusModeSelector = firstAvailable(
            continuousFocus(),
            autoFocus(),
            fixed()
    );
    SelectorFunction<Flash> flashSelector = FlashSelectors.off();

    ScaleType scaleType = centerCropped();

    FrameProcessor frameProcessor = null;

    Logger logger = Loggers.none();

    FotoapparatBuilder(@NonNull Context context) {
        this.context = context;
    }

    /**
     * @param cameraProvider decides which {@link CameraDevice} to use.
     */
    public FotoapparatBuilder cameraProvider(CameraProvider cameraProvider) {
        this.cameraProvider = cameraProvider;
        return this;
    }

    /**
     * @param selector selects size of the photo (in pixels) from list of available sizes.
     */
    public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
        photoSizeSelector = selector;
        return this;
    }

    /**
     * @param selector selects size of preview stream (in pixels) from list of available sizes.
     */
    public FotoapparatBuilder previewSize(SelectorFunction<Size> selector) {
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
    public FotoapparatBuilder focusMode(SelectorFunction<FocusMode> selector) {
        focusModeSelector = selector;
        return this;
    }

    /**
     * @param selector selects flash mode from list of available modes.
     */
    public FotoapparatBuilder flash(SelectorFunction<Flash> selector) {
        flashSelector = selector;
        return this;
    }

    /**
     * @param selector camera sensor position from list of available positions.
     */
    public FotoapparatBuilder lensPosition(SelectorFunction<LensPosition> selector) {
        lensPositionSelector = selector;
        return this;
    }

    /**
     * @param frameProcessor receives preview frames for processing.
     * @see FrameProcessor
     */
    public FotoapparatBuilder frameProcessor(FrameProcessor frameProcessor) {
        this.frameProcessor = frameProcessor;
        return this;
    }

    /**
     * @param logger logger which will print logs. No logger is set by default.
     * @see Loggers
     */
    public FotoapparatBuilder logger(Logger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * @param renderer view which will draw the stream from the camera.
     * @see CameraView
     */
    public FotoapparatBuilder into(CameraRenderer renderer) {
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
