package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

@Getter
@Setter
public class NBTUuid implements NBTBase {

    private final NBTType type = NBTType.LONG;
    private UUID value;

    public NBTUuid(UUID value) {
        this.value = value;
    }

    public NBTUuid(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        out.writeLong(value.getMostSignificantBits());
        out.writeLong(value.getLeastSignificantBits());
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        value = new UUID(in.readLong(), in.readLong());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
