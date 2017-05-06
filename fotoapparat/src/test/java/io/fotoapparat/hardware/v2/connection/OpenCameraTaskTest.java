package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.v2.CameraThread;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SuppressWarnings({"NewApi", "ResourceType"})
@RunWith(MockitoJUnitRunner.class)
public class OpenCameraTaskTest {

    @Mock
    CameraDevice cameraDevice;
    @Mock
    Handler handler;
    @Mock
    CameraThread cameraThread;
    @Mock
    CameraManager manager;

    @InjectMocks
    OpenCameraTask testee;

    @Test
    public void getCameraDevice() throws Exception {
        // Given
        given(cameraThread.createHandler())
                .willReturn(handler);

        testee.onOpened(cameraDevice);

        // When
        CameraDevice cameraDevice = testee.execute("0");

        // Then
        assertEquals(this.cameraDevice, cameraDevice);
    }

}