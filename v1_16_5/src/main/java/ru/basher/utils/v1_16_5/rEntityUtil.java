package ru.basher.utils.v1_16_5;

import lombok.RequiredArgsConstructor;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.api.IEntityUtil;

@RequiredArgsConstructor
public class rEntityUtil implements IEntityUtil {

    private final JavaPlugin plugin;

    @Override
    public @Nullable String getEntityNBTValue(@NotNull Entity entity, @NotNull String key) {
        return entity.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    @Override
    public void addEntityNBT(@NotNull Entity entity, @NotNull String key, @NotNull String value) {
        entity.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    @Override
    public boolean hasEntityNBT(@NotNull Entity entity, @NotNull String key) {
        return entity.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    @Override
    public void removeEntityNBT(@NotNull Entity entity, @NotNull String key) {
        entity.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
    }

    @Override
    public void sendFakeDamageAnimation(@NotNull LivingEntity entity) {
        int viewDistance = plugin.getServer().getViewDistance();
        double radius = viewDistance * viewDistance;
        Location location = entity.getLocation();

        Packet<?> damagePacket = new PacketPlayOutAnimation( ((CraftLivingEntity)entity).getHandle(), 1);
        for (Player player : entity.getWorld().getPlayers()) {
            if(player == null) continue;
            if(player.getLocation().distanceSquared(location) > radius) continue;

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(damagePacket);
        }
    }

    @Override
    public void sendFakeDeathAnimation(@NotNull LivingEntity entity) {
        int viewDistance = plugin.getServer().getViewDistance();
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
