package ru.basher.utils.text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextUtil {

    private static final DecimalFormat intFormat = new DecimalFormat("###,###");
    private static final DecimalFormat doubleFormat = new DecimalFormat("###,###.##");

    @NotNull
    public static String toColor(@Nullable String legacyMsg) {
        if (legacyMsg == null) return "";

        String message = legacyMsg.replace("§", "&");

        String hex;
        for (Matcher matcher = Pattern.compile("&#[A-Fa-f0-9]{6}").matcher(message);
             matcher.find();
             message = message.replace("&" + hex, ChatColor.of(hex) + "")
        ) {
            hex = matcher.group().substring(1);
        }

        return message.replace("&", "§");
    }

    @NotNull
    public static String toColor(@Nullable String text, @NotNull List<TextColor> colors) {
        if (text == null) return "";
        String message = text.replace("§", "&");
        String hex;
        if (colors.contains(TextColor.HEX)) {
            for (Matcher matcher = Pattern.compile("&#[A-Fa-f0-9]{6}").matcher(message);
                 matcher.find();
                 message = message.replace("&" + hex, ChatColor.of(hex) + "")
            ) {
                hex = matcher.group().substring(1);
            }
        } else {
            message = message.replaceAll("&#[A-Fa-f0-9]{6}", "");
        }

        for (TextColor type : colors) {
            if (type != TextColor.HEX) {
                message = message.replace("&" + type.getCharId(), "§" + type.getCharId());
            }
        }

        if (message.contains("&")) {
            message = message.replaceAll("&[0-9a-fA-Fk-oK-OrR]", "");
        }

        return message;
    }

    public static void putAndColor(ConfigurationSection config, @NotNull List<String> to, @NotNull String path) {
        to.clear();
        for (String s : config.getStringList(path)) {
            if (s == null) continue;
            to.add(toColor(s));
        }
    }

    @NotNull
    public static String removeColors(@NotNull String text) {
        return text.replace("§", "&").replaceAll("&#[A-Fa-f0-9]{6}", "").replaceAll("&[A-Fa-fK-Ok-o0-9rR]", "");
    }

    @NotNull
    public static String formatNumber(float number) {
        return doubleFormat.format(number);
    }

    @NotNull
    public static String formatNumber(double number) {
        return doubleFormat.format(number);
    }

    @NotNull
    public static String formatNumber(long number) {
        return intFormat.format(number);
    }

    @NotNull
    public static String formatNumber(int number) {
        return intFormat.format(number);
    }

    public static @NotNull String formatTime(long allSeconds) {
        int days = (int) (allSeconds / 86400L);
        int hours = (int) (allSeconds % 86400L / 3600L);
        int minutes = (int) (allSeconds % 3600L / 60L);
        StringBuilder time = new StringBuilder();
        if (days > 0) {
            time.append(days).append(" ").append(getForm(days, "день", "дня", "дней"));
            if (hours != 0 || minutes != 0) {
                time.append(" ");
            }
        }

        if (hours > 0) {
            time.append(hours).append(" ").append(getForm(hours, "час", "часа", "часов"));
            if (minutes != 0) {
                time.append(" ");
            }
        }

        if (minutes > 0 || time.isEmpty()) {
            time.append(minutes).append(" ").append(getForm(minutes, "минута", "минуты", "минут"));
        }

        return time.toString();
    }

    public static @NotNull String formatTimeWithSeconds(long allSeconds) {
        int days = (int) (allSeconds / 86400L);
        int hours = (int) (allSeconds % 86400L / 3600L);
        int minutes = (int) (allSeconds % 3600L / 60L);
        int seconds = (int) (allSeconds % 60L);
        StringBuilder time = new StringBuilder();
        if (days > 0) {
            time.append(days).append(" ").append(getForm(days, "день", "дня", "дней"));
            if (hours != 0 || minutes != 0 || seconds != 0) {
                time.append(" ");
            }
        }

        if (hours > 0) {
            time.append(hours).append(" ").append(getForm(hours, "час", "часа", "часов"));
            if (minutes != 0 || seconds != 0) {
                time.append(" ");
            }
        }

        if (minutes > 0) {
            time.append(minutes).append(" ").append(getForm(minutes, "минута", "минуты", "минут"));
            if (seconds != 0) {
                time.append(" ");
            }
        }

        if (seconds > 0 || time.isEmpty()) {
            time.append(seconds).append(" ").append(getForm(seconds, "секунда", "секунды", "секунд"));
        }

        return time.toString();
    }

    private static String getForm(int number, String one, String few, String many) {
        number %= 100;
        if (number >= 11 && number <= 14) {
            return many;
        } else {
            return switch (number % 10) {
                case 1 -> one;
                case 2, 3, 4 -> few;
                default -> many;
            };
        }
    }

    public static @NotNull Component jsonToComponent(@NotNull String json) {
        if (json.isEmpty()) return Component.empty();
        try {
            return GsonComponentSerializer.gson().deserialize(json);
        } catch (Exception e) {
            return Component.empty();
        }
    }

}
