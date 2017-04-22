package io.fotoapparat.log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.io.FileUtils.readFileToString;

public class FileLoggerTest {

    static final File FILE = new File("test");

    FileLogger testee;

    @Before
    public void setUp() throws Exception {
        ensureFileDeleted();

        testee = new FileLogger(FILE);
    }

    @Test
    public void writeMessage() throws Exception {
        // When
        testee.log("message");
        testee.log("message");

        // Then
        assertEquals(
                "message\nmessage\n",
                readFileToString(FILE, Charset.defaultCharset())
        );
    }

    @Test
    public void ignoreExceptions() throws Exception {
        // Given
        FileLogger logger = new FileLogger(new File("/"));

        // When
        logger.log("message");

        // Then
        // No exception is thrown
    }

    @After
    public void tearDown() throws Exception {
        ensureFileDeleted();
    }

    private void ensureFileDeleted() {
        if (FILE.exists() && !FILE.delete()) {
            throw new IllegalStateException("Test file still exists: " + FILE);
        }
    }

}