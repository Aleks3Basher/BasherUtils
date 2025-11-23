package ru.basher.utils.menu.creator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Getter
public class MenuButton {

    private final ItemStack itemStack;

    private Consumer<InventoryClickEvent> onClick = null;

    public void onClick(@NotNull Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
    }

}
