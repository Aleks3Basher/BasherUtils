package ru.basher.utils.location;

import java.util.Objects;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Getter
public class Direction {

    private final float yaw;
    private final float pitch;
    private final int hashCode;

    public Direction(@NotNull Location start, @NotNull Location end) {
        double x = end.getX() - start.getX();
        double y = end.getY() - start.getY();
        double z = end.getZ() - start.getZ();
        if (x == 0.0 && z == 0.0) {
            this.yaw = 0.0F;
            this.pitch = y > 0.0 ? -90.0F : 90.0F;
        } else {
            double theta = Math.atan2(-x, z);
            this.yaw = (float)Math.toDegrees((theta + (Math.PI * 2)) % (Math.PI * 2));
            double xz = Math.sqrt(x * x + z * z);
            this.pitch = (float)Math.toDegrees(Math.atan(-y / xz));
        }

        this.hashCode = Objects.hash(this.yaw, this.pitch);
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Direction obj = (Direction)o;
            return this.yaw == obj.yaw && this.pitch == obj.pitch;
        } else {
            return false;
        }
    }

}
