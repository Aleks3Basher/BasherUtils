package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTLong implements NBTBase {

    private final NBTType type = NBTType.LONG;
    private long value;

    public NBTLong(long value) {
        this.value = value;
    }

    public NBTLong(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeLong(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readLong();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTLong(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
