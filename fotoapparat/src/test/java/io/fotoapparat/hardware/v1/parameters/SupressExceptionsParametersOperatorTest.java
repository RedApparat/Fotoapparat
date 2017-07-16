package io.fotoapparat.hardware.v1.parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Parameters;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SupressExceptionsParametersOperatorTest {

    @Mock
    ParametersOperator wrapped;
    @Mock
    Logger logger;
    @Mock
    Parameters parameters;

    @InjectMocks
    SupressExceptionsParametersOperator testee;

    @Test
    public void updateParameters() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(wrapped)
                .updateParameters(parameters);

        // When
        testee.updateParameters(parameters);

        // Then
        verify(wrapped).updateParameters(parameters);
    }

}