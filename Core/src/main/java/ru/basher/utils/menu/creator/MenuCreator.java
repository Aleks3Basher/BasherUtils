package ru.basher.utils.menu.creator;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.menu.Menu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public abstract class MenuCreator {

    private final Map<Integer, MenuButton> buttons = new HashMap<>();
    private final Map<Integer, ItemStack> decor = new HashMap<>();

    private Consumer<Player> onOpen = null;
    private Consumer<InventoryCloseEvent> onClose = null;

    public void setDecor(@NotNull ItemStack itemStack, int slot) {
        decor.put(slot, itemStack);
    }

    public void setDecors(@NotNull ItemStack itemStack, @NotNull Collection<Integer> slots) {
        for(int slot : slots) decor.put(slot, itemStack);
    }

    public void setButton(@NotNull MenuButton button, int slot) {
        buttons.put(slot, button);
    }

    public void setButtons(@NotNull MenuButton button, @NotNull Collection<Integer> slots) {
        for(int slot : slots) buttons.put(slot, button);
    }

    public void onOpen(@NotNull Consumer<Player> onOpen) {
        this.onOpen = onOpen;
    }

    public void onClose(@NotNull Consumer<InventoryCloseEvent> onClose) {
        this.onClose = onClose;
    }

    public @NotNull Menu create(@NotNull String title, int size) {
        return new DefaultMenu(title, size, this);
    }

}
