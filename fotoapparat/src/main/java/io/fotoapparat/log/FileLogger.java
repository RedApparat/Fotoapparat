package io.fotoapparat.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Logs messages to given file.
 */
class FileLogger implements Logger {

    private final File file;
    private FileWriter writer;

    FileLogger(File file) {
        this.file = file;
    }

    @Override
    public void log(String message) {
        try {
            ensureWriterInitialized();

            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            // Do nothing
        }
    }

    private void ensureWriterInitialized() throws IOException {
        if (writer == null) {
            writer = new FileWriter(file);
        }
    }

}
