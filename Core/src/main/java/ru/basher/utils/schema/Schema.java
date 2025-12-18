package ru.basher.utils.schema;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.location.V3;
import ru.basher.utils.nbt.NBTBase;
import ru.basher.utils.nbt.NBTCompound;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Schema {

    private final String id;

    private final Map<V3, BlockData> blocks = new HashMap<>();

    public Schema(String id) {
        this.id = id;
    }

    public Schema(String id, Map<V3, BlockData> blocks) {
        this.id = id;
        this.blocks.putAll(blocks);
    }

    public Schema(String id, NBTCompound compound) {
        this.id = id;
        NBTCompound blocksComp = compound.getCompound("blocks");
        for (Map.Entry<String, NBTBase> entry : blocksComp.getTags().entrySet()) {
            String[] locationArr = entry.getKey().split(";");
            if (locationArr.length != 3) continue;
            try {
                V3 location = new V3(Integer.parseInt(locationArr[0]), Integer.parseInt(locationArr[1]), Integer.parseInt(locationArr[2]));
                BlockData blockData = Bukkit.createBlockData(entry.getValue().asString());
                blocks.put(location, blockData);
            } catch (Exception ignored) {
            }
        }
    }

    public void placeAt(@NotNull Location location) {
        placeAt(location.getWorld(), new V3(location));
    }

    public void placeAt(@NotNull World world, @NotNull V3 location) {
        for (Map.Entry<V3, BlockData> entry : blocks.entrySet()) {
            V3 offset = entry.getKey();
            world.getBlockAt(
                    location.getX() + offset.getX(),
                    location.getY() + offset.getY(),
                    location.getZ() + offset.getZ()
            ).setBlockData(entry.getValue().clone());
        }
    }

    public void setBlocks(@NotNull Location oneLocation, @NotNull Location twoLocation) {
        setBlocks(oneLocation.getWorld(), new V3(oneLocation), new V3(twoLocation));
    }

    public void setBlocks(@NotNull World world, @NotNull V3 oneLocation, @NotNull V3 twoLocation) {
        V3 minLoc = V3.min(oneLocation, twoLocation);
        V3 maxLoc = V3.max(oneLocation, twoLocation);

        blocks.clear();
        for (int x = minLoc.getX(); x <= maxLoc.getX(); x++) {
            for (int y = minLoc.getY(); y <= maxLoc.getY(); y++) {
                for (int z = minLoc.getZ(); z <= maxLoc.getZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if(block.getType().isAir()) continue;

                    V3 offset = new V3(x - minLoc.getX(), y - minLoc.getY(), z - minLoc.getZ());
                    blocks.put(offset, block.getBlockData().clone());
                }
            }
        }
    }

    public @NotNull NBTCompound toCompound() {
        NBTCompound compound = new NBTCompound();
        NBTCompound blocksComp = new NBTCompound();
        for (Map.Entry<V3, BlockData> entry : blocks.entrySet()) {
            V3 loc = entry.getKey();
            blocksComp.setString(loc.getX() + ";" + loc.getY() + ";" + loc.getZ(), entry.getValue().getAsString());
        }
        compound.set("blocks", blocksComp);
        return compound;
    }

    @Override
    public String toString() {
        StringBuilder blocksBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<V3, BlockData> entry : blocks.entrySet()) {
            if (!first) blocksBuilder.append(",");
            else first = false;
            blocksBuilder.append("{").append(entry.getKey().toString()).append(":").append(entry.getValue().getAsString()).append("}");
        }
        return "Schema{" + id + "," + blocksBuilder + "}";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        else if (o != null && this.getClass() == o.getClass()) {
            Schema obj = (Schema) o;
            return id.equals(obj.id);
        } else return false;
    }
}
