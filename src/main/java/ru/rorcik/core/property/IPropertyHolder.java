package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface IPropertyHolder {
    @NonNull RorPropertyContainer getProperties();
    default <T> T getProperty(@NonNull RorPropertyKey<T> key) { return getProperties().get(key); }
    default <T> void setProperty(@NonNull RorPropertyKey<T> key, T value) { getProperties().set(key, value); }
    default boolean hasProperty(@NonNull RorPropertyKey<?> key) { return getProperties().has(key); }
}