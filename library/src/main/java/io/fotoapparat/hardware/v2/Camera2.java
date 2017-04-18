package io.fotoapparat.hardware.v2;

import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.operators.RendererParametersOperator;
import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.RendererParameters;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.preview.PreviewStream;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice {

	private final OrientationOperator orientationOperator;
	private final SurfaceOperator surfaceOperator;
	private final CapabilitiesOperator capabilitiesOperator;
	private final ConnectionOperator connectionOperator;
	private final ParametersOperator parametersOperator;
	private final PreviewOperator previewOperator;
	private final CaptureOperator captureOperator;
	private final PreviewStream previewStream;
	private final RendererParametersOperator rendererParametersOperator;
	private final AutoFocusOperator autoFocusOperator;

	public Camera2(ConnectionOperator connectionOperator,
				   PreviewOperator previewOperator,
				   SurfaceOperator surfaceOperator,
				   OrientationOperator orientationOperator,
				   ParametersOperator parametersOperator,
				   CapabilitiesOperator capabilitiesOperator,
				   CaptureOperator captureOperator,
				   PreviewStream previewStream,
				   RendererParametersOperator rendererParametersOperator,
				   AutoFocusOperator autoFocusOperator) {
		this.connectionOperator = connectionOperator;
		this.parametersOperator = parametersOperator;
		this.previewOperator = previewOperator;
		this.orientationOperator = orientationOperator;
		this.surfaceOperator = surfaceOperator;
		this.capabilitiesOperator = capabilitiesOperator;
		this.captureOperator = captureOperator;
		this.previewStream = previewStream;
		this.rendererParametersOperator = rendererParametersOperator;
		this.autoFocusOperator = autoFocusOperator;
	}

	@Override
	public void open(LensPosition lensPosition) {
		connectionOperator.open(lensPosition);
	}

	@Override
	public void close() {
		connectionOperator.close();
	}

	@Override
	public void startPreview() {
		previewOperator.startPreview();
	}

	@Override
	public void stopPreview() {
		previewOperator.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		surfaceOperator.setDisplaySurface(displaySurface);
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		orientationOperator.setDisplayOrientation(degrees);
	}

	@Override
	public void updateParameters(Parameters parameters) {
		parametersOperator.updateParameters(parameters);
	}

	@Override
	public Capabilities getCapabilities() {
		return capabilitiesOperator.getCapabilities();
	}

	@Override
	public Photo takePicture() {
		return captureOperator.takePicture();
	}

	@Override
	public PreviewStream getPreviewStream() {
		return previewStream;
	}

	@Override
	public RendererParameters getRendererParameters() {
		return rendererParametersOperator.getRendererParameters();
	}

	@Override
	public void autoFocus() {
		autoFocusOperator.autoFocus();
	}

}
