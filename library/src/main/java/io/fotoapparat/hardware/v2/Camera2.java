package io.fotoapparat.hardware.v2;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Collections;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.Parameters;
import io.fotoapparat.photo.Photo;

/**
 * Camera hardware driver for v2 {@link Camera2} API.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2 implements CameraDevice {


    private final CameraManager manager;
    public android.hardware.camera2.CameraDevice camera;
    private CameraCaptureSession session;

    public Camera2(Context context) {
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void open(Parameters parameters) {
        try {
            String[] cameraIdList = manager.getCameraIdList();

            // todo choose camera

            OpenCameraCallback openCameraCallback = new OpenCameraCallback();
            manager.openCamera(cameraIdList[0], openCameraCallback, null);

            camera = openCameraCallback.getCamera();


        } catch (CameraAccessException | InterruptedException e) {
            throw new CameraException(e);
        }
    }

    @Override
    public void close() {
        if (camera != null) {
            camera.close();
        }
        if (session != null) {
            session.close();
        }

    }

    @Override
    public void startPreview() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void setDisplaySurface(Object displaySurface) {
        try {
            CaptureSessionCallback captureSession = new CaptureSessionCallback();
            camera.createCaptureSession(Collections.singletonList((Surface) displaySurface), captureSession, null);

            session = captureSession.getSession();

        } catch (InterruptedException | CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Photo takePicture() {
        try {
            CapturePhotoCallback photoCapture = new CapturePhotoCallback();
            session.capture(null, photoCapture, null);
            TotalCaptureResult result = photoCapture.getResult();
        } catch (CameraAccessException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
