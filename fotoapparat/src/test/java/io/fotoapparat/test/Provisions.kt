package io.fotoapparat.test

import org.mockito.BDDMockito.given

/**
 * Delegates the [org.mockito.BDDMockito.given] / [org.mockito.BDDMockito.BDDMyOngoingStubbing.willReturn] pattern
 */
internal inline infix fun <reified T> T.willReturn(item: T) {
    given(this).willReturn(item)
}

/**
 * Delegates the [org.mockito.BDDMockito.given] / [org.mockito.BDDMockito.BDDMyOngoingStubbing.willThrow] pattern
 */
internal inline infix fun <reified T> T.willThrow(item: Exception) {
    given(this).willThrow(item)
}
