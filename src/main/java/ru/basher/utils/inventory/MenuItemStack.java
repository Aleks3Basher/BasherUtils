package ru.basher.utils.inventory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuItemStack {

    private final ItemStack itemStack;
    @Getter
    private final List<Integer> slots = new ArrayList<>();

    public MenuItemStack(@NotNull ConfigurationSection section) {
        itemStack = new ItemStackGenerator(section).generate();
        if (section.contains("slots")) {
            slots.addAll(section.getIntegerList("slots"));
        } else {
            slots.add(section.getInt("slot", 0));
        }
    }

    public @NotNull ItemStack getItemStackCopy() {
        return itemStack.clone();
    }

    public int getSlot() {
        return slots.get(0);
    }

    public boolean hasSlot(int slot) {
        return slots.contains(slot);
    }

    public void copyToInv(@NotNull Inventory inventory) {
        ItemStack copy = getItemStackCopy();
        for (int slot : slots) {
            inventory.setItem(slot, copy);
        }
    }

}
