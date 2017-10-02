package io.fotoapparat.hardware.v1.parameters;

import android.hardware.Camera;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.hardware.v1.CameraParametersDecorator;
import io.fotoapparat.hardware.v1.ParametersConverter;
import io.fotoapparat.parameter.Parameters;

/**
 * Updates parameters of camera, possibly throwing a {@link RuntimeException} if something goes
 * wrong. We can't really know why camera might reject parameters, so it should be expected.
 */
@SuppressWarnings("deprecation")
public class UnsafeParametersOperator implements ParametersOperator {

    private final Camera camera;
    private final ParametersConverter parametersConverter;

    public UnsafeParametersOperator(Camera camera,
                                    ParametersConverter parametersConverter) {
        this.camera = camera;
        this.parametersConverter = parametersConverter;
    }

    @Override
    public void updateParameters(Parameters parameters) {
        CameraParametersDecorator parametersProvider = parametersConverter.toPlatformParameters(
                parameters,
                new CameraParametersDecorator(camera.getParameters())
        );

        camera.setParameters(parametersProvider.asCameraParameters());
    }
}
