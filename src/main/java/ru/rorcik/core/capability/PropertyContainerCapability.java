package ru.rorcik.core.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import ru.rorcik.core.RorcikCore;
import ru.rorcik.core.property.PropertyContainer;

public class PropertyContainerCapability {
    public static final Capability<PropertyContainer> PROPERTY_CONTAINER =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static void register() {
        RorcikCore.LOGGER.debug("Registering PropertyContainer capability");
    }
}