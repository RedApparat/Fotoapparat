package io.fotoapparat.parameter.selector;

import io.fotoapparat.parameter.range.Range;

/**
 * Created by ychen on 5/24/2017.
 */

public class SensorSensitivitySelectors {

    public static SelectorFunction<Range<Integer>, Integer> manualSensorSensitivity(final Integer value) {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                if(sensorSensitivityCapability.contains(value)) {
                    return value;
                } else {
                    return null;
                }
            }
        };
    }

    public static SelectorFunction<Range<Integer>, Integer> highestSensorSensitivity() {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                return sensorSensitivityCapability.highest();
            }
        };
    }

    public static SelectorFunction<Range<Integer>, Integer> lowestSensorSensitivity() {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> sensorSensitivityCapability) {
                return sensorSensitivityCapability.lowest();
            }
        };
    }

    public static SelectorFunction<Range<Integer>,Integer> automaticSensorSensitivity() {
        return new SelectorFunction<Range<Integer>, Integer>() {
            @Override
            public Integer select(Range<Integer> integerRange) {
                return null;
            }
        };
    }
}
