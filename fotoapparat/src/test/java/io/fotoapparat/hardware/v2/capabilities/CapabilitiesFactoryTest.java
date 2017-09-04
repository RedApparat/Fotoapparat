package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CapabilitiesFactoryTest {

	static final Set<Flash> FLASH_SET = asSet(
			Flash.OFF,
			Flash.TORCH,
			Flash.ON,
			Flash.AUTO,
			Flash.AUTO_RED_EYE
	);
	static final Set<FocusMode> FOCUS_MODE_SET = asSet(
			FocusMode.FIXED,
			FocusMode.AUTO,
			FocusMode.MACRO,
			FocusMode.CONTINUOUS_FOCUS,
			FocusMode.EDOF
	);
	static final Set<Size> SIZE_SET = asSet(
			new Size(10, 20)
	);
    static final Set<Range<Integer>> PREVIEW_FPS_RANGE_SET = asSet(
            Ranges.continuousRange(24000, 24000),
            Ranges.continuousRange(30000, 30000)
    );

	@Mock
	CameraConnection cameraConnection;
	@Mock
	Characteristics characteristics;
	@InjectMocks
	CapabilitiesFactory testee;

	@Before
	public void setUp() throws Exception {
		given(cameraConnection.getCharacteristics())
				.willReturn(characteristics);

		given(characteristics.isFlashAvailable())
				.willReturn(false);
		given(characteristics.getJpegOutputSizes())
				.willReturn(Collections.<Size>emptySet());
		given(characteristics.autoExposureModes())
				.willReturn(new int[0]);
		given(characteristics.autoFocusModes())
				.willReturn(new int[0]);
		given(characteristics.getTargetFpsRanges())
				.willReturn(Collections.<Range<Integer>>emptySet());
	}

	@Test
	public void supportedFlashModes_FlashAvailable() throws Exception {
		// Given
		given(characteristics.isFlashAvailable())
				.willReturn(true);
		given(characteristics.autoExposureModes())
				.willReturn(new int[]{
						CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH,
						CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH,
						CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE
				});

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(FLASH_SET, capabilities.supportedFlashModes());
	}

	@Test
	public void supportedFlashModes_FlashNotAvailable() throws Exception {
		// Given
		given(characteristics.isFlashAvailable())
				.willReturn(false);

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(
				asSet(Flash.OFF),
				capabilities.supportedFlashModes()
		);
	}

	@Test
	public void supportedFocusModes() throws Exception {
		// Given
		given(characteristics.autoFocusModes())
				.willReturn(
						new int[]{
								CameraMetadata.CONTROL_AF_MODE_OFF,
								CameraMetadata.CONTROL_AF_MODE_AUTO,
								CameraMetadata.CONTROL_AF_MODE_MACRO,
								CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
								CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_PICTURE,
								CameraMetadata.CONTROL_AF_MODE_EDOF
						}
				);

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(FOCUS_MODE_SET, capabilities.supportedFocusModes());
	}

	@Test
	public void supportedPictureSizes() throws Exception {
		// Given
		given(characteristics.getJpegOutputSizes())
				.willReturn(SIZE_SET);

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(SIZE_SET, capabilities.supportedPictureSizes());
	}

	@Test
	public void supportedPreviewSizes_Below_1080p() throws Exception {
		// Given
		given(characteristics.getSurfaceOutputSizes())
				.willReturn(asSet(
						new Size(1000, 1000),
						new Size(1080, 1920),
						new Size(1920, 1080),
						new Size(1920, 1920),
						new Size(1440, 1920)
				));

		// When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(
				asSet(
						new Size(1000, 1000),
						new Size(1920, 1080)
				),
				capabilities.supportedPreviewSizes()
		);
	}

	@Test
	public void supportedPreviewFpsRanges() throws Exception {
		// Given
		given(characteristics.getTargetFpsRanges())
				.willReturn(PREVIEW_FPS_RANGE_SET);

        // When
		Capabilities capabilities = testee.getCapabilities();

		// Then
		assertEquals(
		        PREVIEW_FPS_RANGE_SET,
                capabilities.supportedPreviewFpsRanges()
		);
	}

}