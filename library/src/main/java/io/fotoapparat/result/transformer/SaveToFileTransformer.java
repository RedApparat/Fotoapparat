package io.fotoapparat.result.transformer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.fotoapparat.photo.Photo;

/**
 * Saves {@link Photo} to file.
 */
public class SaveToFileTransformer implements Transformer<Photo, Void> {

	private final File file;

	/**
	 * @param file output file.
	 */
	public SaveToFileTransformer(File file) {
		this.file = file;
	}

	@Override
	public Void transform(Photo input) {
		BufferedOutputStream outputStream = outputStream();

		try {
			saveImage(input, outputStream);
		} catch (IOException e) {
			throw new FileSaveException(e);
		}

		return null;
	}

	private void saveImage(Photo input, BufferedOutputStream outputStream) throws IOException {
		try {
			outputStream.write(input.encodedImage);
			outputStream.flush();
		} finally {
			outputStream.close();
		}
	}

	private BufferedOutputStream outputStream() {
		try {
			return new BufferedOutputStream(
					new FileOutputStream(file)
			);
		} catch (FileNotFoundException e) {
			throw new FileSaveException(e);
		}
	}

	/**
	 * Thrown when there is a problem while saving the file.
	 */
	public static class FileSaveException extends RuntimeException {

		public FileSaveException(Throwable cause) {
			super(cause);
		}

	}

}
