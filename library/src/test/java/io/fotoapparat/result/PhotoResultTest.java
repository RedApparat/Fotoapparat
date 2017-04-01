package io.fotoapparat.result;

import org.junit.Test;

import io.fotoapparat.photo.Photo;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertSame;

public class PhotoResultTest {

	static final PendingResult<Photo> PENDING_RESULT = new PendingResult<>(
			immediateFuture(
					Photo.empty()
			),
			new ImmediateExecutor()
	);

	@Test
	public void toPendingResult() throws Exception {
		// Given
		PhotoResult photoResult = new PhotoResult(PENDING_RESULT);

		// When
		PendingResult<Photo> pendingResult = photoResult.toPendingResult();

		// Then
		assertSame(
				PENDING_RESULT,
				pendingResult
		);
	}
}