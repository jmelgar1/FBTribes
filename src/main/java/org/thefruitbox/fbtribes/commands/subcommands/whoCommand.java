package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class whoCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "who";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "See what tribe a player is in";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes who [player]";
	}

	@Override
	public void perform(Player p, String[] args) {
		
		FileConfiguration tribesFile = mainClass.getTribes();
		
		if(args.length == 2) {
			
			OfflinePlayer pl = Bukkit.getServer().getOfflinePlayer(args[1]);
			
			if(pl != null) {
				String playerTribe = tribeManager.getOfflinePlayerTribe(pl);
				ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
				tribeManager.getTribeInfo(tribesFile, tribeSection, playerTribe, p, true);
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
