package io.fotoapparat.test

import java.util.concurrent.Executor

/**
 * [Executor] which executes operation immediately.
 */
object ImmediateExecutor : Executor {

    override fun execute(command: Runnable) {
        command.run()
    }

}