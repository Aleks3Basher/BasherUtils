package ru.basher.utils.location;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChunkUtil {

    @NotNull
    public static List<V3> getChunksAt(@NotNull Location minLocation, @NotNull Location maxLocation) {
        int minChunkX = minLocation.getBlockX() >> 4;
        int minChunkZ = minLocation.getBlockZ() >> 4;
        int maxChunkX = maxLocation.getBlockX() >> 4;
        int maxChunkZ = maxLocation.getBlockZ() >> 4;
        return getChunksAt(minChunkX, minChunkZ, maxChunkX, maxChunkZ);
    }

    @NotNull
    public static List<V3> getChunksAt(@NotNull Loc minLoc, @NotNull Loc maxLoc) {
        int minChunkX = minLoc.getX() >> 4;
        int minChunkZ = minLoc.getZ() >> 4;
        int maxChunkX = maxLoc.getX() >> 4;
        int maxChunkZ = maxLoc.getZ() >> 4;
        return getChunksAt(minChunkX, minChunkZ, maxChunkX, maxChunkZ);
    }

    @NotNull
    public static List<V3> getChunksAt(@NotNull V3 minLoc, @NotNull V3 maxLoc) {
        int minChunkX = minLoc.getX() >> 4;
        int minChunkZ = minLoc.getZ() >> 4;
        int maxChunkX = maxLoc.getX() >> 4;
        int maxChunkZ = maxLoc.getZ() >> 4;
        return getChunksAt(minChunkX, minChunkZ, maxChunkX, maxChunkZ);
    }

    @NotNull
    public static List<V3> getChunksAt(int minChunkX, int minChunkZ, int maxChunkX, int maxChunkZ) {
        List<V3> list = new ArrayList<>();

        for(int x = minChunkX; x <= maxChunkX; ++x) {
            for(int z = minChunkZ; z <= maxChunkZ; ++z) {
                V3 v3 = new V3(x, 0, z);
                if (!list.contains(v3)) {
                    list.add(v3);
                }
            }
        }

        return list;
    }
}
