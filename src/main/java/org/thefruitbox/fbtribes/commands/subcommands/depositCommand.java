package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.InventoryManager;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class depositCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	TribeManager tribeManager = new TribeManager();
	InventoryManager inventoryManager = new InventoryManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "deposit";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes deposit sponges into tribe bank";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes deposit [amount]/all";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
		
		if(!playerTribe.equals("none")) {
			if(args.length == 2) {
				String amount = args[1];
				if(amount.equalsIgnoreCase("all")) {
					
					int spongeCount = 0;
					for(ItemStack item : p.getInventory().getContents()) {
						if(item != null) {
							if(item.getType() == Material.SPONGE) {
								spongeCount += item.getAmount();
							}
						}
					}
					
					InventoryManager.removeItems(p.getInventory(), Material.SPONGE, spongeCount);
					tribeManager.addToVault(playerTribe, spongeCount, p);
					p.sendMessage(ChatColor.GREEN + "You have deposited " + spongeCount + " sponges into the tribe bank!");

					tribeManager.generateScorePerTribe(playerTribe);
					
				} else if(isNumeric(amount)) {
					int intAmount = Integer.parseInt(amount);
					ItemStack sponges = new ItemStack(Material.SPONGE, intAmount);
					if(p.getInventory().containsAtLeast(sponges, intAmount)) {
						InventoryManager.removeItems(p.getInventory(), Material.SPONGE, intAmount);
							
						tribeManager.addToVault(playerTribe, intAmount, p);
						p.sendMessage(ChatColor.GREEN + "You have deposited " + intAmount + " sponges into the tribe bank!");
						tribeManager.generateScorePerTribe(playerTribe);
					} else {
						p.sendMessage(ChatColor.RED + "You do not have " + intAmount + " sponges in your inventory!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Invalid amount!");
				}	
			} else {
				p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
			}
		} else {
			p.sendMessage(ChatColor.RED + "You are not in a tribe!");
		}
	}
	
	boolean isNumeric(String input) {
		if(input == null) {
			return false;
		}
		
		try {
			double d = Double.parseDouble(input);
		} catch (NumberFormatException nfe){
			return false;
		}
		return true;
	}
}
