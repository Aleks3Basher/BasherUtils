package ru.basher.utils.schema.point;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.location.V3;
import ru.basher.utils.nbt.NBTBase;
import ru.basher.utils.nbt.NBTCompound;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class SchemaPoints {

    private final Map<V3, SchemaPoint> points = new HashMap<>();

    public SchemaPoints() {}

    public SchemaPoints(NBTCompound compound) {
        for(Map.Entry<String, NBTBase> entry : compound.getTags().entrySet()) {
            String[] locationArr = entry.getKey().split(";");
            if(locationArr.length != 3) continue;
            try {
                V3 location = new V3(
                        Integer.parseInt(locationArr[0]),
                        Integer.parseInt(locationArr[1]),
                        Integer.parseInt(locationArr[2])
                );
                String text = entry.getValue().asString();
                points.put(location, new SchemaPoint(location, text));
            } catch (Exception ignored) {}
        }
    }

    public @Nullable SchemaPoint get(@NotNull V3 location) {
        return points.get(location);
    }

    public void put(@NotNull SchemaPoint point) {
        points.put(point.getLocation(), point);
    }

    public void remove(@NotNull V3 location) {
        points.remove(location);
    }

    public @NotNull Set<V3> getKeys() {
        return points.keySet();
    }

    public @NotNull Collection<SchemaPoint> getValues() {
        return points.values();
    }

    public @NotNull NBTCompound toCompound() {
        NBTCompound compound = new NBTCompound();
        for(Map.Entry<V3, SchemaPoint> entry : points.entrySet()) {
            V3 loc = entry.getKey();
            compound.setString(loc.getX() + ";" + loc.getY() + ";" + loc.getZ(), entry.getValue().getText());
        }
        return compound;
    }

}
