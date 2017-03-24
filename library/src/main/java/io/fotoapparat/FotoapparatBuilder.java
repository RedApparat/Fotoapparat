package io.fotoapparat;

import io.fotoapparat.parameter.Side;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

    public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
        return this;
    }

    public FotoapparatBuilder cameraSide(SelectorFunction<Side> selector) {
        return this;
    }

    public Fotoapparat build() {
        return new Fotoapparat(this);
    }

}
