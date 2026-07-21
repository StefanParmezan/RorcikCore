package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PropertyContainer {
    private final Map<Characteristic<?>, Object> values = new HashMap<>();

    public <T> void set(@NonNull Characteristic<T> characteristic, @Nullable T value) {
        Objects.requireNonNull(characteristic, "Characteristic cannot be null");
        if (value == null) {
            values.remove(characteristic);
        } else if (!characteristic.getType().isInstance(value)) {
            throw new IllegalArgumentException(
                    "Value type mismatch for characteristic '" + characteristic.getId() +
                            "'. Expected: " + characteristic.getType().getName() +
                            ", got: " + value.getClass().getName()
            );
        } else {
            values.put(characteristic, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Characteristic<T> characteristic) {
        Objects.requireNonNull(characteristic, "Characteristic cannot be null");
        Object value = values.get(characteristic);
        if (value == null) {
            return characteristic.getDefaultValue();
        }
        return characteristic.getType().cast(value);
    }

    public <T> boolean has(@NonNull Characteristic<T> characteristic) {
        Objects.requireNonNull(characteristic, "Characteristic cannot be null");
        return values.containsKey(characteristic);
    }

    public <T> boolean remove(@NonNull Characteristic<T> characteristic) {
        Objects.requireNonNull(characteristic, "Characteristic cannot be null");
        return values.remove(characteristic) != null;
    }

    public void clear() {
        values.clear();
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Map<Characteristic<?>, Object> getAllValues() {
        return Collections.unmodifiableMap(values);
    }

    @NonNull
    public PropertyContainer copy() {
        PropertyContainer copy = new PropertyContainer();
        copy.values.putAll(this.values);
        return copy;
    }

    public void copyFrom(@NonNull PropertyContainer other) {
        Objects.requireNonNull(other, "Source container cannot be null");
        this.values.clear();
        this.values.putAll(other.values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyContainer other)) return false;
        return values.equals(other.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "PropertyContainer{size=" + values.size() + ", values=" + values + "}";
    }
}