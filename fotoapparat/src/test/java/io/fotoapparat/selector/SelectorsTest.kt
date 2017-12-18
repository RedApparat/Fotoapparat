package io.fotoapparat.selector

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SelectorsTest {

    @Test
    fun `Select first available`() {
        // Given
        val options = listOf("B", "C")

        val selectorA: Iterable<String>.() -> String? = { null }
        val selectorB: Iterable<String>.() -> String? = { "B" }

        // When
        val result = firstAvailable(
                selectorA,
                selectorB
        )(options)

        // Then
        assertEquals(
                expected = "B",
                actual = result
        )
    }

    @Test
    fun `Filter selections`() {
        // Given
        lateinit var receivedStrings: Iterable<String>
        val predicate: (String) -> Boolean = {
            it.startsWith("A")
        }
        val selector: Iterable<String>.() -> String? = {
            receivedStrings = this
            "A"
        }

        // When
        val result = filtered(
                selector,
                predicate
        )(listOf(
                "A",
                "B",
                "AB"
        ))

        // Then
        assertEquals(
                expected = "A",
                actual = result
        )
        assertEquals(
                expected = listOf("A", "AB"),
                actual = receivedStrings
        )
    }

    @Test
    fun `Select single which is in the list`() {
        // When
        val result = single(
                preference = "b"
        )(listOf(
                "a",
                "b",
                "c"
        ))

        // Then
        assertEquals(
                expected = "b",
                actual = result
        )
    }

    @Test
    fun `Select single which is not in the list`() {
        // When
        val result = single(
                preference = "b"
        )(listOf(
                "a",
                "c"
        ))

        // Then
        assertEquals(
                expected = null,
                actual = result
        )
    }

    @Test
    fun `Select nothing`() {
        // When
        val result = nothing<String>()(listOf("A", "B", "C"))

        // Then
        assertNull(result)
    }

    @Test
    fun `Select highest value`() {
        // When
        val result = highest<Int>()(listOf(10, 20, 30))

        // Then
        assertEquals(
                expected = 30,
                actual = result
        )
    }

    @Test
    fun `Select highest value, but list was empty`() {
        // When
        val result = highest<Int>()(emptyList())

        // Then
        assertNull(result)
    }

    @Test
    fun `Select lowest value`() {
        // When
        val result = lowest<Int>()(listOf(10, 20, 30))

        // Then
        assertEquals(
                expected = 10,
                actual = result
        )
    }

    @Test
    fun `Select lowest value, but list was empty`() {
        // When
        val result = lowest<Int>()(emptyList())

        // Then
        assertNull(result)
    }

}