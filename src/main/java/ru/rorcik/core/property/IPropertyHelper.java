package ru.rorcik.core.property;

import java.util.Map;

public interface IPropertyHelper {
    String getModId();

    default <T> RorPropertyKey<T> registerProperty(RorPropertyKey<T> key, RorPropertyDescriptor<T> descriptor) {
        RorPropertyRegistry.register(descriptor);
        return key;
    }

    default <T> RorPropertyBuilder<T> propertyBuilder(String id, Class<T> type) {
        return RorPropertyBuilder.of(getModId(), id, type);
    }

    default <T> RorPropertyDescriptor<T> getPropertyDescriptor(RorPropertyKey<T> key) {
        return RorPropertyRegistry.getDescriptor(key);
    }

    default Map<RorPropertyKey<?>, RorPropertyDescriptor<?>> getModPropertyDescriptors() {
        return RorPropertyRegistry.getModDescriptors(getModId());
    }
}