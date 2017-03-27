package io.fotoapparat.hardware;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Parameters of {@link CameraDevice}.
 */
public class Parameters {

	public void putValue(Type type, Object value) {
	}

	public <T> T getValue(Type type) {
		return null;
	}

	public List<Type> storedTypes() {
		return emptyList();
	}

	public enum Type {

		PICTURE_SIZE
	}

}
