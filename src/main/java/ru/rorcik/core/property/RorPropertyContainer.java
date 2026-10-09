package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class RorPropertyContainer {
    private final Map<RorPropertyKey<?>, Object> values = new HashMap<>();

    public <T> void set(@NonNull RorPropertyKey<T> key, @Nullable T value) {
        set(key, value, true);
    }

    public <T> void set(@NonNull RorPropertyKey<T> key, @Nullable T value, boolean validate) {
        Objects.requireNonNull(key, "Property key cannot be null");

        if (value == null) {
            values.remove(key);
            return;
        }

        if (!key.getType().isInstance(value)) {
            throw new IllegalArgumentException("Value type mismatch for property '" + key.getFullName() +
                    "'. Expected: " + key.getType().getName() + ", got: " + value.getClass().getName());
        }

        if (validate) {
            RorPropertyDescriptor<T> descriptor = RorPropertyRegistry.getDescriptor(key);
            if (descriptor != null && !descriptor.validate(value)) {
                throw new IllegalArgumentException("Validation failed for property '" + key.getFullName() +
                        "'. Value: " + value);
            }
        }

        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull RorPropertyKey<T> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        Object value = values.get(key);
        if (value == null) {
            RorPropertyDescriptor<T> descriptor = RorPropertyRegistry.getDescriptor(key);
            return descriptor != null ? descriptor.getDefaultValue() : null;
        }
        return key.getType().cast(value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrNull(@NonNull RorPropertyKey<T> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        Object value = values.get(key);
        return value != null ? key.getType().cast(value) : null;
    }

    public <T> boolean has(@NonNull RorPropertyKey<T> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return values.containsKey(key);
    }

    public <T> boolean remove(@NonNull RorPropertyKey<T> key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return values.remove(key) != null;
    }

    public void clear() { values.clear(); }
    public int size() { return values.size(); }
    public boolean isEmpty() { return values.isEmpty(); }
    public Map<RorPropertyKey<?>, Object> getAllValues() { return Collections.unmodifiableMap(values); }

    @NonNull
    public RorPropertyContainer copy() {
        RorPropertyContainer copy = new RorPropertyContainer();
        
        copy.values.putAll(this.values);
        return copy;
    }

    public void copyFrom(@NonNull RorPropertyContainer other) {
        Objects.requireNonNull(other, "Source container cannot be null");
        this.values.clear();
        this.values.putAll(other.values);
    }

    @Override
    public String toString() {
        return "RorPropertyContainer{size=" + values.size() + ", values=" + values + "}";
    }
}