package io.fotoapparat.util;

import org.junit.Assert;
import org.junit.Test;

import static java.util.Arrays.asList;

public class StringUtilsTest {

    @Test
    public void testJoin() throws Exception {
        // Given

        // When
        String result = StringUtils.join(", ", asList(1, 2, 3, 4, 5));

        // Then
        Assert.assertEquals(result, "1, 2, 3, 4, 5");
    }
}
