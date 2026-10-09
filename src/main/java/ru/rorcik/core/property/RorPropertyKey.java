package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Objects;

public final class RorPropertyKey<T> {
    private final String modId;
    private final String id;
    private final Class<T> type;

    public RorPropertyKey(@NonNull String modId, @NonNull String id, @NonNull Class<T> type) {
        Objects.requireNonNull(modId, "Mod ID cannot be null");
        Objects.requireNonNull(id, "Property ID cannot be null");
        Objects.requireNonNull(type, "Property type cannot be null");
        if (modId.trim().isEmpty()) throw new IllegalArgumentException("Mod ID cannot be empty");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("Property ID cannot be empty");
        this.modId = modId;
        this.id = id;
        this.type = type;
    }

    public String getModId() { return modId; }
    public String getId() { return id; }
    public Class<T> getType() { return type; }
    public String getFullName() { return modId + ":" + id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RorPropertyKey<?> that)) return false;
        return modId.equals(that.modId) && id.equals(that.id) && type.equals(that.type);
    }

    @Override
    public int hashCode() { return Objects.hash(modId, id, type); }
    @Override
    public String toString() { return "RorPropertyKey{" + getFullName() + ", type=" + type.getSimpleName() + "}"; }
}