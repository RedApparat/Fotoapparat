package io.fotoapparat.result;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Future;

import io.fotoapparat.result.adapter.Adapter;
import io.fotoapparat.result.transformer.Transformer;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PendingResultTest {

    static final String RESULT = "Result";
    static final Future<String> FUTURE = immediateFuture(RESULT);

    @Mock
    Transformer<String, Integer> transformer;
    @Mock
    Adapter<String, Integer> adapter;

    PendingResult<String> testee;

    @Before
    public void setUp() throws Exception {
        testee = new PendingResult<>(
                FUTURE,
                new ImmediateExecutor()
        );
    }

    @Test
    public void transform() throws Exception {
        // Given
        given(transformer.transform(RESULT))
                .willReturn(123);

        // When
        Integer result = testee.transform(transformer)
                .await();

        // Then
        assertEquals(
                Integer.valueOf(123),
                result
        );
    }

    @Test
    public void adapt() throws Exception {
        // Given
        given(adapter.adapt(FUTURE))
                .willReturn(123);

        // When
        int result = testee.adapt(adapter);

        // Then
        verify(adapter).adapt(FUTURE);

        assertEquals(123, result);
    }

    @Test
    public void await() throws Exception {
        // When
        String result = testee.await();

        // Then
        assertEquals(
                RESULT,
                result
        );
    }
}