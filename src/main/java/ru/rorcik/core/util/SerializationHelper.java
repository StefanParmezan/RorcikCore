package ru.rorcik.core.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.rorcik.core.property.Characteristic;
import ru.rorcik.core.property.CorePropertyRegistry;
import ru.rorcik.core.property.PropertyContainer;

import java.util.Map;
import java.util.Objects;

public class SerializationHelper {
    private static final String KEY_PROPERTIES = "Properties";
    private static final String KEY_ID = "ID";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_TYPE = "Type";

    public static void save(@NonNull PropertyContainer container, @NonNull CompoundTag tag) {
        Objects.requireNonNull(container, "Container cannot be null");
        Objects.requireNonNull(tag, "Tag cannot be null");

        ListTag propertiesList = new ListTag();

        for (Map.Entry<Characteristic<?>, Object> entry : container.getAllValues().entrySet()) {
            Characteristic<?> characteristic = entry.getKey();
            Object value = entry.getValue();

            CompoundTag propertyTag = new CompoundTag();
            propertyTag.putString(KEY_ID, characteristic.getId());
            propertyTag.putString(KEY_TYPE, value.getClass().getSimpleName());

            serializeValue(propertyTag, value);
            propertiesList.add(propertyTag);
        }

        tag.put(KEY_PROPERTIES, propertiesList);
    }

    @NonNull
    public static PropertyContainer load(@NonNull CompoundTag tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");

        PropertyContainer container = new PropertyContainer();

        if (!tag.contains(KEY_PROPERTIES)) {
            return container;
        }

        ListTag propertiesList = tag.getList(KEY_PROPERTIES, Tag.TAG_COMPOUND);

        for (int i = 0; i < propertiesList.size(); i++) {
            CompoundTag propertyTag = propertiesList.getCompound(i);
            String id = propertyTag.getString(KEY_ID);
            String type = propertyTag.getString(KEY_TYPE);

            Characteristic<?> characteristic = findCharacteristic(id);
            if (characteristic == null) {
                continue;
            }

            Object value = deserializeValue(propertyTag, type);
            if (value != null) {
                setUnchecked(container, characteristic, value);
            }
        }

        return container;
    }

    private static void serializeValue(@NonNull CompoundTag tag, @NonNull Object value) {
        if (value instanceof String) {
            tag.putString(KEY_VALUE, (String) value);
        } else if (value instanceof Float) {
            tag.putFloat(KEY_VALUE, (Float) value);
        } else if (value instanceof Double) {
            tag.putDouble(KEY_VALUE, (Double) value);
        } else if (value instanceof Integer) {
            tag.putInt(KEY_VALUE, (Integer) value);
        } else if (value instanceof Boolean) {
            tag.putBoolean(KEY_VALUE, (Boolean) value);
        } else if (value instanceof Long) {
            tag.putLong(KEY_VALUE, (Long) value);
        } else if (value instanceof ResourceLocation) {
            tag.putString(KEY_VALUE, value.toString());
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + value.getClass().getName());
        }
    }

    private static Object deserializeValue(@NonNull CompoundTag tag, @NonNull String type) {
        return switch (type) {
            case "String" -> tag.getString(KEY_VALUE);
            case "Float" -> tag.getFloat(KEY_VALUE);
            case "Double" -> tag.getDouble(KEY_VALUE);
            case "Integer" -> tag.getInt(KEY_VALUE);
            case "Boolean" -> tag.getBoolean(KEY_VALUE);
            case "Long" -> tag.getLong(KEY_VALUE);
            case "ResourceLocation" -> {
                String location = tag.getString(KEY_VALUE);
                yield ResourceLocationHelper.isValid(location) ? ResourceLocationHelper.parse(location) : null;
            }
            default -> null;
        };
    }

    private static Characteristic<?> findCharacteristic(@NonNull String id) {
        for (Map<String, Characteristic<?>> modMap : CorePropertyRegistry.getAllModMaps().values()) {
            Characteristic<?> characteristic = modMap.get(id);
            if (characteristic != null) {
                return characteristic;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> void setUnchecked(@NonNull PropertyContainer container, @NonNull Characteristic<T> characteristic, @NonNull Object value) {
        container.set(characteristic, (T) value);
    }
}