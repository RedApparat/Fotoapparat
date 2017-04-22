package io.fotoapparat.result;

import java.util.concurrent.Future;

import io.fotoapparat.hardware.Capabilities;

/**
 * Result of capabilities acquisition.
 */
public class CapabilitiesResult {

    private final PendingResult<Capabilities> pendingResult;

    CapabilitiesResult(PendingResult<Capabilities> pendingResult) {
        this.pendingResult = pendingResult;
    }

    /**
     * Creates a new instance of advanced result from a Future result.
     *
     * @param capabilitiesFuture The future result of a {@link Capabilities}.
     * @return The result.
     */
    public static CapabilitiesResult fromFuture(Future<Capabilities> capabilitiesFuture) {
        return new CapabilitiesResult(
                PendingResult.fromFuture(capabilitiesFuture)
        );
    }

    /**
     * @return result as {@link PendingResult}.
     */
    public PendingResult<Capabilities> toPendingResult() {
        return pendingResult;
    }
}
