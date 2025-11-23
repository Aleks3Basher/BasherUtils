package ru.basher.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.UnaryOperator;

public class Util {

    public static void broadcastMessage(@NotNull List<String> message, @NotNull UnaryOperator<String> replace) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null) continue;
            for (String msg : message) {
                player.sendMessage(replace.apply(msg));
            }
        }
    }

    public static void broadcastMessage(@NotNull List<String> message, @NotNull UnaryOperator<String> replace, @NotNull Location center, double radius) {
        double radiusSq = radius * radius;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null || !player.getWorld().equals(center.getWorld()) || player.getLocation().distanceSquared(center) > radiusSq) continue;
            for (String text : message) {
                player.sendMessage(replace.apply(text));
            }
        }
    }

    public static void sendActions(@NotNull Player player, @NotNull List<String> actions) {
        for (String action : actions) {
            String text;
            if (action.startsWith("CHAT:")) {
                text = action.replace("CHAT:", "");
                player.chat(text);
                continue;
            }
            if (action.startsWith("MESSAGE:")) {
                text = action.replace("MESSAGE:", "");
                player.sendMessage(text);
                continue;
            }
            if (action.startsWith("COMMAND:")) {
                text = action.replace("COMMAND:", "");
                Bukkit.dispatchCommand(player, text);
                continue;
            }
            if (action.startsWith("BROADCAST:")) {
                text = action.replace("BROADCAST:", "").replace("{playerName}", player.getName());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (pl == null) continue;
                    pl.sendMessage(text);
                }
                continue;
            }
            if (!action.startsWith("CONSOLE:")) continue;
            text = action.replace("CONSOLE:", "").replace("{playerName}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text);
        }
    }

    public static void sendActions(@NotNull List<String> actions) {
        for (String action : actions) {
            String text;
            if (action.startsWith("BROADCAST:")) {
                text = action.replace("BROADCAST:", "");
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (pl == null) continue;
                    pl.sendMessage(text);
                }
                continue;
            }
            if (!action.startsWith("CONSOLE:")) continue;
            text = action.replace("CONSOLE:", "");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text);
        }
    }

}
