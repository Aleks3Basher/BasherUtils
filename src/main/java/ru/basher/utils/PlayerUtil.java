package ru.basher.utils;

import com.google.common.base.Charsets;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class PlayerUtil {

    public static @NotNull UUID getUUIDFromPlayerName(@NotNull String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
    }

    public static void giveItems(@NotNull Player player, @NotNull ItemStack...itemStacks) {
        Map<Integer, ItemStack> fails = player.getInventory().addItem(itemStacks);
        if(fails.isEmpty()) return;

        World world = player.getWorld();
        for (ItemStack itemStack : fails.values()) {
            world.dropItem(player.getLocation(), itemStack);
        }
    }

}
