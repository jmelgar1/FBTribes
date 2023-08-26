package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.InventoryManager;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class renameCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "rename";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Rename your tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes rename [new name]";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToRename = mainClass.getPrices().getInt("changename");
		
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		
		if(args.length == 2) {
			
			String databaseName = args[1].toLowerCase();
			String showName = args[1];
			
			if(!playerTribe.equals("none")) {
				if(tribeManager.CheckForChief(playerTribe, p)) {
					if(!playerTribe.equals(databaseName)) {
						int vault = tribeManager.getVault(playerTribe);
						if(vault >= priceToRename) {
							tribeManager.removeFromVault(playerTribe, priceToRename, p);
							copyConfigSection(tribesFile, playerTribe, databaseName, showName);
							
							mainClass.saveTribesFile();
							p.sendMessage(ChatColor.GREEN + "Tribe renamed!");
						} else {
							p.sendMessage(ChatColor.RED + "You need at least " + priceToRename + " sponges to in the tribe vault change the tribe name!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Please choose a different name.");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are not tribe chief!");
				}
				
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe! To view other tribes use /tribes info [tribe]");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyConfigSection(FileConfiguration config, String fromPath, String toPath, String showName){
	    Map<String, Object> vals = config.getConfigurationSection(fromPath).getValues(true);
	    String toDot = toPath.equals("") ? "" : ".";
	    for (String s : vals.keySet()){
	        System.out.println(s);
	        Object val = vals.get(s);
	        if (val instanceof List)
	            val = new ArrayList((List)val);
	        config.set(toPath + toDot + s, val);
	    }
	    
	    config.getConfigurationSection(toPath).set("showname", showName);
	    
	    config.set(fromPath, null);
	}
}
