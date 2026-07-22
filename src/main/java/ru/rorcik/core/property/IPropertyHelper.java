package ru.rorcik.core.property;

import ru.rorcik.core.property.builder.CharacteristicBuilder;

import java.util.Map;

public interface IPropertyHelper {
    String getModId();

    default <T> Characteristic<T> registerCharacteristic(Characteristic<T> characteristic) {
        return CorePropertyRegistry.register(getModId(), characteristic);
    }

    default <T> CharacteristicBuilder<T> characteristicBuilder(String id, Class<T> type) {
        return CharacteristicBuilder.of(id, type);
    }

    default <T> Characteristic<T> getCharacteristic(String id) {
        return CorePropertyRegistry.get(getModId(), id);
    }

    default Map<String, Characteristic<?>> getModCharacteristicMap() {
        return CorePropertyRegistry.getModMap(getModId());
    }
}