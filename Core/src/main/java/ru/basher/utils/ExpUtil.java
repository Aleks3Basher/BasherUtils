package ru.basher.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExpUtil {

    public static void takeExpLevels(@NotNull Player player, int takeLevels) {
        double progress = Math.floor( (float)deltaLevelToExp(player.getLevel()) * player.getExp() );
        int newExp = (int)((double)levelToExp(player.getLevel() - takeLevels) + progress);
        setTotalExperience(player, newExp);
    }

    public static void giveExpLevels(@NotNull Player player, int giveLevels) {
        double progress = Math.floor((float)deltaLevelToExp(player.getLevel()) * player.getExp());
        int newExp = (int)((double)levelToExp(player.getLevel() + giveLevels) + progress);
        setTotalExperience(player, newExp);
    }

    public static int getPlayerExperience(@NotNull Player player) {
        return (int)((double)levelToExp(player.getLevel()) + Math.floor((float)deltaLevelToExp(player.getLevel()) * player.getExp()));
    }

    public static void setTotalExperience(Player player, int xp) {
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0.0F);
        player.setTotalExperience(0);
        if (xp >= 1) {
            player.giveExp(xp);
            int xpForLevel = levelToExp(player.getLevel());
            int delta = deltaLevelToExp(player.getLevel());
            player.setExp((float)(xp - xpForLevel) / (float)delta);
        }
    }

    public static int levelToExp(int level) {
        if (level <= 15) {
            return level * level + 6 * level;
        } else {
            return level <= 30
                    ? (int)(2.5 * (double)level * (double)level - 40.5 * (double)level + 360.0)
                    : (int)(4.5 * (double)level * (double)level - 162.5 * (double)level + 2220.0);
        }
    }

    public static int deltaLevelToExp(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else {
            return level <= 30 ? 5 * level - 38 : 9 * level - 158;
        }
    }

    public static int getExpAtLevel(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else {
            return level <= 30 ? 5 * level - 38 : 9 * level - 158;
        }
    }

    public static int getExpToLevel(int level) {
        int exp = 0;

        for(int currentLevel = 0; currentLevel < level; ++currentLevel) {
            exp += getExpAtLevel(currentLevel);
        }

        if (exp < 0) exp = Integer.MAX_VALUE;

        return exp;
    }

    private static int currentLevelExpDelta(@NotNull Player player) {
        return deltaLevelToExp(player.getLevel())
                - (levelToExp(player.getLevel()) + Math.round((float)deltaLevelToExp(player.getLevel()) * player.getExp()) - levelToExp(player.getLevel()));
    }

    public static int expToLevel(double sourceLeftExp, double d) {
        if (d > 21863.0) d = 21863.0;

        for(int i = 1; i <= d + 1.0; ++i) {
            double levelExp = levelToExp(i);
            if (levelExp > sourceLeftExp) {
                return i - 1;
            }
        }

        return 0;
    }

}
