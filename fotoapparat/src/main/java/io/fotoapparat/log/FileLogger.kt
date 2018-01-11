package io.fotoapparat.log

import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Logs messages to given file.
 */
class FileLogger(private val file: File) : Logger {

    private val writer: FileWriter by lazy { FileWriter(file) }

    override fun log(message: String) {
        try {
            writer.write(message + "\n")
            writer.flush()
        } catch (e: IOException) {
            // Do nothing
        }
    }

}
