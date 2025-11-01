package ru.basher.utils.menu.creator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.menu.Menu;

import java.util.Map;

public class DefaultMenu implements Menu {

    private final Inventory inventory;

    private final MenuCreator creator;

    DefaultMenu(String title, int size, MenuCreator creator) {
        inventory = Bukkit.createInventory(this, size, title);
        this.creator = creator;

        for(Map.Entry<Integer, ItemStack> entry : creator.getDecor().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<Integer, MenuButton> entry : creator.getButtons().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onInventoryOpen(@NotNull Player player) {
        if(creator.getOnOpen() != null) {
            creator.getOnOpen().accept(player);
        }
    }

    @Override
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        MenuButton button = creator.getButtons().get(event.getSlot());
        if(button != null && button.getOnClick() != null) {
            button.getOnClick().accept(event);
        }
    }

    @Override
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        if(creator.getOnClose() != null) {
            creator.getOnClose().accept(event);
        }
    }
}
