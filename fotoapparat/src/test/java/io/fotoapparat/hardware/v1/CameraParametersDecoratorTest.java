package io.fotoapparat.hardware.v1;

import android.hardware.Camera;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

import static io.fotoapparat.test.TestUtils.asSet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CameraParametersDecoratorTest {

    @Mock
    Camera.Parameters parameters;
    @InjectMocks
    CameraParametersDecorator parametersProvider;

    @Test
    public void sensorSensitivityValues_Available() throws Exception {
        // Given
        given(parameters.get(anyString()))
                .willReturn("400,600,1200");

        // When
        Set<Integer> isoValues = parametersProvider.getSensorSensitivityValues();

        // Then
        Assert.assertEquals(
                isoValues,
                asSet(400, 600, 1200)
        );
    }

    @Test
    public void sensorSensitivityValues_NotAvailable() throws Exception {
        // Given
        given(parameters.get(anyString()))
                .willReturn(null);

        // When
        Set<Integer> isoValues = parametersProvider.getSensorSensitivityValues();

        // Then
        Assert.assertEquals(
                isoValues,
                Collections.<Integer>emptySet()
        );
    }
}
