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

public class BreakAncientDebri extends EventManager implements Listener {
	
	CoreProtectAPI api = getCoreProtect();
	
	List<Material> blocks = Arrays.asList(Material.ANCIENT_DEBRIS);
	
	@EventHandler
	public void breakAncientDebri(BlockBreakEvent e) {
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
				int amountDropped = spongeManager.getRandomNumber(5, 8);
				b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.SPONGE, amountDropped));
				p.sendMessage(mainClass.spongeColor + "You earned " + amountDropped + " sponges from the ancient debris!");
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
