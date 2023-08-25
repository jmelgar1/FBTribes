package org.thefruitbox.fbtribes.events;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.thefruitbox.fbtribes.managers.EventManager;

import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;

public class BreakDiamondOrEmeraldOre extends EventManager implements Listener {
	
	CoreProtectAPI api = getCoreProtect();
	
	List<Material> blocks = Arrays.asList(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE);
	
	@EventHandler
	public void breakDiamond(BlockBreakEvent e) {
		Block b = (Block) e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();
		
		//check for block type (aka. emerald_ore, diamond ore, etc)
		if(blocks.contains(material)) {
			
			boolean blockPlaced = false;
			
			b.getMetadata("placed");
			if(!b.getMetadata("placed").isEmpty()) {
				blockPlaced = true;
			}
			
			List<String[]> lookup = api.blockLookup(e.getBlock(), 86400);
			for(String[] result : lookup) {
				ParseResult parseResult = api.parseResult(result);
				if(parseResult.getPlayer() != null) {
					blockPlaced = true;
				}
			}
			
			if(blockPlaced == false) {
				int amountDropped = spongeManager.getRandomNumber(0, 4);
				if(amountDropped > 0) {
					b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
					if(material == Material.DIAMOND_ORE || material == Material.DEEPSLATE_DIAMOND_ORE) {
						p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from the diamond ore!");
					} else {
						p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from the emerald ore!");
					}
				}	
			}		
		}
	}
	
	//set metadata to prevent players from gaining points from non naturally generated blocks
	@EventHandler 
	public void blockPlaced(BlockPlaceEvent e){
		Block b = e.getBlock();
		Material material = b.getType();
		
		if(blocks.contains(material)) {
			b.setMetadata("placed", new FixedMetadataValue(mainClass, "something"));
		}
	}
}
