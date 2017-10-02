package io.fotoapparat.result;

import java.util.concurrent.Future;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Parameters;

/**
 * Result of parameters acquisition.
 */
public class ParametersResult {

    private final PendingResult<Parameters> pendingResult;

    ParametersResult(PendingResult<Parameters> pendingResult) {
        this.pendingResult = pendingResult;
    }

    /**
     * Creates a new instance of advanced result from a Future result.
     *
     * @param parametersFuture The future result of a {@link Parameters}.
     * @return The result.
     */
    public static ParametersResult fromFuture(Future<Parameters> parametersFuture) {
        return new ParametersResult(
                PendingResult.fromFuture(parametersFuture)
        );
    }

    /**
     * @return result as {@link PendingResult}.
     */
    public PendingResult<Parameters> toPendingResult() {
        return pendingResult;
    }
}
