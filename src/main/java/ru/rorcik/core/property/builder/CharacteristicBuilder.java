package ru.rorcik.core.property.builder;

import org.checkerframework.checker.nullness.qual.NonNull;
import ru.rorcik.core.property.Characteristic;

import java.util.Objects;
import java.util.function.Predicate;

public class CharacteristicBuilder<T> {
    private final String id;
    private final Class<T> type;
    private T defaultValue;
    private String description = "";
    private String category = "default";
    private boolean transientFlag = false;
    private boolean syncToClient = false;
    private Predicate<T> validator = null;
    private T minValue = null;
    private T maxValue = null;

    private CharacteristicBuilder(@NonNull String id, @NonNull Class<T> type) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(type, "Type cannot be null");
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        this.id = id;
        this.type = type;
    }

    public static <T> CharacteristicBuilder<T> of(@NonNull String id, @NonNull Class<T> type) {
        return new CharacteristicBuilder<>(id, type);
    }

    public CharacteristicBuilder<T> defaultValue(@NonNull T value) {
        this.defaultValue = value;
        return this;
    }

    public CharacteristicBuilder<T> description(@NonNull String description) {
        this.description = description;
        return this;
    }

    public CharacteristicBuilder<T> category(@NonNull String category) {
        this.category = category;
        return this;
    }

    public CharacteristicBuilder<T> transientFlag(boolean flag) {
        this.transientFlag = flag;
        return this;
    }

    public CharacteristicBuilder<T> syncToClient(boolean flag) {
        this.syncToClient = flag;
        return this;
    }

    public CharacteristicBuilder<T> validator(@NonNull Predicate<T> validator) {
        this.validator = validator;
        return this;
    }

    @SuppressWarnings("unchecked")
    public CharacteristicBuilder<T> min(@NonNull T min) {
        if (!(min instanceof Comparable)) {
            throw new IllegalArgumentException("Min value must be Comparable");
        }
        this.minValue = min;
        return this;
    }

    @SuppressWarnings("unchecked")
    public CharacteristicBuilder<T> max(@NonNull T max) {
        if (!(max instanceof Comparable)) {
            throw new IllegalArgumentException("Max value must be Comparable");
        }
        this.maxValue = max;
        return this;
    }

    public Characteristic<T> build() {
        if (defaultValue == null) {
            throw new IllegalStateException("Default value must be set");
        }

        Characteristic<T> characteristic = new Characteristic<>(id, type, defaultValue);
        characteristic.setDescription(description);
        characteristic.setCategory(category);
        characteristic.setTransient(transientFlag);
        characteristic.setSyncToClient(syncToClient);
        characteristic.setValidator(validator);
        characteristic.setMinValue(minValue);
        characteristic.setMaxValue(maxValue);

        return characteristic;
    }
}