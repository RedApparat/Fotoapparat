package io.fotoapparat.parameter.factory;

import org.junit.Test;

import java.util.Collections;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.AntiBandingMode;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;
import io.fotoapparat.util.TestSelectors;

import static io.fotoapparat.util.TestSelectors.selectFromCollection;
import static junit.framework.Assert.assertEquals;

public class ParametersFactoryTest {

    static final Capabilities EMPTY_CAPABILITIES = Capabilities.empty();

    @Test
    public void selectPictureSize() throws Exception {
        // Given
        Size size = new Size(100, 100);

        Capabilities capabilities = new Capabilities(
                Collections.singleton(size),
                Collections.<Size>emptySet(),
                Collections.<AntiBandingMode>emptySet(),
                Collections.<FocusMode>emptySet(),
                Collections.<Flash>emptySet(),
                Collections.<Range<Integer>>emptySet(),
                Ranges.<Integer>emptyRange(),
                false
        );

        // When
        Parameters result = ParametersFactory.selectPictureSize(capabilities,
                selectFromCollection(size));

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.PICTURE_SIZE, size),
                result
        );
    }

    @Test
    public void selectPreviewSize() throws Exception {
        // Given
        Size size = new Size(100, 100);

        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.singleton(size),
                Collections.<AntiBandingMode>emptySet(),
                Collections.<FocusMode>emptySet(),
                Collections.<Flash>emptySet(),
                Collections.<Range<Integer>>emptySet(),
                Ranges.<Integer>emptyRange(),
                false
        );

        // When
        Parameters result = ParametersFactory.selectPreviewSize(capabilities,
                selectFromCollection(size));

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.PREVIEW_SIZE, size),
                result
        );
    }

    @Test
    public void selectFocusMode() throws Exception {
        // Given
        AntiBandingMode antiBandingMode = AntiBandingMode.AUTO;
        FocusMode focusMode = FocusMode.AUTO;

        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.<Size>emptySet(),
                Collections.singleton(antiBandingMode),
                Collections.singleton(focusMode),
                Collections.<Flash>emptySet(),
                Collections.<Range<Integer>>emptySet(),
                Ranges.<Integer>emptyRange(),
                false
        );

        // When
        Parameters result = ParametersFactory.selectFocusMode(capabilities,
                selectFromCollection(focusMode));

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.FOCUS_MODE, focusMode),
                result
        );
    }

    @Test
    public void selectFlashMode() throws Exception {
        // Given
        Flash flash = Flash.AUTO;

        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.<Size>emptySet(),
                Collections.<AntiBandingMode>emptySet(),
                Collections.<FocusMode>emptySet(),
                Collections.singleton(flash),
                Collections.<Range<Integer>>emptySet(),
                Ranges.<Integer>emptyRange(),
                false
        );

        // When
        Parameters result = ParametersFactory.selectFlashMode(capabilities,
                selectFromCollection(flash));

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.FLASH, flash),
                result
        );
    }

    @Test
    public void selectPreviewFpsRange() throws Exception {
        // Given
        Range<Integer> range = Ranges.continuousRange(30000, 30000);

        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.<Size>emptySet(),
                Collections.<AntiBandingMode>emptySet(),
                Collections.<FocusMode>emptySet(),
                Collections.<Flash>emptySet(),
                Collections.singleton(range),
                Ranges.<Integer>emptyRange(),
                false
        );

        // When
        Parameters result = ParametersFactory.selectPreviewFpsRange(capabilities,
                selectFromCollection(range));

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.PREVIEW_FPS_RANGE, range),
                result
        );
    }

    @Test
    public void selectSensorSensitivity() throws Exception {
        // When
        Integer isoValue = 1200;

        Capabilities capabilities = new Capabilities(
                Collections.<Size>emptySet(),
                Collections.<Size>emptySet(),
                Collections.<AntiBandingMode>emptySet(),
                Collections.<FocusMode>emptySet(),
                Collections.<Flash>emptySet(),
                Collections.<Range<Integer>>emptySet(),
                Ranges.continuousRange(isoValue),
                false
        );

        // Then
        Parameters result = ParametersFactory.selectSensorSensitivity(capabilities,
                TestSelectors.<Range<Integer>, Integer>select(isoValue));

        assertEquals(
                new Parameters().putValue(Parameters.Type.SENSOR_SENSITIVITY, isoValue),
                result
        );
    }

    @Test
    public void selectJpegQuality() throws Exception {
        // Given
        int quality = 100;

        // When
        Parameters result = ParametersFactory.selectJpegQuality(quality);

        // Then
        assertEquals(
                new Parameters().putValue(Parameters.Type.JPEG_QUALITY, quality),
                result
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCollectionSelector() throws Exception {
        // Given
        Size size = new Size(100, 100);

        // When
        ParametersFactory.selectPictureSize(EMPTY_CAPABILITIES, selectFromCollection(size));

        // Then
        // Exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRangeSelector() throws Exception {
        // Given
        Integer isoValue = 1200;

        // When
        ParametersFactory.selectSensorSensitivity(EMPTY_CAPABILITIES,
                TestSelectors.<Range<Integer>, Integer>select(isoValue));

        // Then
        // Exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSmallerJpegQuality() throws Exception {
        // Given
        int quality = -1;

        // When
        ParametersFactory.selectJpegQuality(quality);

        // Then
        // Exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidBiggerJpegQuality() throws Exception {
        // Given
        int quality = 101;

        // When
        ParametersFactory.selectJpegQuality(quality);

        // Then
        // Exception
    }
}