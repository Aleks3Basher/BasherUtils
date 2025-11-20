package ru.basher.utils;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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

    public static void sendFakeDamageAnimation(@NotNull LivingEntity entity) {
        int viewDistance = UtilsCore.getPlugin().getServer().getViewDistance();
        double radius = viewDistance * viewDistance;
        Location location = entity.getLocation();

        Packet<?> damagePacket = new PacketPlayOutAnimation( ((CraftLivingEntity)entity).getHandle(), 1);
        for (Player player : entity.getWorld().getPlayers()) {
            if(player == null) continue;
            if(player.getLocation().distanceSquared(location) > radius) continue;

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(damagePacket);
        }
    }

    public static void sendFakeDeathAnimation(@NotNull LivingEntity entity) {
        int viewDistance = UtilsCore.getPlugin().getServer().getViewDistance();
        double radius = viewDistance * viewDistance;
        Location location = entity.getLocation();

        Packet<?> packet = new PacketPlayOutAnimation( ((CraftLivingEntity)entity).getHandle(), 0);
        for (Player player : entity.getWorld().getPlayers()) {
            if(player == null) continue;
            if(player.getLocation().distanceSquared(location) > radius) continue;

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
