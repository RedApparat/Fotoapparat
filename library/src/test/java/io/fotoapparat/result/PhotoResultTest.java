package io.fotoapparat.result;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.transformer.BitmapPhotoTransformer;
import io.fotoapparat.result.transformer.SaveToFileTransformer;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void toBitmap() throws Exception {
        // Given
        PendingResult<Photo> pendingResult = spy(PENDING_RESULT);

        PhotoResult photoResult = new PhotoResult(pendingResult);

        // When
        PendingResult<BitmapPhoto> result = photoResult.toBitmap();

        // Then
        assertNotNull(result);

        verify(pendingResult).transform(
                isA(BitmapPhotoTransformer.class)
        );
    }

    @Test
    public void saveToFile() throws Exception {
        // Given
        PendingResult<Photo> pendingResult = spy(PENDING_RESULT);

        PhotoResult photoResult = new PhotoResult(pendingResult);

        // When
        PendingResult<?> result = photoResult.saveToFile(new File(""));

        // Then
        assertNotNull(result);

        verify(pendingResult).transform(
                isA(SaveToFileTransformer.class)
        );
    }
}