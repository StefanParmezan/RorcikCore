package ru.rorcik.core.registry;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoreIdentifiableRegistry {
    private static final Map<String, IStringIdentifiable> REGISTRY = new ConcurrentHashMap<>();

    public static void register(@NonNull IStringIdentifiable object) {
        String name = object.getRegistryName();
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Registry name cannot be null or empty");
        }
        if (REGISTRY.containsKey(name)) {
            throw new IllegalStateException("Registry name already exists: " + name);
        }
        REGISTRY.put(name, object);
    }

    public static IStringIdentifiable get(String registryName) {
        return REGISTRY.get(registryName);
    }

    public static boolean exists(String registryName) {
        return REGISTRY.containsKey(registryName);
    }

    public static Map<String, IStringIdentifiable> getAll() {
        return Collections.unmodifiableMap(REGISTRY);
    }
}