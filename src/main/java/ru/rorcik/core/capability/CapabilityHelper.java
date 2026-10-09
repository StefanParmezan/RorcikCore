package ru.rorcik.core.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.rorcik.core.property.RorPropertyContainer;
import java.util.Objects;

public class CapabilityHelper {
    public static @NonNull LazyOptional<RorPropertyContainer> getCapability(@NonNull ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        return stack.getCapability(PropertyContainerCapability.PROPERTY_CONTAINER);
    }

    public static boolean hasCapability(@NonNull ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        return getCapability(stack).isPresent();
    }
}