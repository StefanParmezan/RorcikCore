package ru.rorcik.core.city;

import org.junit.jupiter.api.Test;
import ru.rorcik.core.zone.ZoneRules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CityZoneTest {

    private static final ZoneRules RULES = new ZoneRules("plains", 5000);

    private static CityZone zone() {
        return new CityZone(1000, -1000, RULES, 64, 10, -20, 20, -10);
    }

    @Test
    void containsChunkIncludesBounds() {
        CityZone zone = zone();

        assertTrue(zone.containsChunk(10, -20));
        assertTrue(zone.containsChunk(20, -10));
        assertTrue(zone.containsChunk(15, -15));

        assertFalse(zone.containsChunk(9, -15));
        assertFalse(zone.containsChunk(21, -15));
        assertFalse(zone.containsChunk(15, -21));
        assertFalse(zone.containsChunk(15, -9));
    }

    @Test
    void containsBlockUsesChunkBlockBounds() {
        CityZone zone = zone();

        assertEquals(160, zone.minBlockX());
        assertEquals(335, zone.maxBlockX());
        assertEquals(-320, zone.minBlockZ());
        assertEquals(-145, zone.maxBlockZ());

        assertTrue(zone.containsBlock(160, -320));
        assertTrue(zone.containsBlock(335, -145));
        assertFalse(zone.containsBlock(159, -200));
        assertFalse(zone.containsBlock(336, -200));
        assertFalse(zone.containsBlock(200, -321));
        assertFalse(zone.containsBlock(200, -144));
    }

    @Test
    void intersectsBlocksDetectsOverlap() {
        CityZone zone = zone();

        assertTrue(zone.intersectsBlocks(300, -200, 400, -100));
        assertTrue(zone.intersectsBlocks(100, -400, 200, -300));
        assertFalse(zone.intersectsBlocks(336, -200, 400, -100));
        assertFalse(zone.intersectsBlocks(100, -400, 200, -321));
    }

    @Test
    void withChunkBorderExpandsZone() {
        CityZone expanded = zone().withChunkBorder(2);

        assertEquals(8, expanded.minChunkX());
        assertEquals(-22, expanded.minChunkZ());
        assertEquals(22, expanded.maxChunkX());
        assertEquals(-8, expanded.maxChunkZ());
        assertTrue(expanded.containsChunk(8, -22));
        assertTrue(expanded.containsChunk(22, -8));
    }

    @Test
    void invalidBoundsAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new CityZone(0, 0, RULES, 64, 5, 0, 4, 10));
        assertThrows(IllegalArgumentException.class,
                () -> new CityZone(0, 0, RULES, 64, 0, 5, 10, 4));
    }
}
