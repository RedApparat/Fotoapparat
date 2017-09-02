package io.fotoapparat.parameter.update;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.selector.SelectorFunction;

import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class UpdateRequestTest {

    @Mock
    SelectorFunction<Collection<Flash>, Flash> flashSelector;
    @Mock
    SelectorFunction<Collection<FocusMode>, FocusMode> focusModeSelector;

    @Test
    public void build() throws Exception {
        // When
        UpdateRequest updateRequest = UpdateRequest.builder()
                .flash(flashSelector)
                .focusMode(focusModeSelector)
                .build();

        // Then
        assertSame(flashSelector, updateRequest.flashSelector);
        assertSame(focusModeSelector, updateRequest.focusModeSelector);
    }

}