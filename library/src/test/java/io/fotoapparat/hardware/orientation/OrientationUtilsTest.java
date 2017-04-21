package io.fotoapparat.hardware.orientation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrientationUtilsTest {

    @Test
    public void toClosestRightAngle_0() throws Exception {
        assertEquals(
                0,
                OrientationUtils.toClosestRightAngle(5)
        );
    }

    @Test
    public void toClosestRightAngle_90() throws Exception {
        assertEquals(
                90,
                OrientationUtils.toClosestRightAngle(60)
        );
    }

    @Test
    public void toClosestRightAngle_180() throws Exception {
        assertEquals(
                180,
                OrientationUtils.toClosestRightAngle(190)
        );
    }

    @Test
    public void toClosestRightAngle_270() throws Exception {
        assertEquals(
                270,
                OrientationUtils.toClosestRightAngle(269)
        );
    }

    @Test
    public void toClosestRightAngle_360() throws Exception {
        assertEquals(
                0,
                OrientationUtils.toClosestRightAngle(359)
        );
    }

    @Test
    public void computeDisplayOrientation_Phone_Portrait_FrontCamera() throws Exception {
        // Given
        int screenOrientation = 0;
        int cameraOrientation = 270;

        // When
        int result = OrientationUtils.computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        );

        // Then
        assertEquals(90, result);
    }

    @Test
    public void computeDisplayOrientation_Phone_Landscape_FrontCamera() throws Exception {
        // Given
        int screenOrientation = 270;
        int cameraOrientation = 270;

        // When
        int result = OrientationUtils.computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        );

        // Then
        assertEquals(180, result);
    }

    @Test
    public void computeDisplayOrientation_Phone_Portrait_BackCamera() throws Exception {
        // Given
        int screenOrientation = 0;
        int cameraOrientation = 90;

        // When
        int result = OrientationUtils.computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        );

        // Then
        assertEquals(90, result);
    }

    @Test
    public void computeDisplayOrientation_Phone_Landscape_BackCamera() throws Exception {
        // Given
        int screenOrientation = 270;
        int cameraOrientation = 90;

        // When
        int result = OrientationUtils.computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        );

        // Then
        assertEquals(180, result);
    }

    @Test
    public void computeImageOrientation_Phone_Portrait_FrontCamera() throws Exception {
        // Given
        int screenOrientation = 0;
        int cameraOrientation = 270;

        // When
        int result = OrientationUtils.computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        );

        // Then
        assertEquals(90, result);
    }

    @Test
    public void computeImageOrientation_Phone_Landscape_FrontCamera() throws Exception {
        // Given
        int screenOrientation = 270;
        int cameraOrientation = 270;

        // When
        int result = OrientationUtils.computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        );

        // Then
        assertEquals(180, result);
    }

    @Test
    public void computeImageOrientation_Phone_Portrait_BackCamera() throws Exception {
        // Given
        int screenOrientation = 0;
        int cameraOrientation = 90;

        // When
        int result = OrientationUtils.computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        );

        // Then
        assertEquals(270, result);
    }

    @Test
    public void computeImageOrientation_Phone_Landscape_BackCamera() throws Exception {
        // Given
        int screenOrientation = 270;
        int cameraOrientation = 90;

        // When
        int result = OrientationUtils.computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        );

        // Then
        assertEquals(180, result);
    }

}