package io.fotoapparat.hardware.orientation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ListenerUtilsTest {

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

}