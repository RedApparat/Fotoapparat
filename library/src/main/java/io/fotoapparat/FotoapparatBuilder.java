package io.fotoapparat;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;
import io.fotoapparat.view.CameraRenderer;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

	public FotoapparatBuilder renderer(CameraRenderer renderer) {
		return this;
	}

    public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
        return this;
    }

    public FotoapparatBuilder lensPosition(SelectorFunction<LensPosition> selector) {
        return this;
    }

    public Fotoapparat build() {
        return new Fotoapparat(this);
    }

}
