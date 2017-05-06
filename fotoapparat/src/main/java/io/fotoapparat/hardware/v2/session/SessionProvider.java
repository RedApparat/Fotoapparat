package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import java.util.Arrays;
import java.util.List;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.readers.ContinuousSurfaceReader;
import io.fotoapparat.hardware.v2.readers.StillSurfaceReader;
import io.fotoapparat.hardware.v2.surface.TextureManager;

/**
 * Provides {@link PreviewSession}s for the currently given display surface.
 */
@SuppressWarnings("NewApi")
public class SessionProvider implements TextureManager.Listener {

    private PreviewSession previewSession;
    private StillSurfaceReader surfaceReader;
    private ContinuousSurfaceReader continuousSurfaceReader;
    private CameraConnection connection;
    private CaptureRequestFactory captureRequestFactory;

    public SessionProvider(StillSurfaceReader surfaceReader,
                           ContinuousSurfaceReader continuousSurfaceReader,
                           CameraConnection connection,
                           CaptureRequestFactory captureRequestFactory,
                           TextureManager textureManager) {
        this.surfaceReader = surfaceReader;
        this.continuousSurfaceReader = continuousSurfaceReader;
        this.connection = connection;
        this.captureRequestFactory = captureRequestFactory;
        textureManager.setListener(this);
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {
        CameraDevice camera = connection.getCamera();

        Surface captureSurface = surfaceReader.getSurface();
        Surface frameSurface = continuousSurfaceReader.getSurface();
        List<Surface> surfaces = Arrays.asList(surface, captureSurface, frameSurface);

        try {
            CaptureRequest previewRequest = captureRequestFactory.createPreviewRequest();

            previewSession = new PreviewSession(
                    camera,
                    previewRequest,
                    surfaces
            );
        } catch (CameraAccessException e) {
            throw new CameraException(e);
        }
    }

    /**
     * @return The {@link PreviewSession} for the given display surface.
     */
    public PreviewSession getPreviewSession() {
        if (previewSession == null) {
            throw new IllegalStateException("Target display surface has not been set.");
        }
        return previewSession;
    }

}
