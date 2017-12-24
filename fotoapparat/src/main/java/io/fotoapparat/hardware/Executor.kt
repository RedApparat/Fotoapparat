package io.fotoapparat.hardware

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


private var taskExecutor = Executors.newSingleThreadExecutor()
    get() = field.takeUnless { it.isShutdown } ?: Executors.newSingleThreadExecutor().also { field = it }

private val cameraExecutor = Executors.newSingleThreadExecutor()
private val loggingExecutor = Executors.newSingleThreadExecutor()
private val mainThreadHandler = Handler(Looper.getMainLooper())
/**
 * [java.util.concurrent.Executor] operating the [io.fotoapparat.result.PendingResult].
 */
internal val pendingResultExecutor = Executors.newSingleThreadExecutor()
/**
 * [java.util.concurrent.Executor] operating the [io.fotoapparat.preview.PreviewStream].
 */
internal val frameProcessingExecutor = Executors.newSingleThreadExecutor()

/**
 * Shuts down all pending camera tasks.
 */
internal fun shutdownPendingTasks() {
    taskExecutor.shutdownNow()
}

/**
 * Executes a camera task.
 */
internal fun executeTask(function: Runnable) = taskExecutor.execute(function)

/**
 * Executes a camera operation.
 */
internal fun execute(function: () -> Unit) = cameraExecutor.execute(function)

/**
 * Executes an operation in the main thread.
 */
internal fun executeLoggingThread(function: () -> Unit) = loggingExecutor.execute { function() }

/**
 * Executes an operation in the main thread.
 */
internal fun executeMainThread(function: () -> Unit) = mainThreadHandler.post { function() }
