package ru.basher.utils.wand;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WandService {

    private static WandServiceImpl instance;

    static void setInstance(@NotNull WandServiceImpl service) {
        instance = service;
    }

    public static @Nullable SelectionBox getSelection(@NotNull Player player) {
        return instance.getSelection(player);
    }

    public static void giveWandItem(@NotNull Player player) {
        instance.giveWandItem(player);
    }

}
