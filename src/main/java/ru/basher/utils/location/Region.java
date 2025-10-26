package ru.basher.utils.location;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.nbt.NBTCompound;

@Getter
@Setter
public class Region {

    private String world;
    private V3 minLocation;
    private V3 maxLocation;

    public Region() {
        this("world", new V3(), new V3());
    }

    public Region(String world, V3 minLocation, V3 maxLocation) {
        this.world = world;
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
    }

    public boolean intersections(@NotNull Region other) {
        return other.getWorld().equals(this.world) && this.intersections(other.getMinLocation(), other.getMaxLocation());
    }

    public boolean intersections(@NotNull Loc loc) {
        return loc.getWorld().equals(this.world) && this.intersections(loc.getX(), loc.getY(), loc.getZ());
    }

    public boolean intersections(@NotNull V3 location) {
        return this.intersections(location.getX(), location.getY(), location.getZ());
    }

    public boolean intersections(int x, int y, int z) {
        if (x < this.minLocation.getX()) {
            return false;
        } else if (y < this.minLocation.getY()) {
            return false;
        } else if (z < this.minLocation.getZ()) {
            return false;
        } else if (x > this.maxLocation.getX()) {
            return false;
        } else if (y > this.maxLocation.getY()) {
            return false;
        } else {
            return z <= this.maxLocation.getZ();
        }
    }

    public boolean intersections(@NotNull V3 minLocation, @NotNull V3 maxLocation) {
        if (this.minLocation.getX() > maxLocation.getX()) {
            return false;
        } else if (this.maxLocation.getX() < minLocation.getX()) {
            return false;
        } else if (this.minLocation.getY() > maxLocation.getY()) {
            return false;
        } else if (this.maxLocation.getY() < minLocation.getY()) {
            return false;
        } else if (this.minLocation.getZ() > maxLocation.getZ()) {
            return false;
        } else {
            return this.maxLocation.getZ() >= minLocation.getZ();
        }
    }

    public String toString() {
        return "Region{" + this.world + "," + this.minLocation + "," + this.maxLocation + "}";
    }

    public int hashCode() {
        return Objects.hash(this.world, this.minLocation, this.maxLocation);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Region obj = (Region)o;
            return this.world.equals(obj.world) && this.minLocation.equals(obj.minLocation) && this.maxLocation.equals(obj.maxLocation);
        } else {
            return false;
        }
    }

    @NotNull
    public String serialize() {
        NBTCompound compound = new NBTCompound();
        compound.setString("world", this.world);
        compound.setString("minLoc", this.minLocation.serialize());
        compound.setString("maxLoc", this.maxLocation.serialize());
        return compound.serialize();
    }

    @NotNull
    public static Region deserialize(@NotNull String data) {
        NBTCompound compound = NBTCompound.deserialize(data);
        return new Region(compound.getString("world", "world"), V3.deserialize(compound.getString("minLoc")), V3.deserialize(compound.getString("maxLoc")));
    }

}
