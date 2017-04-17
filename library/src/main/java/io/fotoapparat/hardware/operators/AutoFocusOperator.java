package io.fotoapparat.hardware.operators;

/**
 * Performs auto focus.
 */
public interface AutoFocusOperator {

	/**
	 * Performs auto focus. This is a blocking operation which returns when auto focus completes.
	 */
	void autoFocus();

}
