package ru.basher.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WorldUtil {

    public static void explodeAtLocation(@NotNull Location location, double power) {
        World world = location.getWorld();
        if (world != null) {
            world.createExplosion(location.getX(), location.getY(), location.getZ(), (float)power, false, true, null);
        }
    }

    public static void fakeExplodeAtLocation(@NotNull Location location, double power) {
        World world = location.getWorld();
        if (world != null) {
            for(int i = 0; (double)i < power; ++i) {
                double diffX = NumberUtil.getRandomDouble(-power, power);
                double diffY = NumberUtil.getRandomDouble(-power, power);
                double diffZ = NumberUtil.getRandomDouble(-power, power);
                Location loc = location.clone().add(diffX, diffY, diffZ);
                world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 0, 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

    public static @NotNull Block getHighestBlockAt(@NotNull World world, int x, int z) {
        for(int y = 256; y > 0; --y) {
            Block block = world.getBlockAt(x, y, z);
            if (!block.getType().isAir()) {
                return block;
            }
        }

        return world.getBlockAt(x, 0, z);
    }

    public static void dropItem(@NotNull Location location, @NotNull ItemStack... items) {
        World world = location.getWorld();
        if (world != null) {
            for(ItemStack itemStack : items) {
                world.dropItemNaturally(location, itemStack);
            }
        }
    }

}
