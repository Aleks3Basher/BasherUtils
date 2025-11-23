package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTByte implements NBTBase {

    private final NBTType type = NBTType.BYTE;
    private byte value;

    public NBTByte(byte value) {
        this.value = value;
    }

    public NBTByte(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeByte(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readByte();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTByte(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
