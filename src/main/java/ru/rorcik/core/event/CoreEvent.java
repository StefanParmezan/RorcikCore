package ru.rorcik.core.event;

import java.util.HashMap;
import java.util.Map;

public class CoreEvent {
    private final String eventId;
    private final Map<String, Object> data = new HashMap<>();
    private boolean cancelled = false;

    public CoreEvent(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public <T> void set(String key, T value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public <T> T getOrDefault(String key, T defaultValue) {
        Object value = data.get(key);
        return value != null ? (T) value : defaultValue;
    }

    public boolean has(String key) {
        return data.containsKey(key);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}