package io.fotoapparat.hardware.v2;

import android.view.TextureView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v2.capabilities.CapabilitiesFactory;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.parameters.ParametersManager;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.surface.TextureManager;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;

import static java.util.Collections.singleton;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class Camera2Test {

	@Mock
	OrientationManager orientationManager;
	@Mock
	TextureManager textureManager;
	@Mock
	CapabilitiesFactory capabilitiesFactory;
	@Mock
	CameraConnection connection;
	@Mock
	ParametersManager parametersManager;
	@Mock
	SessionManager sessionManager;
	@Mock
	PictureCaptor pictureCaptor;

	@InjectMocks
	Camera2 testee;

	@Test
	public void open() throws Exception {
		// When
		testee.open(LensPosition.FRONT);

		// Then
		verify(connection).open(LensPosition.FRONT);
	}

	@Test
	public void close() throws Exception {
		// When
		testee.close();

		// Then
		verify(connection).close();
	}

	@Test
	public void startPreview() throws Exception {
		// When
		testee.startPreview();

		// Then
		verify(sessionManager).startPreview();
	}

	@Test
	public void stopPreview() throws Exception {
		// When
		testee.stopPreview();

		// Then
		verify(sessionManager).stopPreview();
	}

	@Test
	public void setDisplaySurface() throws Exception {
		// When
		TextureView textureView = Mockito.mock(TextureView.class);
		testee.setDisplaySurface(textureView);

		// Then
		verify(textureManager).setDisplaySurface(textureView);
	}

	@Test
	public void setDisplayOrientation() throws Exception {
		// When
		testee.setDisplayOrientation(90);

		// Then
		verify(orientationManager).setDisplayOrientation(90);
	}

	@Test
	public void updateParameters() throws Exception {
		// When
		Parameters parameters = new Parameters();
		testee.updateParameters(parameters);

		// Then
		verify(parametersManager).updateParameters(parameters);
	}

	@Test
	public void getCapabilities() throws Exception {
		// Given
		Capabilities capabilities = new Capabilities(
				null,
				singleton(FocusMode.MACRO),
				null
		);
		given(capabilitiesFactory.getCapabilities())
				.willReturn(capabilities);

		// When
		Capabilities returnedCapabilities = testee.getCapabilities();

		// Then
		assertEquals(capabilities, returnedCapabilities);
	}

	@Test
	public void takePicture() throws Exception {
		// Given
		Photo photo = new Photo(new byte[0], 0);
		given(pictureCaptor.takePicture())
				.willReturn(photo);

		// When
		Photo returnedPhoto = testee.takePicture();

		// Then
		assertEquals(photo, returnedPhoto);
	}
}