package io.fotoapparat.hardware.v2.surface;

import android.graphics.SurfaceTexture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.Size;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SetTextureBufferSizeTaskTest {

    @Mock
    SurfaceTexture surfaceTexture;

    @Test
    public void setBufferSize() throws Exception {
        // Given
        Size previewSize = new Size(1920, 1080);
        SetTextureBufferSizeTask testee = new SetTextureBufferSizeTask(surfaceTexture, previewSize);

        // When
        testee.run();

        // Then
        verify(surfaceTexture).setDefaultBufferSize(previewSize.width, previewSize.height);
    }
}