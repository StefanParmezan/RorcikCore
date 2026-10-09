package ru.rorcik.core.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import ru.rorcik.core.property.RorPropertyContainer; 
import ru.rorcik.core.util.SerializationHelper;
import java.util.Objects;

public class DynamicCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final RorPropertyContainer container; 
    private final LazyOptional<RorPropertyContainer> lazyContainer; 

    public DynamicCapabilityProvider() {
        this.container = new RorPropertyContainer(); 
        this.lazyContainer = LazyOptional.of(() -> container);
    }

    public DynamicCapabilityProvider(@NonNull RorPropertyContainer container) { 
        Objects.requireNonNull(container, "Container cannot be null");
        this.container = container;
        this.lazyContainer = LazyOptional.of(() -> container);
    }

    @Override
    public @NonNull <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PropertyContainerCapability.PROPERTY_CONTAINER) {
            return lazyContainer.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        SerializationHelper.save(container, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        RorPropertyContainer loaded = SerializationHelper.load(tag); 
        container.copyFrom(loaded);
    }

    public RorPropertyContainer getContainer() { 
        return container;
    }
}