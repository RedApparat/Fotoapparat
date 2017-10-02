package io.fotoapparat.hardware.v2;

import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.operators.AutoFocusOperator;
import io.fotoapparat.hardware.operators.CapabilitiesOperator;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.operators.ExposureMeasurementOperator;
import io.fotoapparat.hardware.operators.OrientationOperator;
import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.hardware.operators.PreviewOperator;
import io.fotoapparat.hardware.operators.RendererParametersOperator;
import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.hardware.provider.AvailableLensPositionsProvider;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.lens.FocusResult;
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
    private final ExposureMeasurementOperator exposureMeasurementOperator;
    private final CaptureOperator captureOperator;
    private final PreviewStream previewStream;
    private final RendererParametersOperator rendererParametersOperator;
    private final AutoFocusOperator autoFocusOperator;
    private final AvailableLensPositionsProvider availableLensPositionsProvider;

    private CountDownLatch currentParametersLatch;
    private Parameters currentParameters;

    public Camera2(Logger logger,
                   ConnectionOperator connectionOperator,
                   PreviewOperator previewOperator,
                   SurfaceOperator surfaceOperator,
                   OrientationOperator orientationOperator,
                   ParametersOperator parametersOperator,
                   CapabilitiesOperator capabilitiesOperator,
                   PreviewStream previewStream,
                   RendererParametersOperator rendererParametersOperator,
                   AutoFocusOperator autoFocusOperator,
                   ExposureMeasurementOperator exposureMeasurementOperator,
                   CaptureOperator captureOperator,
                   AvailableLensPositionsProvider availableLensPositionsProvider) {
        this.logger = logger;
        this.connectionOperator = connectionOperator;
        this.parametersOperator = parametersOperator;
        this.previewOperator = previewOperator;
        this.orientationOperator = orientationOperator;
        this.surfaceOperator = surfaceOperator;
        this.capabilitiesOperator = capabilitiesOperator;
        this.exposureMeasurementOperator = exposureMeasurementOperator;
        this.captureOperator = captureOperator;
        this.previewStream = previewStream;
        this.rendererParametersOperator = rendererParametersOperator;
        this.autoFocusOperator = autoFocusOperator;
        this.availableLensPositionsProvider = availableLensPositionsProvider;
        this.currentParametersLatch = new CountDownLatch(1);
        this.currentParameters = null;
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
        currentParameters = parameters;
        currentParametersLatch.countDown();
    }

    @Override
    public Capabilities getCapabilities() {
        recordMethod();

        return capabilitiesOperator.getCapabilities();
    }

    @Override
    public Parameters getCurrentParameters() {
        try {
            currentParametersLatch.await();
        } catch (InterruptedException ex) {
            // ignore
        }
        return currentParameters;
    }

    @Override
    public FocusResult autoFocus() {
        recordMethod();

        return autoFocusOperator.autoFocus();
    }

    @Override
    public void measureExposure() {
        recordMethod();

        exposureMeasurementOperator.measureExposure();
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
    public List<LensPosition> getAvailableLensPositions() {
        recordMethod();

        return availableLensPositionsProvider.getAvailableLensPositions();
    }

    @Override
    public void setZoom(@FloatRange(from = 0f, to = 1f) float level) {
        throw new UnsupportedOperationException("Not implemented. We do not actively support Camera2 at the moment.");
    }

    private void recordMethod() {
        Exception lastStacktrace = new Exception();

        logger.log(
                lastStacktrace.getStackTrace()[1].getMethodName()
        );
    }
}
