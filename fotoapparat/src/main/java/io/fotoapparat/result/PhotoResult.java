package io.fotoapparat.result;

import android.graphics.Bitmap;

import java.io.File;
import java.util.concurrent.Future;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.photo.Photo;
import io.fotoapparat.result.transformer.BitmapPhotoTransformer;
import io.fotoapparat.result.transformer.SaveToFileTransformer;
import io.fotoapparat.result.transformer.Transformer;

import static io.fotoapparat.result.transformer.SizeTransformers.originalSize;

/**
 * Result of taking the photo.
 */
public class PhotoResult {

    private final PendingResult<Photo> pendingResult;

    PhotoResult(PendingResult<Photo> pendingResult) {
        this.pendingResult = pendingResult;
    }

    /**
     * Creates a new instance of advanced result from a Future result.
     *
     * @param photoFuture The future result of a {@link Photo}.
     * @return The result.
     */
    public static PhotoResult fromFuture(Future<Photo> photoFuture) {
        return new PhotoResult(
                PendingResult.fromFuture(photoFuture)
        );
    }

    /**
     * Converts result to {@link Bitmap} with the same size as original photo.
     *
     * @return result as pending {@link BitmapPhoto} which will be available at some point in the
     * future.
     */
    public PendingResult<BitmapPhoto> toBitmap() {
        return toBitmap(originalSize());
    }

    /**
     * Converts result to {@link Bitmap} of size provided by {@link Transformer}.
     *
     * @param sizeTransformer Given the original size of the photo, returns the updated size so that
     *                        photo will be downscaled, upscaled or unchanged.
     * @return result as pending {@link BitmapPhoto} which will be available at some point in the
     * future.
     */
    public PendingResult<BitmapPhoto> toBitmap(Transformer<Size, Size> sizeTransformer) {
        return pendingResult
                .transform(new BitmapPhotoTransformer(sizeTransformer));
    }

    /**
     * Saves result to file.
     *
     * @return pending operation which completes when photo is saved to file.
     */
    public PendingResult<Void> saveToFile(File file) {
        return pendingResult
                .transform(SaveToFileTransformer.create(file));
    }

    /**
     * @return result as {@link PendingResult}.
     */
    public PendingResult<Photo> toPendingResult() {
        return pendingResult;
    }

}
