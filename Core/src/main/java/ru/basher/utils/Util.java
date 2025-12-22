package ru.basher.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
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
            } else if (action.startsWith("MESSAGE:")) {
                text = action.replace("MESSAGE:", "");
                player.sendMessage(text);
            } else if (action.startsWith("COMMAND:")) {
                text = action.replace("COMMAND:", "");
                player.chat("/" + text);
            } else if (action.startsWith("BROADCAST:")) {
                text = action.replace("BROADCAST:", "").replace("{playerName}", player.getName());
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (target == null) continue;
                    target.sendMessage(text);
                }
            } else if (action.startsWith("CONSOLE:")) {
                text = action.replace("CONSOLE:", "").replace("{playerName}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text);
            } else if (action.startsWith("SOUND:")) {
                text = action.replace("SOUND:", "");
                String[] arr = text.split(";");
                try {
                    Sound sound = Sound.valueOf(arr[0].toUpperCase());
                    float volume = Float.parseFloat(arr[1]);
                    float pitch = Float.parseFloat(arr[2]);
                    player.playSound(player.getLocation(), sound, volume, pitch);
                } catch (Exception ignored) {
                }
            }
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
