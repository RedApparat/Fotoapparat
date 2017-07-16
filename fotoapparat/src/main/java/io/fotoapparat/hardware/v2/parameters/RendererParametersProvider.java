package io.fotoapparat.hardware.v2.parameters;

import io.fotoapparat.hardware.operators.RendererParametersOperator;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.parameter.RendererParameters;

/**
 * Provides {@link RendererParameters} for camera v2.
 */
@SuppressWarnings("NewApi")
public class RendererParametersProvider implements RendererParametersOperator {

    private final ParametersProvider parametersProvider;
    private final OrientationManager orientationManager;

    public RendererParametersProvider(ParametersProvider parametersProvider,
                                      OrientationManager orientationManager) {
        this.parametersProvider = parametersProvider;
        this.orientationManager = orientationManager;
    }

    @Override
    public RendererParameters getRendererParameters() {
        return new RendererParameters(
                parametersProvider.getPreviewSize(),
                orientationManager.getPhotoOrientation()
        );
    }

}
