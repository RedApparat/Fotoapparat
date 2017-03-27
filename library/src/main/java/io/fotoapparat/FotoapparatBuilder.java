package io.fotoapparat;

import io.fotoapparat.hardware.provider.CameraProvider;
import io.fotoapparat.hardware.provider.V1Provider;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

	CameraProvider cameraProvider = new V1Provider();
	CameraRenderer renderer;
	SelectorFunction<Size> photoSizeSelector;
	SelectorFunction<LensPosition> lensPositionSelector;

	FotoapparatBuilder() {
		// Do nothing
	}

	FotoapparatBuilder cameraProvider(CameraProvider cameraProvider) {
		this.cameraProvider = cameraProvider;
		return this;
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
