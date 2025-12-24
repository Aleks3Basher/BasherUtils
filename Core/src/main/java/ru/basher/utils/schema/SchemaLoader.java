package ru.basher.utils.schema;

import org.jetbrains.annotations.NotNull;
import ru.basher.utils.nbt.NBTCompound;

import java.io.File;
import java.nio.file.Files;

public class SchemaLoader {

    public static @NotNull Schema load(@NotNull File folder, @NotNull String id) throws Exception {
        File file = new File(folder, id + ".bschem");
        return load(file);
    }

    public static @NotNull Schema load(@NotNull File file) throws Exception {
        if (!file.exists() || !file.getName().endsWith(".bschem")) throw new RuntimeException("Schema file does not exist or is not a .bschem");
        String line = Files.readString(file.toPath());
        NBTCompound compound = NBTCompound.deserialize(line);
        return new Schema(file.getName().replace(".bschem", ""), compound);
    }

    public static void save(@NotNull File folder, @NotNull Schema schema) throws Exception {
        if (!folder.exists() && !folder.mkdirs()) throw new RuntimeException("Failed to create directories");

        File file = new File(folder, schema.getId() + ".bschem");
        if (!file.exists() && !file.createNewFile()) throw new RuntimeException("Failed to create file");

        NBTCompound compound = schema.toCompound();
        Files.writeString(file.toPath(), compound.serialize());
    }

}
