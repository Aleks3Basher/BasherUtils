package ru.basher.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.api.IEntityUtil;

public class EntityUtil {

    private static final IEntityUtil entityUtil = UtilsCore.getVersion().getEntityUtil();

    public static @Nullable String getEntityNBTValue(@NotNull Entity entity, @NotNull String key) {
        return entityUtil.getEntityNBTValue(entity, key);
    }

    public static void addEntityNBT(@NotNull Entity entity, @NotNull String key, @NotNull String value) {
        entityUtil.addEntityNBT(entity, key, value);
    }

    public static boolean hasEntityNBT(@NotNull Entity entity, @NotNull String key) {
        return entityUtil.hasEntityNBT(entity, key);
    }

    public static void removeEntityNBT(@NotNull Entity entity, @NotNull String key) {
        entityUtil.removeEntityNBT(entity, key);
    }

    public static void sendFakeDamageAnimation(@NotNull LivingEntity entity) {
        entityUtil.sendFakeDamageAnimation(entity);
    }

    public static void sendFakeDeathAnimation(@NotNull LivingEntity entity) {
        entityUtil.sendFakeDeathAnimation(entity);
    }

}
