package ru.basher.utils.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.ItemUtil;

public abstract class CustomMaterial {

    public abstract ItemStack getItemStack();

    @NotNull
    public static CustomMaterial of(@NotNull ConfigurationSection section) {
        String value = section.getString("material", "");
        if (value.startsWith("base64-")) {
            return new Head(value.replace("base64-", ""));
        } else {
            Material material = Material.getMaterial(value.toUpperCase());
            if (material == null) material = Material.STONE;

            if (!material.name().contains("POTION") && material != Material.TIPPED_ARROW) {
                return new Default(material);
            } else {
                Color color = null;
                String rgb = section.getString("color", null);
                if (rgb != null) {
                    try {
                        String[] arr = rgb.split(";");
                        color = Color.fromRGB(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
                    } catch (Exception ignored) {
                    }
                }

                return new Potion(material, color);
            }
        }
    }

    @RequiredArgsConstructor
    public static class Default extends CustomMaterial {
        private final Material material;

        @Override
        public ItemStack getItemStack() {
            return new ItemStack(material, 1);
        }
    }

    @RequiredArgsConstructor
    public static class Head extends CustomMaterial {
        private final String base64;

        @Override
        public ItemStack getItemStack() {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemUtil.setSkull(itemStack, base64);
            return itemStack;
        }
    }

    @RequiredArgsConstructor
    public static class Potion extends CustomMaterial {
        private final Material material;
        @Nullable
        private final Color color;

        @Override
        public ItemStack getItemStack() {
            ItemStack itemStack = new ItemStack(material);
            if (color != null) {
                PotionMeta meta = (PotionMeta)itemStack.getItemMeta();
                meta.setColor(color);
                itemStack.setItemMeta(meta);
            }

            return itemStack;
        }
    }
}
