package io.fotoapparat.hardware.v2.orientation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.v2.connection.CameraConnection;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class OrientationManagerTest {

    static final int PORTRAIT_DEGREES = 0;
    static final int LANDSCAPE_DEGREES = 90;
    static final int REVERSE_PORTRAIT_DEGREES = 180;
    static final int REVERSE_LANDSCAPE_DEGREES = 270;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    CameraConnection cameraConnection;
    @InjectMocks
    OrientationManager testee;

    @Test
    public void back_landscape_0() throws Exception {
        // Given
        givenSensor90Degrees();
        givenLandscapeScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(0, orientation);
    }

    @Test
    public void back_portrait_90() throws Exception {
        // Given
        givenSensor90Degrees();
        givenPortraitScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(270, orientation);
    }

    @Test
    public void back_reverseLandscape_180() throws Exception {
        // Given
        givenSensor90Degrees();
        givenReverseLandscapeScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(180, orientation);
    }

    @Test
    public void back_reversePortrait_270() throws Exception {
        // Given
        givenSensor90Degrees();
        givenReversePortraitScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(90, orientation);
    }

    @Test
    public void front_reverseLandscape_0() throws Exception {
        // Given
        givenFrontLens();
        givenSensor90Degrees();
        givenReverseLandscapeScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(0, orientation);
    }

    @Test
    public void front_portrait_90() throws Exception {
        // Given
        givenFrontLens();
        givenSensor90Degrees();
        givenPortraitScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(270, orientation);
    }

    @Test
    public void front_landscape_180() throws Exception {
        // Given
        givenFrontLens();
        givenSensor90Degrees();
        givenLandscapeScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(180, orientation);
    }

    @Test
    public void front_reversePortrait_270() throws Exception {
        // Given
        givenFrontLens();
        givenSensor90Degrees();
        givenReversePortraitScreen();

        // When
        int orientation = testee.getPhotoOrientation();

        // Then
        assertEquals(90, orientation);
    }

    private void givenFrontLens() {
        given(cameraConnection.getCharacteristics().isFrontFacingLens())
                .willReturn(true);
    }

    private void givenSensor90Degrees() {
        given(cameraConnection.getCharacteristics().getSensorOrientation())
                .willReturn(90);
    }

    private void givenPortraitScreen() {
        testee.setDisplayOrientation(PORTRAIT_DEGREES);
    }

    private void givenLandscapeScreen() {
        testee.setDisplayOrientation(LANDSCAPE_DEGREES);
    }

    private void givenReversePortraitScreen() {
        testee.setDisplayOrientation(REVERSE_PORTRAIT_DEGREES);
    }

    private void givenReverseLandscapeScreen() {
        testee.setDisplayOrientation(REVERSE_LANDSCAPE_DEGREES);
    }
}