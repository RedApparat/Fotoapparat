package io.fotoapparat.parameter.selector;

import io.fotoapparat.hardware.BaseSensorSensitivityCapability;

/**
 * Created by ychen on 5/24/2017.
 */

public class SensorSensitivitySelectors {

    public static SelectorFunction<BaseSensorSensitivityCapability, Integer> manualSensorSensitivity(final Integer value) {
        return new SelectorFunction<BaseSensorSensitivityCapability, Integer>() {
            @Override
            public Integer select(BaseSensorSensitivityCapability sensorSensitivityCapability) {
                if(sensorSensitivityCapability.supports(value)) {
                    return value;
                } else {
                    return null;
                }
            }
        };
    };

    public static SelectorFunction<BaseSensorSensitivityCapability, Integer> highestSensorSensitivity() {
        return new SelectorFunction<BaseSensorSensitivityCapability, Integer>() {
            @Override
            public Integer select(BaseSensorSensitivityCapability sensorSensitivityCapability) {
                return sensorSensitivityCapability.highest();
            }
        };
    }

    public static SelectorFunction<BaseSensorSensitivityCapability, Integer> lowestSensorSensitivity() {
        return new SelectorFunction<BaseSensorSensitivityCapability, Integer>() {
            @Override
            public Integer select(BaseSensorSensitivityCapability sensorSensitivityCapability) {
                return sensorSensitivityCapability.lowest();
            }
        };
    }
}
