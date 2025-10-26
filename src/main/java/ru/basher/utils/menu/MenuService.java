package ru.basher.utils.menu;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MenuService {

    private static MenuServiceImpl instance;

    static void setInstance(@NotNull MenuServiceImpl service) {
        instance = service;
    }

    public static void openMenuAsync(@NotNull Player player, @NotNull Supplier<Menu> constructor) {
        instance.openMenuAsync(player, constructor);
    }

}
