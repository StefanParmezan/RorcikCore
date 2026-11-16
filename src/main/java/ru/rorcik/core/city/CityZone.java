package ru.rorcik.core.city;

import ru.rorcik.core.zone.ZoneRules;

public record CityZone(
        int centerX,
        int centerZ,
        ZoneRules rules,
        int targetHeight,
        int minChunkX,
        int minChunkZ,
        int maxChunkX,
        int maxChunkZ
) {

    public CityZone {
        if (minChunkX > maxChunkX || minChunkZ > maxChunkZ) {
            throw new IllegalArgumentException("Invalid city zone bounds");
        }
    }

    public String typeId() {
        return rules.id();
    }

    public int minDistance() {
        return rules.minDistance();
    }

    public int minBlockX() {
        return minChunkX << 4;
    }

    public int minBlockZ() {
        return minChunkZ << 4;
    }

    public int maxBlockX() {
        return (maxChunkX << 4) + 15;
    }

    public int maxBlockZ() {
        return (maxChunkZ << 4) + 15;
    }

    public boolean containsChunk(int chunkX, int chunkZ) {
        return chunkX >= minChunkX && chunkX <= maxChunkX
                && chunkZ >= minChunkZ && chunkZ <= maxChunkZ;
    }

    public boolean containsBlock(int blockX, int blockZ) {
        return blockX >= minBlockX() && blockX <= maxBlockX()
                && blockZ >= minBlockZ() && blockZ <= maxBlockZ();
    }

    public boolean intersectsBlocks(int minX, int minZ, int maxX, int maxZ) {
        return maxBlockX() >= minX && minBlockX() <= maxX
                && maxBlockZ() >= minZ && minBlockZ() <= maxZ;
    }

    public CityZone withChunkBorder(int border) {
        if (border <= 0) return this;
        return new CityZone(centerX, centerZ, rules, targetHeight,
                minChunkX - border, minChunkZ - border,
                maxChunkX + border, maxChunkZ + border);
    }
}
