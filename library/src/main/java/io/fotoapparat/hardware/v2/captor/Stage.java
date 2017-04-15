package io.fotoapparat.hardware.v2.captor;

/**
 * The stage of a photo capturing routine.
 */
enum Stage {

	/**
	 * Camera has not successfully focused. Auto focus might either been locked or not.
	 */
	UNFOCUSED,

	/**
	 * Camera has focused but needs to gather exposure data.
	 */
	PRECAPTURE,

	/**
	 * Camera can capture a picture. Focus and exposure data have been gathered.
	 */
	CAPTURE,

	/**
	 * Camera completed a picture capture.
	 */
	CAPTURE_COMPLETED
}
