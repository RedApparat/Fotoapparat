package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Retention(RetentionPolicy.SOURCE)
@IntDef({CameraDevice.TEMPLATE_PREVIEW,
        CameraDevice.TEMPLATE_STILL_CAPTURE,
        CameraDevice.TEMPLATE_RECORD,
        CameraDevice.TEMPLATE_VIDEO_SNAPSHOT,
        CameraDevice.TEMPLATE_ZERO_SHUTTER_LAG,
        CameraDevice.TEMPLATE_MANUAL})
@interface RequestTemplate {
}
