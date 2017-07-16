package io.fotoapparat.hardware.v1.parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SplitParametersOperatorTest {

    static final Size PICTURE_SIZE = new Size(100, 100);
    static final Size PREVIEW_SIZE = new Size(50, 50);

    @Mock
    ParametersOperator wrapped;

    @InjectMocks
    SplitParametersOperator testee;

    @Test
    public void updateParameters() throws Exception {
        // Given
        Parameters parameters = new Parameters();
        parameters.putValue(Parameters.Type.PICTURE_SIZE, PICTURE_SIZE);
        parameters.putValue(Parameters.Type.PREVIEW_SIZE, PREVIEW_SIZE);

        // When
        testee.updateParameters(parameters);

        // Then
        verify(wrapped).updateParameters(
                parametersWithJust(Parameters.Type.PICTURE_SIZE, PICTURE_SIZE)
        );
        verify(wrapped).updateParameters(
                parametersWithJust(Parameters.Type.PREVIEW_SIZE, PREVIEW_SIZE)
        );

        verifyNoMoreInteractions(wrapped);
    }

    private Parameters parametersWithJust(Parameters.Type type, Object value) {
        Parameters parameters = new Parameters();
        parameters.putValue(type, value);

        return parameters;
    }
}