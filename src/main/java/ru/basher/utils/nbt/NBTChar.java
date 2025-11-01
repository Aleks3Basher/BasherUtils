package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTChar implements NBTBase {

    private final NBTType type = NBTType.CHAR;
    private char value;

    public NBTChar(char value) {
        this.value = value;
    }

    public NBTChar(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeChar(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readChar();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTChar(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
