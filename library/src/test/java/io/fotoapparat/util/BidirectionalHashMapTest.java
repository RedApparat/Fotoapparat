package io.fotoapparat.util;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class BidirectionalHashMapTest {

    private Map<Integer, String> integerStringMap;

    @Before
    public void setUp() throws Exception {

        integerStringMap = new HashMap<>(3);
        integerStringMap.put(1, "Hello");
        integerStringMap.put(3, "Hi");
        integerStringMap.put(6, "Hey!");
    }

    @Test
    public void testForward() throws Exception {
        // Given

        // When
        BidirectionalHashMap<Integer, String> testee = new BidirectionalHashMap<>(integerStringMap);

        // Then
        assertEquals(integerStringMap, testee.forward());
    }

    @Test
    public void testReverse() throws Exception {
        // Given
        HashMap<String, Integer> stringIntegerMap = new HashMap<>(3);
        stringIntegerMap.put("Hello", 1);
        stringIntegerMap.put("Hi", 3);
        stringIntegerMap.put("Hey!", 6);

        // When
        BidirectionalHashMap<Integer, String> testee = new BidirectionalHashMap<>(integerStringMap);

        // Then
        assertEquals(stringIntegerMap, testee.reversed());

    }
}