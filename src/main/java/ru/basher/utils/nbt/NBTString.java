package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTString implements NBTBase {

    private final NBTType type = NBTType.STRING;
    private String value;

    public NBTString(@NotNull String value) {
        this.value = value;
    }

    public NBTString(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeUTF(value);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = in.readUTF();
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTString(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
