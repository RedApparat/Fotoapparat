package io.fotoapparat;

import java.util.concurrent.ExecutorService;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Builder for {@link Fotoapparat}.
 */
public class FotoapparatBuilder {

	public FotoapparatBuilder executorService(ExecutorService executor) {
		return this;
	}

	public FotoapparatBuilder photoSize(SelectorFunction<Size> selector) {
		return this;
	}

	public Fotoapparat build() {
		return new Fotoapparat(this);
	}

}
