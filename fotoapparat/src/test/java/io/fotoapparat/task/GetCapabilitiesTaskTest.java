package io.fotoapparat.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.provider.GetCapabilitiesTask;
import io.fotoapparat.parameter.range.Ranges;

import static io.fotoapparat.test.TestUtils.resultOf;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class GetCapabilitiesTaskTest {

    private static final Capabilities CAPABILITIES = new Capabilities(
            Collections.singleton(new Size(1400, 1080)),
            Collections.singleton(new Size(1400, 1080)),
            Collections.singleton(FocusMode.CONTINUOUS_FOCUS),
            Collections.singleton(Flash.OFF),
            Collections.singleton(Ranges.continuousRange(30000, 30000)),
            Ranges.continuousRange(1000),
            false
    );

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    GetCapabilitiesTask testee;

    @Test
    public void takePhoto() throws Exception {
        // Given
        given(cameraDevice.getCapabilities())
                .willReturn(CAPABILITIES);

        // When
        Capabilities result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice).getCapabilities();

        assertEquals(result, CAPABILITIES);
    }

}