package ru.basher.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.menu.MenuServiceImpl;
import ru.basher.utils.wand.WandServiceImpl;

public class UtilsCore {

    private static JavaPlugin plugin;
    private static WandServiceImpl wandService;
    private static MenuServiceImpl menuService;

    public static void register(@NotNull JavaPlugin plugin) {
        UtilsCore.plugin = plugin;
        wandService = new WandServiceImpl(plugin);
        wandService.enable();
        menuService = new MenuServiceImpl(plugin);
        menuService.enable();
    }

    public static void unregister() {
        plugin = null;
        if(wandService != null) wandService.disable();
        if(menuService != null) menuService.disable();
    }

    @NotNull
    public static JavaPlugin getPlugin() {
        if (plugin == null) {
            throw new RuntimeException("UtilsCore not registered!");
        }
        return plugin;
    }
}
