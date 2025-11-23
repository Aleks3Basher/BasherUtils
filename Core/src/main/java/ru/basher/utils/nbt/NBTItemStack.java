package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.basher.utils.UtilsCore;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
public class NBTItemStack implements NBTBase {

    private final NBTType type = NBTType.ITEM_STACK;
    private ItemStack value;

    public NBTItemStack(ItemStack value) {
        this.value = value;
    }

    public NBTItemStack(DataInputStream in) throws Exception {
        read(in);
    }

    @Override
    public @NotNull NBTType getType() {
        return type;
    }

    @Override
    public void write(@NotNull DataOutputStream out) throws Exception {
        out.writeByte(type.getId());
        String serialized = UtilsCore.getVersion().getItemUtil().serializeItemStack(value);
        out.writeUTF(serialized);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        String str = in.readUTF();
        value = UtilsCore.getVersion().getItemUtil().deserializeItemStack(str);
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTItemStack(value.clone());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
