package ru.rorcik.core.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;
import ru.rorcik.core.RorcikCore;
import ru.rorcik.core.property.RorPropertyContainer;
import ru.rorcik.core.property.RorPropertyDescriptor;
import ru.rorcik.core.property.RorPropertyKey;
import ru.rorcik.core.property.RorPropertyRegistry;

import java.util.Map;
import java.util.Objects;

public class SerializationHelper {
    private static final String KEY_PROPERTIES = "Properties";
    private static final String KEY_ID = "ID";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_VERSION = "Version";
    private static final int NBT_VERSION = 1;

    public static void save(@NonNull RorPropertyContainer container, @NonNull CompoundTag tag) {
        Objects.requireNonNull(container, "Container cannot be null");
        Objects.requireNonNull(tag, "Tag cannot be null");

        tag.putInt(KEY_VERSION, NBT_VERSION);
        ListTag propertiesList = new ListTag();

        for (Map.Entry<RorPropertyKey<?>, Object> entry : container.getAllValues().entrySet()) {
            RorPropertyKey<?> key = entry.getKey();
            RorPropertyDescriptor<?> descriptor = RorPropertyRegistry.getDescriptor(key);

            if (descriptor != null && descriptor.isTransient()) {
                continue;
            }

            Object value = entry.getValue();
            CompoundTag propertyTag = new CompoundTag();
            propertyTag.putString(KEY_ID, key.getFullName());
            propertyTag.putString(KEY_TYPE, key.getType().getName());

            serializeValue(propertyTag, value);
            propertiesList.add(propertyTag);
        }
        tag.put(KEY_PROPERTIES, propertiesList);
    }

    @NonNull
    public static RorPropertyContainer load(@NonNull CompoundTag tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        RorPropertyContainer container = new RorPropertyContainer();

        int version = tag.contains(KEY_VERSION, Tag.TAG_INT) ? tag.getInt(KEY_VERSION) : 0;
        if (version > NBT_VERSION) {
            RorcikCore.LOGGER.warn("Loading NBT version {} with older code version {}. Data loss may occur.",
                    version, NBT_VERSION);
        }
        
        if (!tag.contains(KEY_PROPERTIES, Tag.TAG_LIST)) {
            return container;
        }

        ListTag propertiesList = tag.getList(KEY_PROPERTIES, Tag.TAG_COMPOUND);

        for (int i = 0; i < propertiesList.size(); i++) {
            CompoundTag propertyTag = propertiesList.getCompound(i);
            String id = propertyTag.getString(KEY_ID);
            String type = propertyTag.getString(KEY_TYPE);

            RorPropertyKey<?> key = RorPropertyRegistry.findKeyByName(id);
            if (key == null) {
                RorcikCore.LOGGER.debug("Skipping unknown property during load: {}", id);
                continue;
            }

            Object value = deserializeValue(propertyTag, type, key.getType());
            if (value != null) {
                setUnchecked(container, key, value);
            }
        }
        return container;
    }

    private static void serializeValue(@NonNull CompoundTag tag, @NonNull Object value) {
        if (value instanceof String) tag.putString(KEY_VALUE, (String) value);
        else if (value instanceof Float) tag.putFloat(KEY_VALUE, (Float) value);
        else if (value instanceof Double) tag.putDouble(KEY_VALUE, (Double) value);
        else if (value instanceof Integer) tag.putInt(KEY_VALUE, (Integer) value);
        else if (value instanceof Boolean) tag.putBoolean(KEY_VALUE, (Boolean) value);
        else if (value instanceof Long) tag.putLong(KEY_VALUE, (Long) value);
        else if (value instanceof ResourceLocation) tag.putString(KEY_VALUE, value.toString());
        else throw new IllegalArgumentException("Unsupported value type: " + value.getClass().getName());
    }

    private static Object deserializeValue(@NonNull CompoundTag tag, @NonNull String typeName, Class<?> targetType) {
        return switch (typeName) {
            case "java.lang.String" -> tag.getString(KEY_VALUE);
            case "java.lang.Float", "float" -> tag.getFloat(KEY_VALUE);
            case "java.lang.Double", "double" -> tag.getDouble(KEY_VALUE);
            case "java.lang.Integer", "int" -> tag.getInt(KEY_VALUE);
            case "java.lang.Boolean", "boolean" -> tag.getBoolean(KEY_VALUE);
            case "java.lang.Long", "long" -> tag.getLong(KEY_VALUE);
            case "net.minecraft.resources.ResourceLocation" -> {
                String location = tag.getString(KEY_VALUE);
                yield ResourceLocationHelper.isValid(location) ? ResourceLocationHelper.parse(location) : null;
            }
            default -> {
                RorcikCore.LOGGER.warn("Unknown property type during deserialization: {}", typeName);
                yield null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <T> void setUnchecked(@NonNull RorPropertyContainer container,
                                         @NonNull RorPropertyKey<T> key,
                                         @NonNull Object value) {
        container.set(key, (T) value, false);
    }
}