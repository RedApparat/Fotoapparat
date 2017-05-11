package io.fotoapparat.hardware.v2.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.hardware.v2.stream.OnImageAcquiredObserver.OnFrameAcquiredListener;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PreviewStream2Test {

    static final Size PREVIEW_SIZE = new Size(10, 20);

    @Mock
    ParametersProvider parametersProvider;
    @Mock
    Logger logger;

    @Test
    public void acquireFrame() throws Exception {
        // Given
        given(parametersProvider.getPreviewSize())
                .willReturn(PREVIEW_SIZE);

        final AtomicReference<OnFrameAcquiredListener> listenerReference = new AtomicReference<>();
        final AtomicReference<Frame> frameReference = new AtomicReference<>();
        final CountDownLatch listenerSet = new CountDownLatch(1);
        final CountDownLatch frameAcquired = new CountDownLatch(1);

        PreviewStream2 testee = new PreviewStream2(new OnImageAcquiredObserver() {
            @Override
            public void setListener(OnFrameAcquiredListener listener) {
                listenerReference.set(listener);
                listenerSet.countDown();
            }
        }, parametersProvider, logger);

        testee.addProcessor(new FrameProcessor() {
            @Override
            public void processFrame(Frame frame) {
                frameReference.set(frame);
                frameAcquired.countDown();
            }
        });
        testee.start();

        // When
        listenerSet.await();
        listenerReference.get().onFrameAcquired(new byte[]{1});

        // Then
        frameAcquired.await();
        assertEquals(new Frame(PREVIEW_SIZE, new byte[]{1}, 0), frameReference.get());
    }
}