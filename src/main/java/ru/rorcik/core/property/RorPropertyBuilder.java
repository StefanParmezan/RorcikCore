package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Objects;
import java.util.function.Predicate;

public final class RorPropertyBuilder<T> {
    private final String modId;
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

    private RorPropertyBuilder(@NonNull String modId, @NonNull String id, @NonNull Class<T> type) {
        Objects.requireNonNull(modId, "Mod ID cannot be null");
        Objects.requireNonNull(id, "Property ID cannot be null");
        Objects.requireNonNull(type, "Property type cannot be null");
        if (modId.trim().isEmpty()) throw new IllegalArgumentException("Mod ID cannot be empty");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("Property ID cannot be empty");
        this.modId = modId;
        this.id = id;
        this.type = type;
    }

    public static <T> RorPropertyBuilder<T> of(@NonNull String modId, @NonNull String id, @NonNull Class<T> type) {
        return new RorPropertyBuilder<>(modId, id, type);
    }

    public RorPropertyBuilder<T> defaultValue(@NonNull T value) { this.defaultValue = value; return this; }
    public RorPropertyBuilder<T> description(@NonNull String description) { this.description = description; return this; }
    public RorPropertyBuilder<T> category(@NonNull String category) { this.category = category; return this; }
    public RorPropertyBuilder<T> transientFlag(boolean flag) { this.transientFlag = flag; return this; }
    public RorPropertyBuilder<T> syncToClient(boolean flag) { this.syncToClient = flag; return this; }
    public RorPropertyBuilder<T> validator(@NonNull Predicate<T> validator) { this.validator = validator; return this; }

    @SuppressWarnings("unchecked")
    public RorPropertyBuilder<T> min(@NonNull T min) {
        if (!(min instanceof Comparable)) throw new IllegalArgumentException("Min value must be Comparable");
        this.minValue = min; return this;
    }

    @SuppressWarnings("unchecked")
    public RorPropertyBuilder<T> max(@NonNull T max) {
        if (!(max instanceof Comparable)) throw new IllegalArgumentException("Max value must be Comparable");
        this.maxValue = max; return this;
    }

    public RorPropertyKey<T> buildAndRegister() {
        if (defaultValue == null) throw new IllegalStateException("Default value must be set");
        RorPropertyKey<T> key = new RorPropertyKey<>(modId, id, type);
        RorPropertyDescriptor<T> descriptor = new RorPropertyDescriptor<>(key, defaultValue);
        descriptor.setDescription(description);
        descriptor.setCategory(category);
        descriptor.setTransient(transientFlag);
        descriptor.setSyncToClient(syncToClient);
        descriptor.setValidator(validator);
        descriptor.setMinValue(minValue);
        descriptor.setMaxValue(maxValue);
        RorPropertyRegistry.register(descriptor);
        return key;
    }
}