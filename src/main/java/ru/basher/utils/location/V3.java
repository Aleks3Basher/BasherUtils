package ru.basher.utils.location;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.NumberUtil;
import ru.basher.utils.nbt.NBTCompound;

@Getter
@AllArgsConstructor
public class V3 {

    private int x;
    private int y;
    private int z;

    public V3() {
        this(0, 0, 0);
    }

    public V3(@NotNull Location location) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public V3(ConfigurationSection section) {
        this(section.getInt("x", 0), section.getInt("y", 0), section.getInt("z", 0));
    }

    @NotNull
    public V3 setX(int x) {
        this.x = x;
        return this;
    }

    @NotNull
    public V3 setY(int y) {
        this.y = y;
        return this;
    }

    @NotNull
    public V3 setZ(int z) {
        this.z = z;
        return this;
    }

    @NotNull
    public V3 add(@NotNull V3 v3) {
        return this.add(v3.getX(), v3.getY(), v3.getZ());
    }

    @NotNull
    public V3 add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public double distance(@NotNull V3 target) {
        return NumberUtil.sqrt(this.distanceSquared(target));
    }

    public double distanceSquared(@NotNull V3 target) {
        return NumberUtil.square(this.x - target.getX()) + NumberUtil.square(this.y - target.getY()) + NumberUtil.square(this.z - target.getZ());
    }

    @NotNull
    public V3 getVector(@NotNull V3 endPoint) {
        return new V3(endPoint.getX() - this.x, endPoint.getY() - this.y, endPoint.getZ() - this.z);
    }

    public boolean intersection(@NotNull V3 min, @NotNull V3 max) {
        if (min.getX() > this.x) {
            return false;
        } else if (min.getY() > this.y) {
            return false;
        } else if (min.getZ() > this.z) {
            return false;
        } else if (max.getX() < this.x) {
            return false;
        } else if (max.getY() < this.y) {
            return false;
        } else {
            return max.getZ() >= this.z;
        }
    }

    public String toString() {
        return "V3{" + this.x + "," + this.y + "," + this.z + "}";
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            V3 v3 = (V3)o;
            return this.x == v3.x && this.y == v3.y && this.z == v3.z;
        } else {
            return false;
        }
    }

    @NotNull
    public V3 copy() {
        return new V3(this.x, this.y, this.z);
    }

    @NotNull
    public static V3 min(@NotNull V3 one, @NotNull V3 two) {
        return new V3(Math.min(one.getX(), two.getX()), Math.min(one.getY(), two.getY()), Math.min(one.getZ(), two.getZ()));
    }

    @NotNull
    public static V3 max(@NotNull V3 one, @NotNull V3 two) {
        return new V3(Math.max(one.getX(), two.getX()), Math.max(one.getY(), two.getY()), Math.max(one.getZ(), two.getZ()));
    }

    @NotNull
    public static V3 center(@NotNull V3 one, @NotNull V3 two) {
        return new V3((one.getX() + two.getX()) / 2, (one.getY() + two.getY()) / 2, (one.getZ() + two.getZ()) / 2);
    }

    @NotNull
    public String serialize() {
        NBTCompound compound = new NBTCompound();
        compound.setInt("x", this.x);
        compound.setInt("y", this.y);
        compound.setInt("z", this.z);
        return compound.serialize();
    }

    @NotNull
    public static V3 deserialize(@NotNull String data) {
        NBTCompound compound = NBTCompound.deserialize(data);
        return new V3(compound.getInt("x", 0), compound.getInt("y", 0), compound.getInt("z", 0));
    }

    public static boolean intersections(@NotNull V3 min1, @NotNull V3 max1, @NotNull V3 min2, @NotNull V3 max2) {
        return max1.x >= min2.x && max2.x >= min1.x && max1.y >= min2.y && max2.y >= min1.y && max1.z >= min2.z && max2.z >= min1.z;
    }
}
