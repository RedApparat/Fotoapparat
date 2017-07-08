package io.fotoapparat.routine.picture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.routine.picture.TakePictureRoutine;
import io.fotoapparat.routine.picture.TakePictureTask;
import io.fotoapparat.test.ImmediateExecutor;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TakePictureRoutineTest {

    @Mock
    CameraDevice cameraDevice;
    @Spy
    Executor executor = new ImmediateExecutor();

    @InjectMocks
    TakePictureRoutine testee;

    @Test
    public void takePicture_EmptyRequest() throws Exception {
        // When
        PhotoResult result = testee.takePicture();

        // Then
        verify(executor).execute(isA(TakePictureTask.class));

        assertNotNull(result);
    }

}