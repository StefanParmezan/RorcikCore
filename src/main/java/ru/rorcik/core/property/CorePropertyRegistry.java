package ru.rorcik.core.property;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CorePropertyRegistry {
    private static final Map<String, Map<String, Characteristic<?>>> GLOBAL_REGISTRY = new ConcurrentHashMap<>();

    public static <T> Characteristic<T> register(String modId, Characteristic<T> characteristic) {
        if (modId == null || modId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mod ID cannot be null or empty");
        }
        if (characteristic == null) {
            throw new IllegalArgumentException("Characteristic cannot be null");
        }
        GLOBAL_REGISTRY.computeIfAbsent(modId, k -> new ConcurrentHashMap<>())
                .put(characteristic.getId(), characteristic);
        return characteristic;
    }

    @SuppressWarnings("unchecked")
    public static <T> Characteristic<T> get(String modId, String id) {
        Map<String, Characteristic<?>> modMap = GLOBAL_REGISTRY.get(modId);
        return modMap == null ? null : (Characteristic<T>) modMap.get(id);
    }

    public static Map<String, Characteristic<?>> getModMap(String modId) {
        return GLOBAL_REGISTRY.getOrDefault(modId, Collections.emptyMap());
    }

    public static Map<String, Map<String, Characteristic<?>>> getAllModMaps() {
        return Collections.unmodifiableMap(GLOBAL_REGISTRY);
    }
}