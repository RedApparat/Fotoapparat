package io.fotoapparat;

import android.content.Context;

import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.hardware.provider.V1AvailableLensPositionProvider;
import io.fotoapparat.hardware.provider.V1Provider;
import io.fotoapparat.log.DummyLogger;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.parameter.selector.Selectors;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.view.CameraRenderer;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

	Context context;
	AvailableLensPositionsProvider availableLensPositionsProvider = new V1AvailableLensPositionProvider();
	CameraProvider cameraProvider = new V1Provider();
	CameraRenderer renderer;

	SelectorFunction<Size> photoSizeSelector;
	SelectorFunction<LensPosition> lensPositionSelector;
	SelectorFunction<FocusMode> focusModeSelector = Selectors.nothing();
	SelectorFunction<Flash> flashSelector = Selectors.nothing();

	FrameProcessor frameProcessor = null;

	Logger logger = new DummyLogger();

	FotoapparatBuilder(Context context) {
		this.context = context;
	}

	FotoapparatBuilder cameraProvider(CameraProvider cameraProvider) {
		this.cameraProvider = cameraProvider;
		return this;
	}

	public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
		photoSizeSelector = selector;
		return this;
	}

	public FotoapparatBuilder focusMode(SelectorFunction<FocusMode> selector) {
		focusModeSelector = selector;
		return this;
	}

	public FotoapparatBuilder flash(SelectorFunction<Flash> selector) {
		flashSelector = selector;
		return this;
	}

	public FotoapparatBuilder lensPosition(SelectorFunction<LensPosition> selector) {
		lensPositionSelector = selector;
		return this;
	}

	public FotoapparatBuilder frameProcessor(FrameProcessor frameProcessor) {
		this.frameProcessor = frameProcessor;
		return this;
	}

	public FotoapparatBuilder logger(Logger logger) {
		this.logger = logger;
		return this;
	}

	public FotoapparatBuilder into(CameraRenderer renderer) {
		this.renderer = renderer;
		return this;
	}

	public Fotoapparat build() {
		validate();

		return Fotoapparat.create(this);
	}

	private void validate() {
		if (renderer == null) {
			throw new IllegalStateException("CameraRenderer is mandatory.");
		}

		if (lensPositionSelector == null) {
			throw new IllegalStateException("LensPosition selector is mandatory.");
		}
	}

}
