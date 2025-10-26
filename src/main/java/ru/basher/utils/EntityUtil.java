package ru.basher.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityUtil {

    @Nullable
    public static String getEntityNBTValue(@NotNull Entity entity, @NotNull String key) {
        return entity.getPersistentDataContainer().get(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void addEntityNBT(@NotNull Entity entity, @NotNull String key, @NotNull String value) {
        entity.getPersistentDataContainer().set(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING, value);
    }

    public static boolean hasEntityNBT(@NotNull Entity entity, @NotNull String key) {
        return entity.getPersistentDataContainer().has(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void removeEntityNBT(@NotNull Entity entity, @NotNull String key) {
        entity.getPersistentDataContainer().remove(new NamespacedKey(UtilsCore.getPlugin(), key));
    }
}
