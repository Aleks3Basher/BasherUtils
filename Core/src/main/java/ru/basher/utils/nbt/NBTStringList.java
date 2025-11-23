package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NBTStringList implements NBTBase {

    private final NBTType type = NBTType.STRING_LIST;
    private List<String> value;

    public NBTStringList(@NotNull List<String> value) {
        this.value = value;
    }

    public NBTStringList(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeInt(value.size());
        for(String str : value) {
            out.writeUTF(str);
        }
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        int size = in.readInt();
        value = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            value.add(in.readUTF());
        }
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTStringList(new ArrayList<>(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
