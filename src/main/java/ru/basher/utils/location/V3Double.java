package ru.basher.utils.location;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.NumberUtil;

@Getter
@AllArgsConstructor
public class V3Double {

    private double x;
    private double y;
    private double z;

    public V3Double() {
        this(0.0, 0.0, 0.0);
    }

    public V3Double(Location location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public V3Double(ConfigurationSection section) {
        this(section.getDouble("x", 0), section.getDouble("y", 0), section.getDouble("z", 0));
    }

    @NotNull
    public V3Double setX(double x) {
        this.x = x;
        return this;
    }

    @NotNull
    public V3Double setY(double y) {
        this.y = y;
        return this;
    }

    @NotNull
    public V3Double setZ(double z) {
        this.z = z;
        return this;
    }

    @NotNull
    public V3Double add(@NotNull V3 v3) {
        return this.add(v3.getX(), v3.getY(), v3.getZ());
    }

    @NotNull
    public V3Double add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public double distance(@NotNull V3Double target) {
        return NumberUtil.sqrt(this.distanceSquared(target));
    }

    public double distanceSquared(@NotNull V3Double target) {
        return NumberUtil.square(this.x - target.getX()) + NumberUtil.square(this.y - target.getY()) + NumberUtil.square(this.z - target.getZ());
    }

    @NotNull
    public V3Double getVector(@NotNull V3Double endPoint) {
        return new V3Double(endPoint.getX() - this.x, endPoint.getY() - this.y, endPoint.getZ() - this.z);
    }

    public boolean intersection(@NotNull V3Double min, @NotNull V3Double max) {
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
        return "V3Double{" + this.x + "," + this.y + "," + this.z + "}";
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            V3Double obj = (V3Double)o;
            return this.x == obj.x && this.y == obj.y && this.z == obj.z;
        } else {
            return false;
        }
    }

    public V3Double copy() {
        return new V3Double(this.x, this.y, this.z);
    }

    public static V3Double min(@NotNull V3Double one, @NotNull V3Double two) {
        return new V3Double(Math.min(one.getX(), two.getX()), Math.min(one.getY(), two.getY()), Math.min(one.getZ(), two.getZ()));
    }

    public static V3Double max(@NotNull V3Double one, @NotNull V3Double two) {
        return new V3Double(Math.max(one.getX(), two.getX()), Math.max(one.getY(), two.getY()), Math.max(one.getZ(), two.getZ()));
    }

    public static V3Double center(@NotNull V3Double one, @NotNull V3Double two) {
        return new V3Double((one.getX() + two.getX()) / 2.0, (one.getY() + two.getY()) / 2.0, (one.getZ() + two.getZ()) / 2.0);
    }

}
