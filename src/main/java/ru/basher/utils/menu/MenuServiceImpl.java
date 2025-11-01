package ru.basher.utils.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.menu.listener.InventoryListener;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class MenuServiceImpl {

    private static final long CLICK_COOLDOWN = 100L;

    private final Plugin plugin;
    private final InventoryListener inventoryListener;

    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public MenuServiceImpl(Plugin plugin) {
        this.plugin = plugin;
        inventoryListener = new InventoryListener(plugin, this);
    }

    public void enable() {
        inventoryListener.register();

        MenuService.setInstance(this);
    }

    public void disable() {
        inventoryListener.unregister();
        cooldowns.clear();
    }

    public void removeCooldown(@NotNull UUID playerUniqueId) {
        cooldowns.remove(playerUniqueId);
    }

    public boolean canClick(@NotNull UUID playerUniqueId) {
        long now = System.currentTimeMillis();
        Long inMap = cooldowns.get(playerUniqueId);

        if(inMap != null && inMap + CLICK_COOLDOWN > now) return false;

        cooldowns.put(playerUniqueId, now);
        return true;
    }

    public void openMenuAsync(@NotNull Player player, @NotNull Supplier<Menu> constructor) {
        CompletableFuture.supplyAsync(constructor).thenAccept(menu -> {
            Bukkit.getScheduler().runTask(plugin, () -> menu.open(player));
        });
    }

}
