package io.fotoapparat.routine.focus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.result.FocusResult;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.test.ImmediateExecutor;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AutoFocusRoutineTest {

    @Mock
    CameraDevice cameraDevice;
    @Spy
    Executor executor = new ImmediateExecutor();

    @InjectMocks
    AutoFocusRoutine testee;

    @Test
    public void autoFocus() throws Exception {
        // When
        PendingResult<FocusResult> result = testee.autoFocus();

        // Then
        verify(executor).execute(isA(AutoFocusTask.class));

        assertNotNull(result);
    }

}