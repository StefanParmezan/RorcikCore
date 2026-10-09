package ru.rorcik.core.property;

import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Objects;

public class StatModifier {
    private final RorPropertyKey<?> target;
    private final float value;
    private final Operation operation;

    public enum Operation { ADD, MULTIPLY }

    public StatModifier(@NonNull RorPropertyKey<?> target, float value, @NonNull Operation operation) {
        Objects.requireNonNull(target, "Target key cannot be null");
        Objects.requireNonNull(operation, "Operation cannot be null");
        this.target = target;
        this.value = value;
        this.operation = operation;
    }

    public static StatModifier add(@NonNull RorPropertyKey<?> target, float amount) {
        return new StatModifier(target, amount, Operation.ADD);
    }

    public static StatModifier multiply(@NonNull RorPropertyKey<?> target, float multiplier) {
        return new StatModifier(target, multiplier, Operation.MULTIPLY);
    }

    public RorPropertyKey<?> getTarget() { return target; }
    public float getValue() { return value; }
    public Operation getOperation() { return operation; }

    @SuppressWarnings("unchecked")
    public <T> T apply(@NonNull T baseValue) {
        Objects.requireNonNull(baseValue, "Base value cannot be null");
        if (baseValue instanceof Float) return (T) (Object) applyToFloat((Float) baseValue);
        else if (baseValue instanceof Double) return (T) (Object) applyToDouble((Double) baseValue);
        else if (baseValue instanceof Integer) return (T) (Object) applyToInteger((Integer) baseValue);
        else if (baseValue instanceof Long) return (T) (Object) applyToLong((Long) baseValue);
        else throw new IllegalArgumentException("StatModifier can only be applied to numeric types. Got: "
                    + baseValue.getClass().getName());
    }

    private float applyToFloat(float base) {
        return operation == Operation.ADD ? base + value : base * value;
    }

    private double applyToDouble(double base) {
        return operation == Operation.ADD ? base + value : base * value;
    }

    private int applyToInteger(int base) {
        return operation == Operation.ADD
                ? base + (int) value
                : (int) (base * (double) value);
    }

    private long applyToLong(long base) {
        return operation == Operation.ADD
                ? base + (long) value
                : (long) (base * (double) value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatModifier other)) return false;
        return Float.compare(other.value, value) == 0 && target.equals(other.target) && operation == other.operation;
    }

    @Override
    public int hashCode() { return Objects.hash(target, value, operation); }

    @Override
    public String toString() {
        return "StatModifier{target=" + target.getFullName() + ", value=" + value + ", operation=" + operation + "}";
    }
}