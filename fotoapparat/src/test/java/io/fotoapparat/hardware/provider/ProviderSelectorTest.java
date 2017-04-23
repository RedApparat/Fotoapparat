package io.fotoapparat.hardware.provider;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.util.SDKInfo;

import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProviderSelectorTest {

    @Mock
    Context context;
    @Mock
    SDKInfo sdkInfo;

    ProviderSelector testee;

    @Before
    public void setUp() throws Exception {
        testee = new ProviderSelector(context, sdkInfo);
    }

    @Test
    public void bellowLollipop_V1AvailableLensPositionProvider() throws Exception {
        // Given
        givenBelowLollipop();

        // When
        AvailableLensPositionsProvider cameraProvider = testee.availableLensPositionsProvider();

        // Then
        assertTrue(cameraProvider instanceof V1AvailableLensPositionProvider);
    }

    @Test
    public void lollipopOrHigher_V2AvailableLensPositionProvider() throws Exception {
        // Given
        givenLollipopOrHigher();

        // When
        AvailableLensPositionsProvider cameraProvider = testee.availableLensPositionsProvider();

        // Then
        assertTrue(cameraProvider instanceof V2AvailableLensPositionProvider);
    }

    @Test
    public void bellowLollipop_V1CameraProvider() throws Exception {
        // Given
        givenBelowLollipop();

        // When
        CameraProvider cameraProvider = testee.cameraProvider();

        // Then
        assertTrue(cameraProvider instanceof V1Provider);
    }

    @Test
    public void lollipopOrHigher_V2CameraProvider() throws Exception {
        // Given
        givenLollipopOrHigher();

        // When
        CameraProvider cameraProvider = testee.cameraProvider();

        // Then
        assertTrue(cameraProvider instanceof V2Provider);
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