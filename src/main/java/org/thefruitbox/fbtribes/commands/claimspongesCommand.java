package org.thefruitbox.fbtribes.commands;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;

import net.md_5.bungee.api.ChatColor;

public class claimspongesCommand implements CommandExecutor {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	String cmd1 = "claimsponges";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            	if(cmd.getName().equalsIgnoreCase(cmd1)) {
            		
            		ConfigurationSection rewards = mainClass.getRewards();
            		String playerUUID = p.getUniqueId().toString();
            		
            		if(rewards.getConfigurationSection(playerUUID) != null) {
            			
            			ConfigurationSection playerRewards = rewards.getConfigurationSection(playerUUID);
            			int unclaimedAmount = playerRewards.getInt("unclaimed");
            			
            			if(unclaimedAmount != 0) {
            				ItemStack sponges = new ItemStack(Material.SPONGE, unclaimedAmount);
            		    	Map<Integer, ItemStack> spongeStack = p.getInventory().addItem(sponges);
            		    	if(!spongeStack.isEmpty()) {
	            		    	for(Map.Entry<Integer, ItemStack> entry : spongeStack.entrySet()) {
	            		    		ItemStack stack = entry.getValue();
	            		    		if(stack.getAmount() > 0) {
	            		    			int amountClaimed = unclaimedAmount - stack.getAmount();
	            		    			p.sendMessage(mainClass.spongeColor + "You have claimed " + amountClaimed + " sponges!");
	            			    		p.sendMessage(ChatColor.RED + "Inventory full. Could not claim " + stack.getAmount() + " sponges.");
	            			    		playerRewards.set("unclaimed", stack.getAmount());
	            			    		break;
	            		    		}
	            		    	}    	
            		    	} else {
            		    		playerRewards.set("unclaimed", 0);
            		    		p.sendMessage(mainClass.spongeColor + "You have claimed " + unclaimedAmount + " sponges!");
            		    	}
            		    	
            		    	mainClass.saveRewardsFile();
            			} else {
            				p.sendMessage(ChatColor.RED + "You do not have any unclaimed sponges!");
            			}
            		}
            }
        }
        return true;
    } 
}
