package ru.basher.utils;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.api.IItemUtil;

public class ItemUtil {

    private static final IItemUtil itemUtil = UtilsCore.getVersion().getItemUtil();

    public static void setSkull(@NotNull ItemStack headItemStack, @NotNull String base64) {
        itemUtil.setSkull(headItemStack, base64);
    }

    public static void setDamageItem(@NotNull ItemStack itemStack, int damage) {
        itemUtil.setDamageItem(itemStack, damage);
    }

    public static @Nullable String getItemNBTValue(@NotNull ItemStack itemStack, @NotNull String key) {
        return itemUtil.getItemNBTValue(itemStack, key);
    }

    public static void addItemNBT(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value) {
        itemUtil.addItemNBT(itemStack, key, value);
    }

    public static boolean hasItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        return itemUtil.hasItemNBT(itemStack, key);
    }

    public static void removeItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        itemUtil.removeItemNBT(itemStack, key);
    }

    public static @NotNull String serializeItemStack(@NotNull ItemStack itemStack) {
        return itemUtil.serializeItemStack(itemStack);
    }

    public static @NotNull ItemStack deserializeItemStack(@NotNull String serializedItemStack) throws Exception {
        return itemUtil.deserializeItemStack(serializedItemStack);
    }

}
