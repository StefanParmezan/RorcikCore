package ru.rorcik.core.event;

import net.minecraftforge.eventbus.api.Event;
import ru.rorcik.core.zone.ZoneRules;
import java.util.Set;

public class ZoneEvents {
    public static class ZoneReservedEvent extends Event {
        public final int centerX;
        public final int centerZ;
        public final ZoneRules rules;
        public final int targetHeight;
        public final Set<Long> affectedChunks; 

        public ZoneReservedEvent(int centerX, int centerZ, ZoneRules rules,
                                 int targetHeight, Set<Long> affectedChunks) {
            this.centerX = centerX;
            this.centerZ = centerZ;
            this.rules = rules;
            this.targetHeight = targetHeight;
            this.affectedChunks = affectedChunks;
        }
    }
}