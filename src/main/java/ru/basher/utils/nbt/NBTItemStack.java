package ru.basher.utils.nbt;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asNMSCopy(value);
        net.minecraft.server.v1_16_R3.NBTTagCompound nmsTagComp = nmsItemStack.save(new net.minecraft.server.v1_16_R3.NBTTagCompound());
        String serialize = new String(Base64.getEncoder().encode(nmsTagComp.toString().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        out.writeUTF(serialize);
    }

    @Override
    public void read(@NotNull DataInputStream in) throws Exception {
        String str = in.readUTF();

        String decoded = new String(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        net.minecraft.server.v1_16_R3.NBTTagCompound nmsTagComp = net.minecraft.server.v1_16_R3.MojangsonParser.parse(decoded);
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = net.minecraft.server.v1_16_R3.ItemStack.a(nmsTagComp);

        value = org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack.asBukkitCopy(nmsItemStack);
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
