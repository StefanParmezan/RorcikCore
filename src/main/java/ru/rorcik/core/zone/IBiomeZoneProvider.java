package ru.rorcik.core.zone;

import net.minecraft.resources.ResourceLocation;
import java.util.Optional;

public interface IBiomeZoneProvider {
    Optional<TemperatureZone> getZone(ResourceLocation biomeId);
}