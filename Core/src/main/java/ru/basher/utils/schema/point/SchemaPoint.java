package ru.basher.utils.schema.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.location.V3;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchemaPoint {

    private final V3 location;
    private String text;

    @Override
    public @NotNull String toString() {
        return "SchemaPoint{location=" + location + ", text=" + text + "}";
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(final @Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        SchemaPoint object = (SchemaPoint) obj;
        return location.equals(object.getLocation());
    }

}
