package io.fotoapparat.hardware.v1.parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.operators.ParametersOperator;
import io.fotoapparat.parameter.Parameters;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SwitchOnFailureParametersOperatorTest {

    @Mock
    ParametersOperator first;
    @Mock
    ParametersOperator second;
    @Mock
    Parameters parameters;

    SwitchOnFailureParametersOperator testee;

    @Before
    public void setUp() throws Exception {
        testee = new SwitchOnFailureParametersOperator(
                first, second
        );
    }

    @Test
    public void firstSucceeds() throws Exception {
        // When
        testee.updateParameters(parameters);

        // Then
        verify(first).updateParameters(parameters);

        verifyZeroInteractions(second);
    }

    @Test
    public void firstFails() throws Exception {
        // Given
        doThrow(new RuntimeException())
                .when(first)
                .updateParameters(parameters);

        // When
        testee.updateParameters(parameters);

        // Then
        verify(second).updateParameters(parameters);
    }

}