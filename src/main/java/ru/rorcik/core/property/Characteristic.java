package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class Characteristic<T> {
    private final String id;
    private final Class<T> type;
    private final T defaultValue;

    private String description = "";
    private String category = "default";
    private boolean transientFlag = false;
    private boolean syncToClient = false;
    private Predicate<T> validator = null;
    private T minValue = null;
    private T maxValue = null;

    public Characteristic(@NonNull String id, @NonNull Class<T> type, @Nullable T defaultValue) {
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("Characteristic ID cannot be null or empty");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isTransient() {
        return transientFlag;
    }

    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }

    public boolean isSyncToClient() {
        return syncToClient;
    }

    public void setSyncToClient(boolean syncToClient) {
        this.syncToClient = syncToClient;
    }

    public Predicate<T> getValidator() {
        return validator;
    }

    public void setValidator(Predicate<T> validator) {
        this.validator = validator;
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    @SuppressWarnings("unchecked")
    public boolean validate(@NonNull T value) {
        Objects.requireNonNull(value, "Value cannot be null");

        if (validator != null && !validator.test(value)) {
            return false;
        }

        if (minValue != null && value instanceof Comparable) {
            Comparable<T> comparable = (Comparable<T>) value;
            if (comparable.compareTo(minValue) < 0) {
                return false;
            }
        }

        if (maxValue != null && value instanceof Comparable) {
            Comparable<T> comparable = (Comparable<T>) value;
            return comparable.compareTo(maxValue) <= 0;
        }

        return true;
    }
}