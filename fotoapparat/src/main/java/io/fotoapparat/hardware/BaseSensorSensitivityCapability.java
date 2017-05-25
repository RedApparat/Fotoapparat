package io.fotoapparat.hardware;

/**
 * Created by ychen on 5/25/2017.
 */

public abstract class BaseSensorSensitivityCapability {
    abstract public boolean supports(Integer value);
    abstract public Integer highest();
    abstract public Integer lowest();

    public String toString() {
        return String.format("%1$ to %2$", lowest(), highest());
    }

    public static BaseSensorSensitivityCapability EMPTY = new BaseSensorSensitivityCapability() {
        @Override
        public boolean supports(Integer value) {
            return false;
        }

        @Override
        public Integer highest() {
            return null;
        }

        @Override
        public Integer lowest() {
            return null;
        }
    };
}
