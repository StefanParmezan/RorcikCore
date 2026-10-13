package ru.rorcik.core.event;

import net.minecraftforge.eventbus.api.Event;
import ru.rorcik.core.zone.ZoneRules;

public class ZoneEvents {
    public static class ZoneReservedEvent extends Event {
        public final int chunkX;
        public final int chunkZ;
        public final int centerX;
        public final int centerZ;
        public final ZoneRules rules;
        public final int targetHeight; 

        public ZoneReservedEvent(int chunkX, int chunkZ, int centerX, int centerZ, ZoneRules rules, int targetHeight) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            this.centerX = centerX;
            this.centerZ = centerZ;
            this.rules = rules;
            this.targetHeight = targetHeight; 
        }
    }
}