package ru.basher.utils.v1_16_5;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.api.IItemUtil;

@RequiredArgsConstructor
public class rItemUtil implements IItemUtil {

    private final JavaPlugin plugin;

    @Override
    public void setSkull(@NotNull ItemStack headItemStack, @NotNull String base64) {
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

    @Override
    public void setDamageItem(@NotNull ItemStack itemStack, int damage) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable damageable) {
            int max = itemStack.getType().getMaxDurability();
            damageable.setDamage(Math.max(0, max - damage));
            itemStack.setItemMeta((ItemMeta)damageable);
        }
    }

    @Override
    public @Nullable String getItemNBTValue(@NotNull ItemStack itemStack, @NotNull String key) {
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    @Override
    public void addItemNBT(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean hasItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    @Override
    public void removeItemNBT(@NotNull ItemStack itemStack, @NotNull String key) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
        itemStack.setItemMeta(meta);
    }

    @Override
    public @NotNull String serializeItemStack(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(itemStack);
        net.minecraft.server.v1_16_R3.NBTTagCompound nmsTagComp = nmsItemStack.save(new net.minecraft.server.v1_16_R3.NBTTagCompound());
        return new String(Base64.getEncoder().encode(nmsTagComp.toString().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    @Override
    public @NotNull ItemStack deserializeItemStack(@NotNull String serializedItemStack) throws Exception {
        String decoded = new String(Base64.getDecoder().decode(serializedItemStack.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        net.minecraft.server.v1_16_R3.NBTTagCompound nmsTagComp = net.minecraft.server.v1_16_R3.MojangsonParser.parse(decoded);
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = net.minecraft.server.v1_16_R3.ItemStack.a(nmsTagComp);

        return org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(nmsItemStack);
    }
}
