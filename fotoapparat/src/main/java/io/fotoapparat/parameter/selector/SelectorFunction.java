package io.fotoapparat.parameter.selector;

/**
 * Function which selects a value based on the input
 */
public interface SelectorFunction<Input, Output> {

    /**
     * @return selected value.
     */
    Output select(Input input);

}

