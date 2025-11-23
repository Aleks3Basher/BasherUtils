package ru.basher.utils.nbt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public enum NBTType {

    EMPTY(0),
    BOOLEAN(1),
    BYTE(2),
    CHAR(3),
    SHORT(4),
    INT(5),
    LONG(6),
    FLOAT(7),
    DOUBLE(8),
    STRING(9),
    COMPOUND(10),
    UUID(11),
    STRING_LIST(12),
    ITEM_STACK(13);

    private final int id;

    public byte getId() {
        return (byte) id;
    }

    private static final NBTType[] types;
    static {
        types = values();
    }

    @NotNull
    public static NBTType getById(int id) {
        if(id < 0 || types.length <= id) {
            throw new RuntimeException("NBTType id is incorrect");
        }
        return types[id];
    }
}
