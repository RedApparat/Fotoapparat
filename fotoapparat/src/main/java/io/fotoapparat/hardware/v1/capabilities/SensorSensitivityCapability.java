package io.fotoapparat.hardware.v1.capabilities;

import java.util.Collection;
import java.util.Collections;

import io.fotoapparat.hardware.BaseSensorSensitivityCapability;

/**
 * Created by ychen on 5/25/2017.
 */

public class SensorSensitivityCapability extends BaseSensorSensitivityCapability {
    Collection<Integer> values;
    public SensorSensitivityCapability(Collection<Integer> values) {
        this.values = values;
    }

    @Override
    public boolean supports(Integer value) {
        return values.contains(value);
    }

    @Override
    public Integer highest() {
        return Collections.max(values);
    }

    @Override
    public Integer lowest() {
        return Collections.min(values);
    }
}
