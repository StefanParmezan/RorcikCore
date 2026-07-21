package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface IPropertyHolder {
    @NonNull
    PropertyContainer getProperties();

    default <T> T getProperty(@NonNull Characteristic<T> characteristic) {
        return getProperties().get(characteristic);
    }

    default <T> void setProperty(@NonNull Characteristic<T> characteristic, T value) {
        getProperties().set(characteristic, value);
    }

    default boolean hasProperty(@NonNull Characteristic<?> characteristic) {
        return getProperties().has(characteristic);
    }
}