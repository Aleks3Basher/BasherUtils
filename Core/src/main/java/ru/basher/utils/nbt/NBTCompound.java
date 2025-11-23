package ru.basher.utils.nbt;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;

public class NBTCompound implements NBTBase {

    private final NBTType type = NBTType.COMPOUND;
    @Getter
    private final Map<String, NBTBase> tags = new HashMap<>();

    public NBTCompound() {
    }

    public NBTCompound(Map<String, NBTBase> tags) {
        this.tags.putAll(tags);
    }

    public NBTCompound(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }
    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeInt(tags.size());
        for (Map.Entry<String, NBTBase> entry : tags.entrySet()) {
            out.writeUTF(entry.getKey());
            entry.getValue().write(out);
        }
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readUTF();
            byte typeId = in.readByte();
            NBTType type = NBTType.getById(typeId);
            NBTBase value = NBTFactory.createNBT(type, in);
            tags.put(key, value);
        }
    }

    public boolean has(@NotNull String key) {
        return tags.containsKey(key);
    }

    public @NotNull NBTCompound set(@NotNull String key, @NotNull NBTBase nbtBase) {
        tags.put(key, nbtBase);
        return this;
    }
    @NotNull
    public NBTBase get(@NotNull String key) {
        NBTBase base = tags.get(key);
        return base == null ? new NBTEmpty() : base;
    }
    public @NotNull NBTCompound remove(@NotNull String key) {
        tags.remove(key);
        return this;
    }

    public @NotNull NBTCompound setEmpty(@NotNull String key) {
        tags.put(key, new NBTEmpty());
        return this;
    }

    @NotNull
    public NBTEmpty getEmpty(@NotNull String key) {
        NBTEmpty nbt = getEmpty(key, null);
        return nbt == null ? new NBTEmpty() : nbt;
    }
    public NBTEmpty getEmpty(@NotNull String key, NBTEmpty def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTEmpty) return (NBTEmpty)base;
        else return def;
    }

    public @NotNull NBTCompound setBoolean(@NotNull String key, boolean value) {
        tags.put(key, new NBTBoolean(value));
        return this;
    }
    public boolean getBoolean(@NotNull String key) {
        return getBoolean(key, false);
    }
    public boolean getBoolean(@NotNull String key, boolean def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTBoolean) return ((NBTBoolean)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setByte(@NotNull String key, byte value) {
        tags.put(key, new NBTByte(value));
        return this;
    }
    public byte getByte(@NotNull String key) {
        return getByte(key, (byte)0);
    }
    public byte getByte(@NotNull String key, byte def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTByte) return ((NBTByte)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setChar(@NotNull String key, char value) {
        tags.put(key, new NBTChar(value));
        return this;
    }
    public char getChar(@NotNull String key) {
        return getChar(key, (char) 0);
    }
    public char getChar(@NotNull String key, char def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTChar) return ((NBTChar)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setShort(@NotNull String key, short value) {
        tags.put(key, new NBTShort(value));
        return this;
    }
    public short getShort(@NotNull String key) {
        return getShort(key, (short) 0);
    }
    public short getShort(@NotNull String key, short def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTShort) return ((NBTShort)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setInt(@NotNull String key, int value) {
        tags.put(key, new NBTInt(value));
        return this;
    }
    public int getInt(@NotNull String key) {
        return getInt(key, 0);
    }
    public int getInt(@NotNull String key, int def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTInt) return ((NBTInt)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setLong(@NotNull String key, long value) {
        tags.put(key, new NBTLong(value));
        return this;
    }
    public long getLong(@NotNull String key) {
        return getLong(key, 0L);
    }
    public long getLong(@NotNull String key, long def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTLong) return ((NBTLong)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setFloat(@NotNull String key, float value) {
        tags.put(key, new NBTFloat(value));
        return this;
    }
    public float getFloat(@NotNull String key) {
        return getFloat(key, 0.0f);
    }
    public float getFloat(@NotNull String key, float def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTFloat) return ((NBTFloat)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setDouble(@NotNull String key, double value) {
        tags.put(key, new NBTDouble(value));
        return this;
    }
    public double getDouble(@NotNull String key) {
        return getDouble(key, 0.0);
    }
    public double getDouble(@NotNull String key, double def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTDouble) return ((NBTDouble)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setString(@NotNull String key, @NotNull String value) {
        tags.put(key, new NBTString(value));
        return this;
    }

    public @NotNull String getString(@NotNull String key) {
        return getString(key, "");
    }
    public String getString(@NotNull String key, String def) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTString) return ((NBTString)base).getValue();
        else return def;
    }

    public @NotNull NBTCompound setCompound(@NotNull String key, @NotNull NBTCompound value) {
        tags.put(key, value);
        return this;
    }

    public @NotNull NBTCompound getCompound(@NotNull String key) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTCompound) return (NBTCompound) base;
        else return new NBTCompound();
    }

    public @NotNull NBTCompound setUuid(@NotNull String key, @NotNull UUID value) {
        tags.put(key, new NBTUuid(value));
        return this;
    }
    @NotNull
    public UUID getUuid(@NotNull String key) {
        UUID uuid = getUuid(key, null);
        return uuid == null ? UUID.randomUUID() : uuid;
    }
    public UUID getUuid(@NotNull String key, UUID uuid) {
        NBTBase base = tags.get(key);
        if (base instanceof NBTUuid) return ((NBTUuid)base).getValue();
        else return uuid;
    }

    public @NotNull NBTCompound setStringList(@NotNull String key, @NotNull List<String> list) {
        tags.put(key, new NBTStringList(list));
        return this;
    }
    public @NotNull List<String> getStringList(@NotNull String key) {
        List<String> list = getStringList(key, null);
        return list == null ? new ArrayList<>() : list;
    }
    public List<String> getStringList(@NotNull String key, List<String> def) {
        NBTBase base = tags.get(key);
        if(base instanceof NBTStringList stringList) return stringList.getValue();
        else return def;
    }
    public @NotNull NBTCompound setItemStack(@NotNull String key, @NotNull ItemStack value) {
        tags.put(key, new NBTItemStack(value));
        return this;
    }
    public @NotNull ItemStack getItemStack(@NotNull String key) {
        ItemStack itemStack = getItemStack(key, null);
        return itemStack == null ? new ItemStack(Material.STONE) : itemStack;
    }
    public ItemStack getItemStack(@NotNull String key, ItemStack def) {
        NBTBase base = tags.get(key);
        if(base instanceof NBTItemStack itemStack) return itemStack.getValue();
        else return def;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("{");
        Collection<String> collection = tags.keySet();
        for (String s : collection) {
            if (stringbuilder.length() != 1) {
                stringbuilder.append(',');
            }
            stringbuilder.append(s).append(':').append(tags.get(s));
        }
        return stringbuilder.append('}').toString();
    }

    @Override
    public @NotNull NBTCompound copy() {
        NBTCompound compound = new NBTCompound();
        for(Map.Entry<String, NBTBase> entry : tags.entrySet()) {
            compound.set(entry.getKey(), entry.getValue().copy());
        }
        return compound;
    }

    @NotNull
    public String serialize() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);
        try {
            write(dataOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    @NotNull
    public static NBTCompound deserialize(@Nullable String value) {
        if (value == null || value.isEmpty()) return new NBTCompound();
        byte[] bytes = Base64.getDecoder().decode(value);
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            int id = in.readByte();
            NBTType type = NBTType.getById(id);
            if(type != NBTType.COMPOUND) throw new RuntimeException();
            return new NBTCompound(in);
        } catch (Exception e) {
            throw new RuntimeException("Error to deserialize NBTCompound with value: " + value, e);
        }
    }

}
