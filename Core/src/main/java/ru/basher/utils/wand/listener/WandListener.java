package ru.basher.utils.wand.listener;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.basher.utils.location.V3;
import ru.basher.utils.wand.rWandService;

@AllArgsConstructor
public class WandListener implements Listener {

	private final rWandService service;

	public void register(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void unregister() {
		PlayerInteractEvent.getHandlerList().unregister(this);
		PlayerQuitEvent.getHandlerList().unregister(this);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if(event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		ItemStack itemStack = event.getItem();
		if(itemStack == null || itemStack.getType().isAir()) return;
		if(!service.isWandItem(itemStack)) return;

		event.setCancelled(true);

		Block block = event.getClickedBlock();
		if(block == null) return;
		Player player = event.getPlayer();
		Location location = block.getLocation();

		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			service.setWand(player, null, new V3(location), location.getWorld().getName());
			player.sendMessage("§eТочка 2 установлена на x: " + location.getBlockX() + " y: " + location.getBlockY() + " z: " + location.getBlockZ());
		} else {
			service.setWand(player, new V3(location), null, location.getWorld().getName());
            player.sendMessage("§eТочка 1 установлена на x: " + location.getBlockX() + " y: " + location.getBlockY() + " z: " + location.getBlockZ());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		service.removeSelection(event.getPlayer());
	}
	
}
