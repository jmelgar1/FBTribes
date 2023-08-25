package org.thefruitbox.fbtribes.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.managers.EventManager;

public class KillEvent extends EventManager implements Listener {
	
	@EventHandler
	public void killMob(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		
		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
		
			int amountDropped = spongeManager.getRandomNumber(0, 51);
			
			if(entity.getType() == EntityType.ENDER_DRAGON) {
				amountDropped = spongeManager.getRandomNumber(8, 13);
				p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from killing an enderdragon!");
				entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
			} else if(entity.getType() == EntityType.WITHER){
				amountDropped = spongeManager.getRandomNumber(3, 8);
				p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from killing a wither!");
				entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
			}
			
			if(entity.getType() != EntityType.PLAYER ||
			   entity.getType() != EntityType.ARROW ||
			   entity.getType() != EntityType.ARMOR_STAND ||
			   entity.getType() != EntityType.BOAT ||
			   entity.getType() != EntityType.CHEST_BOAT ||
			   entity.getType() != EntityType.ENDER_CRYSTAL ||
			   entity.getType() != EntityType.ENDER_PEARL ||
			   entity.getType() != EntityType.GLOW_ITEM_FRAME ||
			   entity.getType() != EntityType.ITEM_FRAME ||
			   entity.getType() != EntityType.MINECART ||
			   entity.getType() != EntityType.MINECART_CHEST ||
			   entity.getType() != EntityType.MINECART_FURNACE ||
			   entity.getType() != EntityType.MINECART_HOPPER ||
			   entity.getType() != EntityType.MINECART_TNT ||
			   entity.getType() != EntityType.PAINTING ||
			   entity.getType() != EntityType.SHULKER_BULLET ||
			   entity.getType() != EntityType.WITHER_SKULL ||
			   entity.getType() != EntityType.WITHER ||
			   entity.getType() != EntityType.ENDER_DRAGON) {
				amountDropped = spongeManager.getRandomNumber(0, 1201);
				if(amountDropped == 1 || amountDropped == 2 || amountDropped == 3) {
					entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
					p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from killing " + entity.getType().getName() + "!");	
				}
			}
		}
	}
}
