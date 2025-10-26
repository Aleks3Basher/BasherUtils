package ru.basher.utils.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.ItemUtil;
import ru.basher.utils.text.TextUtil;

@Getter
@AllArgsConstructor
public class ItemStackGenerator {

    private String name;
    private List<String> lore = new ArrayList<>();
    private CustomMaterial material;
    private int amount;
    private Map<Enchantment, Integer> enchants = new HashMap<>();
    private List<ItemFlag> itemFlags = new ArrayList<>();
    private List<ItemAttribute> attributes = new ArrayList<>();
    private int customModelData;
    private int damage;

    public ItemStackGenerator(@NotNull ConfigurationSection section) {
        name = TextUtil.toColor(section.getString("name", ""));

        for(String s : section.getStringList("lore")) {
            if (s != null) {
                lore.add(TextUtil.toColor(s));
            }
        }

        material = CustomMaterial.of(section);
        amount = section.getInt("amount", 1);

        for(String s : section.getStringList("enchants")) {
            if (s != null) {
                String[] arr = s.split(";");
                if (arr.length == 2) {
                    Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(arr[0].toLowerCase()));
                    if (enchantment != null) {
                        int level = Integer.parseInt(arr[1]);
                        enchants.put(enchantment, level);
                    }
                }
            }
        }

        for(String s : section.getStringList("itemFlags")) {
            if (s != null) {
                ItemFlag itemFlag = ItemFlag.valueOf(s.toUpperCase());
                itemFlags.add(itemFlag);
            }
        }

        for(String s : section.getStringList("attributes")) {
            if (s != null) {
                String[] arr = s.split(";");
                if (arr.length == 2) {
                    Attribute attribute = Attribute.valueOf(arr[0]);
                    boolean isPercent;
                    double value;
                    if (arr[1].endsWith("%")) {
                        value = Double.parseDouble(arr[1].replace("%", ""));
                        isPercent = true;
                    } else {
                        value = Double.parseDouble(arr[1]);
                        isPercent = false;
                    }

                    attributes.add(new ItemAttribute(attribute, value, isPercent));
                }
            }
        }

        customModelData = section.getInt("customModelData", 0);
        damage = section.getInt("damage", 0);
    }

    public @NotNull ItemStack generate(@NotNull Replace... replaces) {
        return generateArr(replaces);
    }

    public @NotNull ItemStack generateArr(@NotNull Replace[] replaces) {
        ItemStack itemStack = material.getItemStack();
        itemStack.setAmount(amount);
        ItemMeta meta = itemStack.getItemMeta();
        String name = this.name;
        List<String> lore = new ArrayList<>(this.lore);
        if (replaces != null) {
            for(Replace replace : replaces) {
                name = replace.apply(name);
                replace.apply(lore);
            }
        }

        meta.setDisplayName(name);
        meta.setLore(lore);

        for(Entry<Enchantment, Integer> entry : this.enchants.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        for(ItemFlag itemFlag : this.itemFlags) {
            meta.addItemFlags(itemFlag);
        }

        for(ItemAttribute attribute : this.attributes) {
            AttributeModifier modifier = attribute.isPercent()
                    ? new AttributeModifier(UUID.randomUUID(), attribute.getAttribute().name(), attribute.getValue(), Operation.ADD_SCALAR, EquipmentSlot.OFF_HAND)
                    : new AttributeModifier(UUID.randomUUID(), attribute.getAttribute().name(), attribute.getValue(), Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
            meta.addAttributeModifier(attribute.getAttribute(), modifier);
        }

        meta.setCustomModelData(this.customModelData);
        itemStack.setItemMeta(meta);
        if (this.damage != 0) {
            ItemUtil.setDamageItem(itemStack, this.damage);
        }

        return itemStack;
    }

    public void combine(@NotNull ItemStackGenerator child) {
        if (this.name.isEmpty()) {
            this.name = child.getName();
        } else {
            this.name = this.name.replace("{name}", child.getName());
        }

        if (this.lore.isEmpty()) {
            this.lore.addAll(child.getLore());
        } else {
            new Replace("{lore}", child.getLore()).apply(this.lore);
        }

        this.material = child.getMaterial();

        this.amount = child.getAmount();

        for(Entry<Enchantment, Integer> entry : child.getEnchants().entrySet()) {
            this.enchants.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        for(ItemFlag itemFlag : child.getItemFlags()) {
            if (!this.itemFlags.contains(itemFlag)) {
                this.itemFlags.add(itemFlag);
            }
        }

        for(ItemAttribute attribute : child.getAttributes()) {
            if (!this.attributes.contains(attribute)) {
                this.attributes.add(attribute);
            }
        }

        if (child.getCustomModelData() != 0) {
            this.customModelData = child.getCustomModelData();
        }

        if (child.getDamage() != 0) {
            this.damage = child.getDamage();
        }
    }

    @NotNull
    public ItemStackGenerator copy() {
        return new ItemStackGenerator(
                this.name,
                new ArrayList<>(this.lore),
                this.material,
                this.amount,
                new HashMap<>(this.enchants),
                new ArrayList<>(this.itemFlags),
                new ArrayList<>(this.attributes),
                this.customModelData,
                this.damage
        );
    }
}
