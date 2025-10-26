package ru.basher.utils.wand;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.ItemUtil;
import ru.basher.utils.location.V3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WandServiceImpl {

	private static final String WAND_ITEM_TAG = "butils_wand_item";

	private final Plugin plugin;
	private final WandListener wandListener;

	private final Map<UUID, Wand> activeWands = new HashMap<>();

	public WandServiceImpl(Plugin plugin) {
		this.plugin = plugin;
		wandListener = new WandListener(this);
	}

	public void enable() {
		wandListener.register(plugin);
		WandService.setInstance(this);
	}
	public void disable() {
		wandListener.unregister();
	}

	public boolean isWandItem(@NotNull ItemStack itemStack) {
		return ItemUtil.hasItemNBT(itemStack, WAND_ITEM_TAG);
	}
	
	public void setWand(@NotNull Player player, @Nullable V3 firstPos, @Nullable V3 secondPos, @NotNull String worldName) {
		if(firstPos == null && secondPos == null) return;
		Wand wand = activeWands.get(player.getUniqueId());
		if(wand != null && !wand.getWorldName().equalsIgnoreCase(worldName)) {
			wand = null;
		}
		if(wand == null) {
			wand = new Wand(firstPos, secondPos, worldName);
		} else {
			if(firstPos != null) wand.setFirstPos(firstPos);
			if(secondPos != null) wand.setSecondPos(secondPos);
		}
		activeWands.put(player.getUniqueId(), wand);
	}

	public void removeSelection(@NotNull Player player) {
		activeWands.remove(player.getUniqueId());
	}

	@Nullable
	public SelectionBox getSelection(@NotNull Player player) {
		Wand wand = activeWands.get(player.getUniqueId());
		if(wand == null || wand.getFirstPos() == null || wand.getSecondPos() == null) return null;
		return new SelectionBox(wand.getWorldName(), V3.min(wand.getFirstPos(), wand.getSecondPos()),
				V3.max(wand.getFirstPos(), wand.getSecondPos()));
	}

	public void giveWandItem(@NotNull Player player) {
		ItemStack item = new ItemStack(Material.IRON_AXE);
		item.editMeta(meta -> meta.setDisplayName("§eВыделитель"));
		ItemUtil.addItemNBT(item, WAND_ITEM_TAG, "");

		player.getInventory().addItem(item);
	}
	
}
