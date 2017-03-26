package io.fotoapparat;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

	CameraRenderer renderer;
	SelectorFunction<Size> photoSizeSelector;
	SelectorFunction<LensPosition> lensPositionSelector;

	FotoapparatBuilder() {
		// Do nothing
	}

	public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
		photoSizeSelector = selector;
		return this;
	}

	public FotoapparatBuilder lensPosition(SelectorFunction<LensPosition> selector) {
		lensPositionSelector = selector;
		return this;
	}

	public FotoapparatBuilder into(CameraRenderer renderer) {
		this.renderer = renderer;
		return this;
	}

	public Fotoapparat build() {
		validate();

		return new Fotoapparat(this);
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
