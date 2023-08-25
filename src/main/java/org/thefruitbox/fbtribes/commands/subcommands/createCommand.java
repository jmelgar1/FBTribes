package org.thefruitbox.fbtribes.commands.subcommands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class createCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Create a tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes create [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			String tribeName = args[1];
			if(tribeName.matches("[a-zA-Z]+") == true) {
				if(tribeName.length() <= 16 && tribeName.length() >= 4) {
				FileConfiguration tribesFile = mainClass.getTribes();
				
				String playerUUID = p.getUniqueId().toString();
				
				boolean inTribe = true;
				int count = 0;
				
				for(String tribe : tribesFile.getKeys(false)) {
					count++;
					ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
					if(!tribeSection.getStringList("members").contains(playerUUID) && !tribeSection.get("chief").equals(playerUUID)) {
						inTribe = false;
					}
				}
				
				boolean tribeExists = false;
				for(String tribe : tribesFile.getKeys(false)) {
					if(tribe.equalsIgnoreCase(tribeName)) {
						tribeExists = true;
					}
				}
				
				if(inTribe == false || count == 0) {
					if(tribesFile.getConfigurationSection(tribeName) == null && tribeExists == false) {
						ConfigurationSection newTribe = tribesFile.createSection(tribeName.toLowerCase());
						newTribe.set("level", 1);
						newTribe.set("chief", playerUUID);
						newTribe.set("elder", "");
						newTribe.set("vault", 0);
						newTribe.set("showname", tribeName);
						newTribe.set("currentWarps", 0);
						newTribe.set("maxWarps", 1);
						newTribe.set("maxPlayers", 3);
						newTribe.set("requiredSponges", 50);
						newTribe.set("totalSponges", 0);
						newTribe.set("economyScore", 0);
						//newTribe.createSection("warps");
						newTribe.createSection("tribalgameswins");
						ConfigurationSection tribalGamesSection = newTribe.getConfigurationSection("tribalgameswins");
						tribalGamesSection.set("ctf", 0);
						tribalGamesSection.set("koth", 0);
						tribalGamesSection.set("tott", 0);
						tribalGamesSection.set("rating", 0);
						
						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
						newTribe.set("dateCreated", format.format(now).toString());
						
						List<String> tribeMembers = new ArrayList<String>();
						tribeMembers.add(playerUUID);
						newTribe.set("members", tribeMembers);
	 
						Bukkit.broadcastMessage(mainClass.tribesColor + tribeName + " has been created by " + p.getName() + "!");
						for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 0);
						}
						
						mainClass.saveTribesFile();
						
					} else {
						p.sendMessage(ChatColor.RED + "That tribe name is already in use!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are already in a tribe!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Tribe names must be between 4 and 16 characters long!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Tribe names must only contain upper or lower case letters!");
		}
	} else {
		p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
