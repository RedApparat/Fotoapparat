package io.fotoapparat.result;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.test.ImmediateExecutor;

import static io.fotoapparat.test.TestUtils.immediateFuture;
import static junit.framework.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class CapabilitiesResultTest {

    static final PendingResult<Capabilities> PENDING_RESULT = new PendingResult<>(
            immediateFuture(
                    Capabilities.empty()
            ),
            new ImmediateExecutor()
    );

    @Test
    public void toPendingResult() throws Exception {
        // Given
        CapabilitiesResult photoResult = new CapabilitiesResult(PENDING_RESULT);

        // When
        PendingResult<Capabilities> pendingResult = photoResult.toPendingResult();

        // Then
        assertSame(
                PENDING_RESULT,
                pendingResult
        );
    }

}