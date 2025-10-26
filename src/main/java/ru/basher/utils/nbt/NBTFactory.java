package ru.basher.utils.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class NBTFactory {

    private static final Map<NBTType, Function<DataInputStream, NBTBase>> NBT_CREATORS = new EnumMap<>(NBTType.class);

    static {
        NBT_CREATORS.put(NBTType.EMPTY, wrapException(NBTEmpty::new));
        NBT_CREATORS.put(NBTType.BOOLEAN, wrapException(NBTBoolean::new));
        NBT_CREATORS.put(NBTType.BYTE, wrapException(NBTByte::new));
        NBT_CREATORS.put(NBTType.CHAR, wrapException(NBTChar::new));
        NBT_CREATORS.put(NBTType.SHORT, wrapException(NBTShort::new));
        NBT_CREATORS.put(NBTType.INT, wrapException(NBTInt::new));
        NBT_CREATORS.put(NBTType.LONG, wrapException(NBTLong::new));
        NBT_CREATORS.put(NBTType.FLOAT, wrapException(NBTFloat::new));
        NBT_CREATORS.put(NBTType.DOUBLE, wrapException(NBTDouble::new));
        NBT_CREATORS.put(NBTType.STRING, wrapException(NBTString::new));
        NBT_CREATORS.put(NBTType.COMPOUND, wrapException(NBTCompound::new));
        NBT_CREATORS.put(NBTType.UUID, wrapException(NBTUuid::new));
        NBT_CREATORS.put(NBTType.STRING_LIST, wrapException(NBTStringList::new));
    }

    @NotNull
    public static NBTBase createNBT(@NotNull NBTType type, @NotNull DataInputStream in) {
        Function<DataInputStream, NBTBase> creator = NBT_CREATORS.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown nbt type: " + type);
        }
        return creator.apply(in);
    }

    private static <T, R> Function<T, R> wrapException(ThrowingFunction<T, R> func) {
        return t -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }

}
