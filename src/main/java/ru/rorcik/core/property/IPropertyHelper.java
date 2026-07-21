package ru.rorcik.core.property;

import java.util.Map;

public interface IPropertyHelper {
    String getModId();

    default <T> Characteristic<T> registerCharacteristic(String id, Class<T> type, T defaultValue) {
        return CorePropertyRegistry.register(getModId(), id, type, defaultValue);
    }

    default <T> Characteristic<T> getCharacteristic(String id) {
        return CorePropertyRegistry.get(getModId(), id);
    }

    default Map<String, Characteristic<?>> getModCharacteristicMap() {
        return CorePropertyRegistry.getModMap(getModId());
    }
}