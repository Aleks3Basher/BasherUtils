package ru.basher.utils.inventory;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuItem {

    private final ItemStackGenerator generator;
    private final List<Integer> slots = new ArrayList<>();

    public MenuItem() {
        generator = new ItemStackGenerator();
    }

    public MenuItem(@NotNull ConfigurationSection section) {
        generator = new ItemStackGenerator(section);
        if (section.contains("slots")) {
            slots.addAll(section.getIntegerList("slots"));
        } else {
            slots.add(section.getInt("slot", 0));
        }
    }

    public int getSlot() {
        return slots.get(0);
    }

    public boolean hasSlot(int slot) {
        return slots.contains(slot);
    }

    public @NotNull ItemStack getItemStack(@NotNull Replace... replaces) {
        return generator.generateArr(replaces);
    }

    public void copyToInv(@NotNull Inventory inventory, @NotNull Replace... replaces) {
        ItemStack itemStack = getItemStack(replaces);

        for(int slot : slots) {
            inventory.setItem(slot, itemStack.clone());
        }
    }

}
