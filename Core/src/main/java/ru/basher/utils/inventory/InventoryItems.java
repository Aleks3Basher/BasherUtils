package ru.basher.utils.inventory;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class InventoryItems {

    private final Map<Material, List<ItemStack>> itemsByMaterial = new HashMap<>();
    private final Map<Material, Integer> amountsByMaterial = new HashMap<>();

    public InventoryItems(Inventory inventory) {
        for(int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack == null) continue;
            Material material = itemStack.getType();
            if(material.isAir()) continue;

            itemsByMaterial.computeIfAbsent(material, k -> new ArrayList<>()).add(itemStack);
            amountsByMaterial.merge(material, itemStack.getAmount(), Integer::sum);
        }
    }

    public boolean contains(@NotNull Material material, int amount) {
        return amountsByMaterial.getOrDefault(material, 0) >= amount;
    }

    public boolean contains(@NotNull Material material, int customModelData, int amount) {
        List<ItemStack> list = itemsByMaterial.get(material);
        if(list == null) return false;
        int remaining = amount;
        for(ItemStack itemStack : list) {
            if(remaining <= 0) break;
            if(itemStack.getItemMeta().getCustomModelData() != customModelData) continue;
            remaining -= itemStack.getAmount();
        }
        return remaining <= 0;
    }

    public void remove(@NotNull Material material, int amount) {
        remove(material, 0, amount);
    }

    public void remove(@NotNull Material material, int customModelData, int amount) {
        List<ItemStack> list = itemsByMaterial.get(material);
        if(list == null) return;

        int remaining = amount;
        for(ItemStack itemStack : list) {
            if(remaining <= 0) break;
            if(customModelData != 0) {
                if(itemStack.getItemMeta().getCustomModelData() != customModelData) continue;
            }
            if(itemStack.getAmount() >= remaining) {
                itemStack.setAmount(itemStack.getAmount() - remaining);
                remaining = 0;
            } else {
                remaining -= itemStack.getAmount();
                itemStack.setAmount(0);
            }
        }
    }

    public int getAmount(@NotNull Material material) {
        return amountsByMaterial.getOrDefault(material, 0);
    }

    public int getAmount(@NotNull Material material, int customModelData) {
        List<ItemStack> list = itemsByMaterial.get(material);
        if(list == null) return 0;
        int amount = 0;
        for(ItemStack itemStack : list) {
            if(customModelData != 0) {
                if(itemStack.getItemMeta().getCustomModelData() == customModelData) {
                    amount += itemStack.getAmount();
                }
            } else {
                amount += itemStack.getAmount();
            }
        }
        return amount;
    }

}
