package io.fotoapparat.parameter.selector;

import android.support.annotation.Nullable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import static io.fotoapparat.test.TestUtils.asSet;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SelectorsTest {

    @Mock
    SelectorFunction<Collection<String>, String> functionA;
    @Mock
    SelectorFunction<Collection<String>, String> functionB;

    @Test
    public void firstAvailable() throws Exception {
        // Given
        List<String> options = asList("B", "C");

        given(functionA.select(options))
                .willReturn(null);

        given(functionB.select(options))
                .willReturn("B");

        // When
        String result = Selectors
                .firstAvailable(
                        functionA,
                        functionB
                )
                .select(options);

        // Then
        assertEquals("B", result);
    }

    @Test
    public void filtered() throws Exception {
        // Given
        SelectorFunction<Collection<String>, String> function = Selectors.filtered(
                functionA,
                new Predicate<String>() {
                    @Override
                    public boolean condition(@Nullable String value) {
                        return value != null && value.startsWith("A");
                    }
                }
        );

        given(functionA.select(ArgumentMatchers.<String>anyCollection()))
                .willReturn("A");

        // When
        String result = function.select(asList("A", "B", "AB"));

        // Then
        assertEquals("A", result);

        verify(functionA).select(asSet("A", "AB"));
    }

    @Test
    public void single() throws Exception {
        // When
        String result = Selectors
                .single("b")
                .select(asList(
                        "a", "b", "c"
                ));

        // Then
        assertEquals("b", result);
    }

    @Test
    public void single_NotFound() throws Exception {
        // When
        String result = Selectors
                .single("b")
                .select(asList(
                        "a", "c"
                ));

        // Then
        assertEquals(null, result);
    }

    @Test
    public void nothing() throws Exception {
        // When
        String result = Selectors.<Collection<String>, String>nothing()
                .select(asList("A", "B", "C"));

        // Then
        assertNull(result);
    }
}