package ru.basher.utils.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface NBTBase {

    @NotNull NBTType getType();

    void write(@NotNull DataOutputStream out) throws Exception;
    void read(@NotNull DataInputStream in) throws Exception;

    default boolean asBoolean() {
        return ((NBTBoolean) this).getValue();
    }

    default byte asByte() {
        return ((NBTByte) this).getValue();
    }

    default char asChar() {
        return ((NBTChar) this).getValue();
    }

    default @NotNull NBTCompound asCompound() {
        return (NBTCompound) this;
    }

    default double asDouble() {
        return ((NBTDouble) this).getValue();
    }

    default float asFloat() {
        return ((NBTFloat) this).getValue();
    }

    default int asInt() {
        return ((NBTInt) this).getValue();
    }

    default long asLong() {
        return ((NBTLong) this).getValue();
    }

    default short asShort() {
        return ((NBTShort) this).getValue();
    }

    default @NotNull String asString() {
        return ((NBTString) this).getValue();
    }


}
