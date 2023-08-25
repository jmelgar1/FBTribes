package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class ownershipCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ownership";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Transfer chief ownership";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes ownership [player]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			Player newChief = Bukkit.getServer().getPlayer(args[1]);
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(newChief != null) {
				String newChiefTribe = tribeManager.getPlayerTribe(newChief);
				if(tribeManager.CheckForChief(playerTribe, p) == true) {
					if(tribeManager.CheckSameTribe(playerTribe, newChiefTribe)) {
						if(!newChief.getName().equals(p.getName())) {
							tribeManager.setChief(playerTribe, newChief);
							tribeManager.sendMessageToMembers(playerTribe, ChatColor.GREEN + p.getName()
							+ " has tranferred tribe ownership to "
							+ newChief.getName() + "!");
							if(tribeManager.CheckForElder(playerTribe, newChief) == true) {
								tribeManager.removeElder(playerTribe);
								tribeManager.setElder(playerTribe, p);
							}
						} else {
							p.sendMessage(ChatColor.RED + "You are already chief!");
						}
					} else {
						p.sendMessage(ChatColor.RED + newChief.getName() + " is not in your tribe!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You must be a chief to use this command!");
				}
			} else {
				p.sendMessage(ChatColor.RED + args[1] + " not found!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
