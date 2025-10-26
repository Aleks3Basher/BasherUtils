package ru.basher.utils.location;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.nbt.NBTCompound;

@Getter
@Setter
public class Loc {

    private final String world;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;

    public Loc() {
        this("world", 0, 0, 0, 0.0f, 0.0f);
    }

    public Loc(@NotNull ConfigurationSection section) {
        this(section.getString("world", "world"), section.getInt("x", 0), section.getInt("y", 0), section.getInt("z", 0));
    }

    public Loc(@NotNull Location location) {
        this(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Loc(@NotNull String world, double x, double y, double z) {
        this(world, (int)x, (int)y, (int)z);
    }

    public Loc(@NotNull String world, int x, int y, int z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public Loc(@NotNull String world, int x, int y, int z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Loc(@NotNull Location location, boolean withDirection) {
        this.world = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        if (withDirection) {
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        } else {
            this.yaw = 0.0F;
            this.pitch = 0.0F;
        }
    }

    @NotNull
    public Loc add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @NotNull
    public Location toBukkitLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) {
            throw new RuntimeException("Loc.toBukkitLocation() world cannot be null");
        } else {
            return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }

    @NotNull
    public Location toBukkitLocation(@NotNull World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public String toString() {
        return "Loc{" + this.world + "," + this.x + "," + this.y + "," + this.z + "," + this.yaw + "," + this.pitch + "}";
    }

    public int hashCode() {
        return Objects.hash(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public boolean equal(@NotNull Location location) {
        World world = location.getWorld();
        if (world == null) {
            return false;
        } else {
            return world.getName().equals(this.world) && this.x == location.getBlockX() && this.y == location.getBlockY() && this.z == location.getBlockZ();
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Loc obj = (Loc)o;
            return this.world.equalsIgnoreCase(obj.world)
                    && this.x == obj.x
                    && this.y == obj.y
                    && this.z == obj.z
                    && this.yaw == obj.yaw
                    && this.pitch == obj.pitch;
        } else {
            return false;
        }
    }

    @NotNull
    public Loc copy() {
        return new Loc(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    @NotNull
    public String serialize() {
        NBTCompound compound = new NBTCompound();
        compound.setString("world", this.world);
        compound.setInt("x", this.x);
        compound.setInt("y", this.y);
        compound.setInt("z", this.z);
        compound.setFloat("yaw", this.yaw);
        compound.setFloat("pitch", this.pitch);
        return compound.serialize();
    }

    @NotNull
    public static Loc deserialize(@NotNull String data) {
        NBTCompound compound = NBTCompound.deserialize(data);
        return new Loc(
                compound.getString("world", ""),
                compound.getInt("x", 0),
                compound.getInt("y", 0),
                compound.getInt("z", 0),
                compound.getFloat("yaw", 0.0F),
                compound.getFloat("pitch", 0.0F)
        );
    }

}
