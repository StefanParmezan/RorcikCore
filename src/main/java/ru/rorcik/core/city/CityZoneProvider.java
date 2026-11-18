package ru.rorcik.core.city;

import java.util.List;

public interface CityZoneProvider {

    CityZone getZoneAtChunk(int chunkX, int chunkZ);

    List<CityZone> getNearbyZones(int blockX, int blockZ, int radius);
}
