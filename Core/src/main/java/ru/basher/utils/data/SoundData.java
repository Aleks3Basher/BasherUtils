package ru.basher.utils.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class SoundData {
    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SoundData() {
        sound = Sound.BLOCK_BEACON_POWER_SELECT;
        volume = 1.0f;
        pitch = 1.0f;
    }

    public SoundData(@NotNull ConfigurationSection section) {
        this.sound = Sound.valueOf(section.getString("id", "BLOCK_BEACON_POWER_SELECT").toUpperCase());
        this.volume = (float)section.getDouble("volume", 0.0);
        this.pitch = (float)section.getDouble("pitch", 0.0);
    }

    public void play(@NotNull Player player) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void play(@NotNull Location location) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }
}