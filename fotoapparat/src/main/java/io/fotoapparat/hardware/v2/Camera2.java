package io.fotoapparat.hardware.v2;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

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
import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
import io.fotoapparat.log.Logger;
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

	private final Logger logger;
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
	private final AvailableLensPositionsProvider availableLensPositionsProvider;

	public Camera2(Logger logger,
				   ConnectionOperator connectionOperator,
				   PreviewOperator previewOperator,
				   SurfaceOperator surfaceOperator,
				   OrientationOperator orientationOperator,
				   ParametersOperator parametersOperator,
				   CapabilitiesOperator capabilitiesOperator,
				   CaptureOperator captureOperator,
				   PreviewStream previewStream,
				   RendererParametersOperator rendererParametersOperator,
				   AutoFocusOperator autoFocusOperator,
				   AvailableLensPositionsProvider availableLensPositionsProvider) {
		this.logger = logger;
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
		this.availableLensPositionsProvider = availableLensPositionsProvider;
	}

	@Override
	public void open(LensPosition lensPosition) {
		recordMethod();

		connectionOperator.open(lensPosition);
	}

	@Override
	public void close() {
		recordMethod();

		connectionOperator.close();
	}

	@Override
	public void startPreview() {
		recordMethod();

		previewOperator.startPreview();
	}

	@Override
	public void stopPreview() {
		recordMethod();

		previewOperator.stopPreview();
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		recordMethod();

		surfaceOperator.setDisplaySurface(displaySurface);
	}

	@Override
	public void setDisplayOrientation(int degrees) {
		recordMethod();

		orientationOperator.setDisplayOrientation(degrees);
	}

	@Override
	public void updateParameters(Parameters parameters) {
		recordMethod();

		parametersOperator.updateParameters(parameters);
	}

	@Override
	public Capabilities getCapabilities() {
		recordMethod();

		return capabilitiesOperator.getCapabilities();
	}

	@Override
	public Photo takePicture() {
		recordMethod();

		return captureOperator.takePicture();
	}

	@Override
	public PreviewStream getPreviewStream() {
		recordMethod();

		return previewStream;
	}

	@Override
	public RendererParameters getRendererParameters() {
		recordMethod();

		return rendererParametersOperator.getRendererParameters();
	}

	@Override
	public void autoFocus() {
		recordMethod();

		autoFocusOperator.autoFocus();
	}

	@Override
	public List<LensPosition> getAvailableLensPositions() {
		return availableLensPositionsProvider.getAvailableLensPositions();
	}

	private void recordMethod() {
		Exception lastStacktrace = new Exception();

		logger.log(
				lastStacktrace.getStackTrace()[1].getMethodName()
		);
	}
}
