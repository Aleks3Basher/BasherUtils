package ru.basher.utils.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IItemUtil {

    void setSkull(@NotNull ItemStack headItemStack, @NotNull String base64);

    void setDamageItem(@NotNull ItemStack itemStack, int damage);

    @Nullable String getItemNBTValue(@NotNull ItemStack itemStack, @NotNull String key);

    void addItemNBT(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value);

    boolean hasItemNBT(@NotNull ItemStack itemStack, @NotNull String key);

    void removeItemNBT(@NotNull ItemStack itemStack, @NotNull String key);

    @NotNull String serializeItemStack(@NotNull ItemStack itemStack);

    @NotNull ItemStack deserializeItemStack(@NotNull String serializedItemStack) throws Exception;

}
