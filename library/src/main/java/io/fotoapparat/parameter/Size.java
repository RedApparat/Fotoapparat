package io.fotoapparat.parameter;

/**
 * Size in arbitrary units.
 */
public class Size {

	public final int width;
	public final int height;

	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Size size = (Size) o;

		if (width != size.width) return false;
		return height == size.height;

	}

	@Override
	public int hashCode() {
		int result = width;
		result = 31 * result + height;
		return result;
	}

	@Override
	public String toString() {
		return "Size{" +
				"width=" + width +
				", height=" + height +
				'}';
	}
}
