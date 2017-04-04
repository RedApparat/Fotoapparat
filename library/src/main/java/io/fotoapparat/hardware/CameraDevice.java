package io.fotoapparat.hardware;

import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;

/**
 * Abstraction for camera hardware.
 */
public interface CameraDevice
		extends CaptureOperator, PreviewOperator, CapabilitiesOperator, OrientationOperator, ParametersOperator, ConnectionOperator, SurfaceOperator {

	@Override
	void open(LensPosition lensPosition);

	@Override
	void close();

	@Override
	void startPreview();

	@Override
	void stopPreview();

	@Override
	void setDisplaySurface(Object displaySurface);

	@Override
	void setDisplayOrientation(int degrees);

	@Override
	void updateParameters(Parameters parameters);

	@Override
	Capabilities getCapabilities();

	@Override
	Photo takePicture();

}
