package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class GetCharacteristicsTaskTest {

    @Mock
    CameraManager manager;
    @InjectMocks
    GetCharacteristicsTask testee;

    @Test
    public void getCharacteristics() throws Exception {
        // Given

        // When
        Characteristics call = testee.execute("0");

        // Then
        assertNotNull(call);
    }

    @Test(expected = CameraException.class)
    public void managerException_exception() throws Exception {
        // Given
        given(manager.getCameraCharacteristics(anyString()))
                .willThrow(CameraAccessException.class);

        // When
        Characteristics call = testee.execute("0");

        // Then
        // exception
    }
}