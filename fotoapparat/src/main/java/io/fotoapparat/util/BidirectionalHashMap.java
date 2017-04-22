package io.fotoapparat.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Immutable map in which values might be used as keys and keys as values.
 */
public class BidirectionalHashMap<K, V> {

    private final Map<K, V> keyToValue = new HashMap<>();
    private final Map<V, K> valueToKey = new HashMap<>();

    public BidirectionalHashMap(Map<K, V> keyToValue) {
        this.keyToValue.putAll(keyToValue);

        for (Map.Entry<K, V> entry : keyToValue.entrySet()) {
            valueToKey.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Returns the normal view of this map as in a normal {@link Map}
     *
     * @return the normal view of this map
     */
    public Map<K, V> forward() {
        return Collections.unmodifiableMap(keyToValue);
    }

    /**
     * Returns the reversed view of this map, which maps each of its
     * values to its associated key.
     *
     * @return the inverse view of this map
     */
    public Map<V, K> reversed() {
        return Collections.unmodifiableMap(valueToKey);
    }

}
