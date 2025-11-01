package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTDouble implements NBTBase {

    private final NBTType type = NBTType.DOUBLE;
    private double value;

    public NBTDouble(double value) {
        this.value = value;
    }

    public NBTDouble(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeDouble(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readDouble();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTDouble(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
