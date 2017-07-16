package io.fotoapparat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.provider.CapabilitiesProvider;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.CapabilitiesResult;
import io.fotoapparat.result.FocusResult;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.routine.CheckAvailabilityRoutine;
import io.fotoapparat.routine.ConfigurePreviewStreamRoutine;
import io.fotoapparat.routine.StartCameraRoutine;
import io.fotoapparat.routine.StopCameraRoutine;
import io.fotoapparat.routine.UpdateOrientationRoutine;
import io.fotoapparat.routine.focus.AutoFocusRoutine;
import io.fotoapparat.routine.picture.TakePictureRoutine;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FotoapparatTest {

    static final PhotoResult PHOTO_RESULT = PhotoResult.fromFuture(
            immediateFuture(
                    Photo.empty()
            )
    );

    static final PendingResult<FocusResult> FOCUS_RESULT = PendingResult.fromFuture(
            immediateFuture(
                    FocusResult.FOCUSED
            )
    );

    static final CapabilitiesResult CAPABILITIES_RESULT = CapabilitiesResult.fromFuture(
            immediateFuture(
                    Capabilities.empty()
            )
    );

    @Mock
    StartCameraRoutine startCameraRoutine;
    @Mock
    StopCameraRoutine stopCameraRoutine;
    @Mock
    UpdateOrientationRoutine updateOrientationRoutine;
    @Mock
    ConfigurePreviewStreamRoutine configurePreviewStreamRoutine;
    @Mock
    CapabilitiesProvider capabilitiesProvider;
    @Mock
    TakePictureRoutine takePictureRoutine;
    @Mock
    AutoFocusRoutine autoFocusRoutine;
    @Mock
    CheckAvailabilityRoutine checkAvailabilityRoutine;

    Fotoapparat testee;

    @Before
    public void setUp() throws Exception {
        testee = new Fotoapparat(
                startCameraRoutine,
                stopCameraRoutine,
                updateOrientationRoutine,
                configurePreviewStreamRoutine,
                capabilitiesProvider,
                takePictureRoutine,
                autoFocusRoutine,
                checkAvailabilityRoutine,
                new ImmediateExecutor()
        );
    }

    @Test
    public void start() throws Exception {
        // When
        testee.start();

        // Then
        InOrder inOrder = inOrder(
                startCameraRoutine,
                updateOrientationRoutine,
                configurePreviewStreamRoutine
        );

        inOrder.verify(startCameraRoutine).run();
        inOrder.verify(configurePreviewStreamRoutine).run();
        inOrder.verify(updateOrientationRoutine).start();
    }

    @Test(expected = IllegalStateException.class)
    public void start_SecondTime() throws Exception {
        // Given
        testee.start();

        // When
        testee.start();

        // Then
        // Expect exception
    }

    @Test
    public void stop() throws Exception {
        // Given
        testee.start();

        // When
        testee.stop();

        // Then
        InOrder inOrder = inOrder(
                stopCameraRoutine,
                updateOrientationRoutine
        );

        inOrder.verify(updateOrientationRoutine).stop();
        inOrder.verify(stopCameraRoutine).run();
    }

    @Test(expected = IllegalStateException.class)
    public void stop_NotStarted() throws Exception {
        // When
        testee.stop();

        // Then
        // Expect exception
    }

    @Test
    public void getCapabilities() throws Exception {
        // Given
        given(capabilitiesProvider.getCapabilities())
                .willReturn(CAPABILITIES_RESULT);

        testee.start();

        // When
        CapabilitiesResult result = testee.getCapabilities();

        // Then
        assertEquals(
                CAPABILITIES_RESULT,
                result
        );
    }

    @Test
    public void takePicture() throws Exception {
        // Given
        given(takePictureRoutine.takePicture())
                .willReturn(PHOTO_RESULT);

        testee.start();

        // When
        PhotoResult result = testee.takePicture();

        // Then
        assertEquals(
                PHOTO_RESULT,
                result
        );
    }

    @Test(expected = IllegalStateException.class)
    public void takePicture_NotStartedYet() throws Exception {
        // When
        testee.takePicture();

        // Then
        // Expect exception
    }

    @Test(expected = IllegalStateException.class)
    public void getCapabilities_NotStartedYet() throws Exception {
        // When
        testee.getCapabilities();

        // Then
        // Expect exception
    }

    @Test
    public void autoFocus() throws Exception {
        // Given
        testee.start();

        // When
        testee.autoFocus();

        // Then
        verify(autoFocusRoutine).autoFocus();
    }

    @Test(expected = IllegalStateException.class)
    public void autoFocus_NotStartedYet() throws Exception {
        // When
        testee.autoFocus();

        // Then
        // Expect exception
    }

    @Test
    public void focus() throws Exception {
        // Given
        given(autoFocusRoutine.autoFocus())
                .willReturn(FOCUS_RESULT);

        testee.start();

        // When
        PendingResult<FocusResult> result = testee.focus();

        // Then
        assertEquals(
                FOCUS_RESULT,
                result
        );

    }

    @Test(expected = IllegalStateException.class)
    public void focus_NotStartedYet() throws Exception {
        // When
        testee.focus();

        // Then
        // Expect exception
    }

    @Test(expected = IllegalStateException.class)
    public void ensureNonNullContext() throws Exception {
        // Given

        // When
        Fotoapparat.with(null);

        // Then
        // Expect exception
    }

    @Test
    public void isAvailable_Yes() throws Exception {
        // Given
        given(checkAvailabilityRoutine.isAvailable())
                .willReturn(true);

        // When
        boolean result = testee.isAvailable();

        // Then
        assertTrue(result);
    }

    @Test
    public void isAvailable_No() throws Exception {
        // Given
        given(checkAvailabilityRoutine.isAvailable())
                .willReturn(false);

        // When
        boolean result = testee.isAvailable();

        // Then
        assertFalse(result);
    }

}