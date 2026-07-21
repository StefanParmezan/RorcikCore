package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Characteristic<T> {
    private final String id;
    private final Class<T> type;
    private final T defaultValue;

    public Characteristic(@NonNull String id, @NonNull Class<T> type, T defaultValue) {
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("Characteristic ID cannot be empty");
        }

        this.id = id;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getId() {
        return id;
    }

    public Class<T> getType() {
        return type;
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}