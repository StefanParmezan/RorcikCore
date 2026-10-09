package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class RorPropertyRegistry {
    private static final Map<RorPropertyKey<?>, RorPropertyDescriptor<?>> DESCRIPTORS = new ConcurrentHashMap<>();
    private static final Map<String, RorPropertyKey<?>> BY_NAME = new ConcurrentHashMap<>();

    public static <T> void register(@NonNull RorPropertyDescriptor<T> descriptor) {
        Objects.requireNonNull(descriptor, "Descriptor cannot be null");
        RorPropertyKey<?> key = descriptor.getKey();

        if (DESCRIPTORS.putIfAbsent(key, descriptor) != null) {
            throw new IllegalStateException("Property already registered: " + key.getFullName());
        }
        BY_NAME.put(key.getFullName(), key);
    }

    @SuppressWarnings("unchecked")
    public static <T> RorPropertyDescriptor<T> getDescriptor(@NonNull RorPropertyKey<T> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return (RorPropertyDescriptor<T>) DESCRIPTORS.get(key);
    }

    public static boolean isRegistered(@NonNull RorPropertyKey<?> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return DESCRIPTORS.containsKey(key);
    }

    public static RorPropertyKey<?> findKeyByName(@NonNull String fullName) {
        return BY_NAME.get(fullName);
    }

    public static Map<RorPropertyKey<?>, RorPropertyDescriptor<?>> getModDescriptors(@NonNull String modId) {
        Objects.requireNonNull(modId, "Mod ID cannot be null");
        return DESCRIPTORS.entrySet().stream()
                .filter(e -> e.getKey().getModId().equals(modId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<RorPropertyKey<?>, RorPropertyDescriptor<?>> getAllDescriptors() {
        return Collections.unmodifiableMap(DESCRIPTORS);
    }

    public static void clear() {
        DESCRIPTORS.clear();
        BY_NAME.clear();
    }
}