package io.fotoapparat.hardware.v2.capabilities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Range;

import io.fotoapparat.hardware.BaseSensorSensitivityCapability;

/**
 * Created by ychen on 5/25/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SensorSensitivityCapability extends BaseSensorSensitivityCapability {
    Range<Integer> range;

    public SensorSensitivityCapability(Range<Integer> range) {
        this.range = range;
    }

    @Override
    public boolean supports(Integer value) {
        return range.contains(value);
    }

    @Override
    public Integer highest() {
        return range.getUpper();
    }

    @Override
    public Integer lowest() {
        return range.getLower();
    }
}
