package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTInt implements NBTBase {

    private final NBTType type = NBTType.INT;
    private int value;

    public NBTInt(int value) {
        this.value = value;
    }

    public NBTInt(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeInt(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readInt();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTInt(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
