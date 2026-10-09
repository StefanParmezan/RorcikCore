package ru.rorcik.core.zone;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;

public class ZoneRegistrySavedData extends SavedData {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DATA_NAME = "rorcik_zone_registry";
    private final List<ReservedZone> reservedZones = new ArrayList<>();

    public record ReservedZone(int x, int z, ZoneRules rules) {}

    public static ZoneRegistrySavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(ZoneRegistrySavedData::load, ZoneRegistrySavedData::new, DATA_NAME);
    }

    public static ZoneRegistrySavedData load(CompoundTag tag) {
        ZoneRegistrySavedData data = new ZoneRegistrySavedData();
        
        ListTag list = tag.getList("zones", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag zoneTag = list.getCompound(i);
            data.reservedZones.add(new ReservedZone(
                    zoneTag.getInt("x"),
                    zoneTag.getInt("z"),
                    new ZoneRules(zoneTag.getString("id"), zoneTag.getInt("minDist"), zoneTag.getInt("radius"))
            ));
        }
        LOGGER.info("[ZONE-REGISTRY] Loaded {} reserved zones.", data.reservedZones.size());
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (ReservedZone zone : reservedZones) {
            CompoundTag zoneTag = new CompoundTag();
            zoneTag.putInt("x", zone.x);
            zoneTag.putInt("z", zone.z);
            zoneTag.putString("id", zone.rules.id()); 
            zoneTag.putInt("minDist", zone.rules.minDistance());
            zoneTag.putInt("radius", zone.rules.estimatedRadius());
            list.add(zoneTag);
        }
        tag.put("zones", list);
        return tag;
    }

    public synchronized boolean tryReserveZone(int x, int z, ZoneRules rules) {
        for (ReservedZone existing : reservedZones) {
            int dx = x - existing.x;
            int dz = z - existing.z;
            int distanceSquared = dx * dx + dz * dz;
            int requiredMinDist = Math.max(rules.minDistance(), existing.rules.minDistance());
            if (distanceSquared < (requiredMinDist * requiredMinDist)) return false;
        }
        reservedZones.add(new ReservedZone(x, z, rules));
        setDirty();
        LOGGER.info("[ZONE-REGISTRY] Reserved {} zone at [{}, {}]", rules.id(), x, z);
        return true;
    }

    public List<ReservedZone> getAllReservedZones() {
        return new ArrayList<>(reservedZones);
    }
}