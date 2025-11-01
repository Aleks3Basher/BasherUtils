package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTBoolean implements NBTBase {

    private final NBTType type = NBTType.BOOLEAN;
    private boolean value;

    public NBTBoolean(boolean value) {
        this.value = value;
    }

    public NBTBoolean(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeBoolean(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readBoolean();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTBoolean(value);
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }

}
