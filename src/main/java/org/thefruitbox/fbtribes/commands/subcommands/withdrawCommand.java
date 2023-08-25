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

public class withdrawCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	TribeManager tribeManager = new TribeManager();
	InventoryManager inventoryManager = new InventoryManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "withdraw";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "FBTribes withdraw sponges into tribe bank";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes withdraw [amount]/all";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
		
		int vault = 0;
		
		if(!playerTribe.equals("none")) {
			if(args.length == 2) {
				String amount = args[1];
				if(amount.equalsIgnoreCase("all")) {
					
					vault = tribeManager.getVault(playerTribe);

	                // Give the player as many sponges as can fit in their inventory. If it doesn't fit,
	                // do not take it out of the vault.
	                int maxStackSize = Material.SPONGE.getMaxStackSize();
	                int maxAmount = p.getInventory().firstEmpty() == -1 ? 0 : (p.getInventory().firstEmpty() + 1) * maxStackSize;
	                int amountToGive = Math.min(maxAmount, vault);
	                ItemStack sponges = new ItemStack(Material.SPONGE, amountToGive);

	                if (amountToGive > 0) {
	                    p.getInventory().addItem(sponges);
	                    tribeManager.removeFromVault(playerTribe, amountToGive, p);
	                    p.sendMessage(ChatColor.GREEN + "You received " + amountToGive + " sponges from the tribe vault.");
	                } else {
	                    p.sendMessage(ChatColor.RED + "Your inventory is full. Could not retrieve any sponges.");
	                }
					
					
				} else if(isNumeric(amount) == true) {
					   int intAmount = Integer.parseInt(amount);
		                ItemStack sponges = new ItemStack(Material.SPONGE, intAmount);

		                if(intAmount <= tribeManager.getVault(playerTribe)) {
		                int maxStackSize = Material.SPONGE.getMaxStackSize();
		                int maxAmount = p.getInventory().firstEmpty() == -1 ? 0 : (p.getInventory().firstEmpty() + 1) * maxStackSize;
		                int amountToGive = Math.min(maxAmount, intAmount);

		                if (amountToGive > 0) {
		                    sponges.setAmount(amountToGive);
		                    tribeManager.removeFromVault(playerTribe, amountToGive, p);
		                    p.getInventory().addItem(sponges);
		                    p.sendMessage(ChatColor.GREEN + "You received " + amountToGive + " sponges.");
		                } else {
		                    p.sendMessage(ChatColor.RED + "Your inventory is full. Could not give any sponges.");
		                }
		            } else {
		            	p.sendMessage(ChatColor.RED + "You do not have enough sponges in the tribe vault!");
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
