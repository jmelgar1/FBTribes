package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class upgradeCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();
	
	//Main instance
	private Main mainClass = Main.getInstance();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "upgrade";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "FBTribes upgrade command";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes upgrade";
	}

	@Override
	public void perform(Player p, String[] args) {
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
		int upgradeCost = tribeSection.getInt("requiredSponges");
		int tribeVault = tribeSection.getInt("vault");
		
		if(!playerTribe.equals("none")) {
			if(args.length == 1) {
				if(tribeManager.CheckForChief(playerTribe, p)) {
					if(tribeManager.getLevel(playerTribe) != 10) {
						tribeManager.removeFromVault(playerTribe, upgradeCost, p);
						tribeManager.upgradeTribe(playerTribe, tribeVault, p);
					} else {
						p.sendMessage(ChatColor.RED + "Your tribe is max level!");
					}
				} else {			
					p.sendMessage(ChatColor.RED + "Only tribe chiefs can upgrade the tribe!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
			}
		} else {
			p.sendMessage(ChatColor.RED + "You are not in a tribe!");
		}
	}
}
