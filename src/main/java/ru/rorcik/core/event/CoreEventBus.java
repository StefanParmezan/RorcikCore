package ru.rorcik.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CoreEventBus {
    private static final Map<String, List<Consumer<CoreEvent>>> LISTENERS = new HashMap<>();

    public static void register(String eventId, Consumer<CoreEvent> listener) {
        LISTENERS.computeIfAbsent(eventId, k -> new ArrayList<>()).add(listener);
    }

    public static void unregister(String eventId, Consumer<CoreEvent> listener) {
        List<Consumer<CoreEvent>> listeners = LISTENERS.get(eventId);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public static void post(CoreEvent event) {
        List<Consumer<CoreEvent>> listeners = LISTENERS.get(event.getEventId());
        if (listeners != null) {
            for (Consumer<CoreEvent> listener : listeners) {
                listener.accept(event);
                if (event.isCancelled()) {
                    break;
                }
            }
        }
    }

    public static boolean hasListeners(String eventId) {
        List<Consumer<CoreEvent>> listeners = LISTENERS.get(eventId);
        return listeners != null && !listeners.isEmpty();
    }
}