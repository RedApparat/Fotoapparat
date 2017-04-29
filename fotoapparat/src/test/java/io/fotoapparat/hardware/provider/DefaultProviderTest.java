package io.fotoapparat.hardware.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.log.Logger;
import io.fotoapparat.util.SDKInfo;

import static junit.framework.Assert.assertSame;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProviderTest {

	@Mock
	CameraProvider v1Provider;
	@Mock
	CameraDevice camera1;

	@Mock
	CameraProvider v2Provider;
	@Mock
	CameraDevice camera2;

	@Mock
	SDKInfo sdkInfo;
	@Mock
	Logger logger;

	DefaultProvider testee;

	@Before
	public void setUp() throws Exception {
		testee = new DefaultProvider(
				v1Provider,
				v2Provider,
				sdkInfo
		);

		given(v1Provider.get(logger))
				.willReturn(camera1);
		given(v2Provider.get(logger))
				.willReturn(camera2);
	}

	@Test
	public void bellowLollipop_V1CameraProvider() throws Exception {
		// Given
		givenBelowLollipop();

		// When
		CameraDevice device = testee.get(logger);

		// Then
		assertSame(camera1, device);
	}

	@Test
	public void lollipopOrHigher_V2CameraProvider() throws Exception {
		// Given
		givenLollipopOrHigher();

		// When
		CameraDevice device = testee.get(logger);

		// Then
		assertSame(camera2, device);
	}

	private void givenBelowLollipop() {
		given(sdkInfo.isBellowLollipop())
				.willReturn(true);
	}

	private void givenLollipopOrHigher() {
		given(sdkInfo.isBellowLollipop())
				.willReturn(false);
	}
}