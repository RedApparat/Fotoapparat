package io.fotoapparat.request;

import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Builder for {@link PhotoRequest}.
 */
public class PhotoRequestBuilder {

    public PhotoRequestBuilder flash(SelectorFunction<LensPosition> selector) {
        return this;
    }

    public PhotoRequest build() {
        return new PhotoRequest(this);
    }

}
