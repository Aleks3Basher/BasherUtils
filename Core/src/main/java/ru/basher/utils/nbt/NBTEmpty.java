package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTEmpty implements NBTBase {

    private final NBTType type = NBTType.EMPTY;

    public NBTEmpty() {}

    public NBTEmpty(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {}

    @Override
    public @NotNull NBTBase copy() {
        return new NBTEmpty();
    }

    @Override
    public String toString() {
        return "";
    }

}
