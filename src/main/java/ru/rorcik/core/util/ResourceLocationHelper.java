package ru.rorcik.core.util;

import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ResourceLocationHelper {
    public static ResourceLocation parse(@NonNull String location) {
        if (location.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource location string cannot be empty");
        }
        ResourceLocation parsed = ResourceLocation.tryParse(location);
        if (parsed == null) {
            throw new IllegalArgumentException("Invalid resource location format: " + location);
        }
        return parsed;
    }

    public static ResourceLocation parseOrDefault(String location, ResourceLocation defaultLocation) {
        if (location == null || location.trim().isEmpty()) {
            return defaultLocation;
        }
        ResourceLocation parsed = ResourceLocation.tryParse(location);
        return parsed != null ? parsed : defaultLocation;
    }

    public static boolean isValid(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        return ResourceLocation.tryParse(location) != null;
    }
}