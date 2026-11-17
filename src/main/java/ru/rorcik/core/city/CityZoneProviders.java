package ru.rorcik.core.city;

import java.util.Objects;

public final class CityZoneProviders {

    private static volatile CityZoneProvider provider;

    private CityZoneProviders() {
    }

    public static void register(CityZoneProvider cityZoneProvider) {
        provider = Objects.requireNonNull(cityZoneProvider, "cityZoneProvider");
    }

    public static CityZoneProvider getProvider() {
        return provider;
    }

    public static boolean hasProvider() {
        return provider != null;
    }

    public static void clear() {
        provider = null;
    }
}
