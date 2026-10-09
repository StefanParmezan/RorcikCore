package ru.rorcik.core.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import ru.rorcik.core.property.RorPropertyContainer;

public class PropertyContainerCapability {
    public static final Capability<RorPropertyContainer> PROPERTY_CONTAINER =
            CapabilityManager.get(new CapabilityToken<>() {});
}