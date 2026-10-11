package ru.rorcik.core.zone;

import net.minecraft.resources.ResourceLocation;
import java.util.Optional;

public class BiomeZoneRegistry {
    private static IBiomeZoneProvider provider = null;

    public static void register(IBiomeZoneProvider provider) {
        BiomeZoneRegistry.provider = provider;
    }

    public static Optional<TemperatureZone> getZone(ResourceLocation biomeId) {
        if (provider != null) {
            return provider.getZone(biomeId);
        }
        return Optional.empty();
    }

    public static boolean hasProvider() {
        return provider != null;
    }
}