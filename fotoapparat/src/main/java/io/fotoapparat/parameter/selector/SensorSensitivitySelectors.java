package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.range.Range;

/**
 * Selector functions for sensor sensitivity (ISO).
 */
public class SensorSensitivitySelectors {

    /**
     * @param iso the specified ISO value
     * @return {@link SelectorFunction} which selects the specified ISO value.
     *         If there is no specified value - selects default ISO value.
     */
    public static SelectorFunction<Range<Integer>, Integer> manualSensorSensitivity(final int iso) {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                if(sensorSensitivityCapability.contains(iso)) {
                    return iso;
                } else {
                    return null;
                }
            }
        };
    }

    /**
     * @return {@link SelectorFunction} which selects highest ISO value.
     */
    public static SelectorFunction<Range<Integer>, Integer> highestSensorSensitivity() {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                return sensorSensitivityCapability.highest();
            }
        };
    }

    /**
     * @return {@link SelectorFunction} which selects lowest ISO value.
     */
    public static SelectorFunction<Range<Integer>, Integer> lowestSensorSensitivity() {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                return sensorSensitivityCapability.lowest();
            }
        };
    }
}
