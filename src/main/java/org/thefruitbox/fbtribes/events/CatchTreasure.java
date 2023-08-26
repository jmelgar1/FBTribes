package org.thefruitbox.fbtribes.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.managers.EventManager;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;

public class CatchTreasure extends EventManager implements Listener {

	private final ChatUtilities cu = new ChatUtilities();
	
	@EventHandler
	public void catchFish(PlayerFishEvent event) {
		
		Player p = event.getPlayer();
		
		//check for block type (aka. emerald_ore, diamond ore, etc)
		if(event.getState() == State.CAUGHT_FISH) {
			
			Item item = (Item) event.getCaught();
				
			//if player catches treasure
			if (item.getItemStack().getType() == Material.ENCHANTED_BOOK ||
					  item.getItemStack().getType() == Material.FISHING_ROD ||
					  item.getItemStack().getType() == Material.NAME_TAG ||
					  item.getItemStack().getType() == Material.NAUTILUS_SHELL ||
					  item.getItemStack().getType() == Material.SADDLE) {
				
				int amountDropped = spongeManager.getRandomNumber(2, 4);
				p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
				p.sendMessage(cu.spongeColor + "You earned " + amountDropped + " sponges from catching treasure!");
			} 
		}
	}
}
