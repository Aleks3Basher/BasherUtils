package ru.basher.utils.location;

import java.util.Objects;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.NumberUtil;
import ru.basher.utils.nbt.NBTCompound;

@Getter
public class LocDouble {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public LocDouble(@NotNull ConfigurationSection section) {
        this(
                section.getString("world", "world"),
                section.getDouble("x", 0.0),
                section.getDouble("y", 0.0),
                section.getDouble("z", 0.0),
                (float)section.getDouble("yaw", 0.0),
                (float)section.getDouble("pitch", 0.0)
        );
    }

    public LocDouble(@NotNull String world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public LocDouble(@NotNull String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LocDouble(@NotNull Location location) {
        this(location, false);
    }

    public LocDouble(@NotNull Location location, boolean withDirection) {
        this(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                withDirection ? location.getYaw() : 0.0F,
                withDirection ? location.getPitch() : 0.0F
        );
    }

    public double distance(@NotNull Location target) {
        return this.distance(new LocDouble(target));
    }

    public double distance(@NotNull LocDouble target) {
        return NumberUtil.sqrt(this.distanceSquared(target));
    }

    public double distanceSquared(@NotNull LocDouble target) {
        return NumberUtil.square(this.x - target.getX()) + NumberUtil.square(this.y - target.getY()) + NumberUtil.square(this.z - target.getZ());
    }

    @NotNull
    public Location toBukkitLocation() {
        World world = Bukkit.getWorld(this.world);
        if (world == null) {
            throw new RuntimeException("[VCLib] LocDouble.toBukkitLocation() world cannot be null");
        } else {
            return this.toBukkitLocation(world);
        }
    }

    @NotNull
    public Location toBukkitLocation(@NotNull World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public String toString() {
        return "LocDouble{" + this.world + "," + this.x + "," + this.y + "," + this.z + "," + this.yaw + "," + this.pitch + "}";
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.world, this.x, this.y, this.z, this.yaw, this.pitch});
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            LocDouble obj = (LocDouble)o;
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
    public String serialize() {
        NBTCompound compound = new NBTCompound();
        compound.setString("world", this.world);
        compound.setDouble("x", this.x);
        compound.setDouble("y", this.y);
        compound.setDouble("z", this.z);
        compound.setFloat("yaw", this.yaw);
        compound.setFloat("pitch", this.pitch);
        return compound.serialize();
    }

    @NotNull
    public static LocDouble deserialize(@NotNull String data) {
        NBTCompound compound = NBTCompound.deserialize(data);
        return new LocDouble(
                compound.getString("world", ""),
                compound.getDouble("x", 0.0),
                compound.getDouble("y", 0.0),
                compound.getDouble("z", 0.0),
                compound.getFloat("yaw", 0.0F),
                compound.getFloat("pitch", 0.0F)
        );
    }

}
