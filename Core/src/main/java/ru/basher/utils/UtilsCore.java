package ru.basher.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.api.Version;
import ru.basher.utils.menu.rMenuService;
import ru.basher.utils.v1_16_5.v1_16_5;
import ru.basher.utils.v1_20_4.v1_20_4;
import ru.basher.utils.wand.rWandService;

public class UtilsCore {

    private static Version version;
    private static rWandService wandService;
    private static rMenuService menuService;

    public static void register(@NotNull JavaPlugin plugin) {
        version = matchVersion(plugin);
        wandService = new rWandService(plugin);
        wandService.enable();
        menuService = new rMenuService(plugin);
        menuService.enable();
    }

    public static void unregister() {
        if(menuService != null) menuService.disable();
        if(wandService != null) wandService.disable();
        version = null;
    }

    private static @NotNull Version matchVersion(@NotNull JavaPlugin plugin) {
        String version = plugin.getServer().getBukkitVersion();
        if(version.contains("1.20.4")) {
            return v1_20_4.newInstance(plugin);
        } else {
            return v1_16_5.newInstance(plugin);
        }
    }

    public static @NotNull Version getVersion() {
        if (version == null) {
            throw new RuntimeException("UtilsCore not registered!");
        }
        return version;
    }
}
