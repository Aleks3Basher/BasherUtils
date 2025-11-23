package ru.basher.utils.menu.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import ru.basher.utils.menu.Menu;
import ru.basher.utils.menu.rMenuService;

@RequiredArgsConstructor
public class InventoryListener implements Listener {

    private final Plugin plugin;
    private final rMenuService service;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        InventoryClickEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getSlot() < 0) return;

        Player player = (Player)event.getWhoClicked();
        Inventory inv = player.getOpenInventory().getTopInventory();

        if(inv.getHolder() instanceof Menu menu) {
            if(service.canClick(player.getUniqueId())) {
                menu.onInventoryClick(event);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        service.removeCooldown(event.getPlayer().getUniqueId());
        if(event.getInventory().getHolder() instanceof Menu menu) {
            menu.onInventoryClose(event);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        service.removeCooldown(event.getPlayer().getUniqueId());
    }

}
