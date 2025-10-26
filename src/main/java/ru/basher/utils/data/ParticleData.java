package ru.basher.utils.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class ParticleData {

    private final Particle particle;
    private final int amount;
    private final float speed;

    public ParticleData() {
        particle = Particle.CLOUD;
        amount = 1;
        speed = 1.0f;
    }

    public ParticleData(@NotNull ConfigurationSection section) {
        this.particle = Particle.valueOf(section.getString("particle", "CLOUD").toUpperCase());
        this.amount = section.getInt("amount", 1);
        this.speed = (float)section.getDouble("speed", 1.0);
    }

    public void spawn(@NotNull Location location) {
        location.getWorld().spawnParticle(particle, location, amount, 0, 0, 0, speed);
    }

}
