package ru.basher.utils.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IEntityUtil {

    @Nullable String getEntityNBTValue(@NotNull Entity entity, @NotNull String key);

    void addEntityNBT(@NotNull Entity entity, @NotNull String key, @NotNull String value);

    boolean hasEntityNBT(@NotNull Entity entity, @NotNull String key);

    void removeEntityNBT(@NotNull Entity entity, @NotNull String key);

    void sendFakeDamageAnimation(@NotNull LivingEntity entity);

    void sendFakeDeathAnimation(@NotNull LivingEntity entity);

}
