package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTShort implements NBTBase {

    private final NBTType type = NBTType.SHORT;
    private short value;

    public NBTShort(short value) {
        this.value = value;
    }

    public NBTShort(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeShort(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readShort();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTShort(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
