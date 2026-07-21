package ru.rorcik.core.property;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CorePropertyRegistry {
    private static final Map<String, Map<String, Characteristic<?>>> GLOBAL_REGISTRY = new ConcurrentHashMap<>();

    public static <T> Characteristic<T> register(String modId, String id, Class<T> type, T defaultValue) {
        if (modId == null || modId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mod ID cannot be null or empty");
        }
        Characteristic<T> characteristic = new Characteristic<>(id, type, defaultValue);
        GLOBAL_REGISTRY.computeIfAbsent(modId, k -> new ConcurrentHashMap<>()).put(id, characteristic);
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

    public static boolean exists(String modId, String id) {
        return get(modId, id) != null;
    }
}