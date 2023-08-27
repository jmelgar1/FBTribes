package org.thefruitbox.fbtribes.commands.subcommands;

import com.google.gson.JsonObject;
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
		
		JsonObject tribesJson = mainClass.getTribesJson();
		
		if(args.length == 2) {
			
			OfflinePlayer pl = Bukkit.getServer().getOfflinePlayer(args[1]);

			String playerTribe = tribeManager.getPlayerTribe(pl);
			JsonObject tribeSection = tribesJson.getAsJsonObject(playerTribe);
			tribeManager.getTribeInfo(tribesJson, tribeSection, playerTribe, p, true);
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
