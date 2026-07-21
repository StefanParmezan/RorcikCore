package ru.rorcik.core.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.rorcik.core.property.PropertyContainer;

import java.util.Objects;

public class CapabilityHelper {
    public static @NonNull LazyOptional<PropertyContainer> getCapability(@NonNull ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        return stack.getCapability(PropertyContainerCapability.PROPERTY_CONTAINER);
    }

    public static @NonNull PropertyContainer getOrCreate(@NonNull ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        LazyOptional<PropertyContainer> cap = getCapability(stack);
        return cap.orElseGet(PropertyContainer::new);
    }

    public static boolean hasCapability(@NonNull ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        return getCapability(stack).isPresent();
    }
}