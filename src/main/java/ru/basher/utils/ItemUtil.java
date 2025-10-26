package ru.basher.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemUtil {

    public static void setSkull(@NotNull ItemStack headItemStack, @NotNull String base64) {
        if (!base64.isEmpty()) {
            if (headItemStack.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta)headItemStack.getItemMeta();
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                profile.getProperties().put("textures", new Property("textures", base64));

                try {
                    Field field = meta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(meta, profile);
                } catch (Exception ignored) {
                }

                headItemStack.setItemMeta(meta);
            }
        }
    }

    public static void setDamageItem(@NotNull ItemStack itemStack, int damage) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable damageable) {
            int max = itemStack.getType().getMaxDurability();
            damageable.setDamage(Math.max(0, max - damage));
            itemStack.setItemMeta((ItemMeta)damageable);
        }
    }

    @Nullable
    public static String getItemNBTValue(@NotNull ItemStack itemStack, @NotNull String key) {
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.get(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void addItemNBT(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
    }

    public static boolean hasItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(UtilsCore.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void removeItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().remove(new NamespacedKey(UtilsCore.getPlugin(), key));
        itemStack.setItemMeta(meta);
    }
}
