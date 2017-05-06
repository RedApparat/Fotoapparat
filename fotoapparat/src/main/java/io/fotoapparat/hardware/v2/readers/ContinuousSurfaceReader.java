package io.fotoapparat.hardware.v2.readers;

import android.graphics.ImageFormat;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.nio.ByteBuffer;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.hardware.v2.stream.OnImageAcquiredObserver;
import io.fotoapparat.parameter.Size;

/**
 * Creates a {@link Surface} which can capture continuous events (several frames).
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ContinuousSurfaceReader
        implements OnImageAcquiredObserver, ImageReader.OnImageAvailableListener {

    private final ParametersProvider parametersProvider;
    private final CameraThread cameraThread;
    private ImageReader imageReader;
    private OnFrameAcquiredListener listener;

    public ContinuousSurfaceReader(ParametersProvider parametersProvider, CameraThread cameraThread) {
        this.parametersProvider = parametersProvider;
        this.cameraThread = cameraThread;
    }

    private static byte[] YUV_420_888toNV21(Image image) {
        byte[] nv21;
        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
        ByteBuffer uBuffer = image.getPlanes()[1].getBuffer();
        ByteBuffer vBuffer = image.getPlanes()[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        nv21 = new byte[ySize + uSize + vSize];

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        return nv21;
    }

    @Override
    public void onImageAvailable(ImageReader reader) {
        Image image = reader.acquireNextImage();
        Image.Plane[] planes = image.getPlanes();

        if (planes.length > 0) {
            byte[] bytes = YUV_420_888toNV21(image);

            if (listener != null) {
                listener.onFrameAcquired(bytes);
            }
        }
        image.close();
    }

    /**
     * Returns a {@link Surface} which can be used as a target for continuous capture events.
     *
     * @return the new Surface
     */
    public Surface getSurface() {
        if (imageReader == null) {
            createImageReader();
        }
        return imageReader.getSurface();
    }

    private void createImageReader() {
        Size previewSize = parametersProvider.getPreviewSize();

        imageReader = ImageReader
                .newInstance(
                        previewSize.width,
                        previewSize.height,
                        ImageFormat.YUV_420_888,
                        1
                );

        imageReader.setOnImageAvailableListener(
                this,
                cameraThread.createHandler()
        );
    }

    @Override
    public void setListener(OnFrameAcquiredListener listener) {
        this.listener = listener;
    }
}
