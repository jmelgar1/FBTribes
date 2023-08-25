package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class demoteCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "demote";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Demote a player from elder status";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes demote [player]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			Player demotedPlayer = Bukkit.getServer().getPlayer(args[1]);
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(demotedPlayer != null) {
				String demotedPlayerTribe = tribeManager.getPlayerTribe(demotedPlayer);
				if(tribeManager.CheckForChief(playerTribe, p) == true) {
					if(playerTribe.equals(demotedPlayerTribe)) {
						if(!demotedPlayer.getName().equals(p.getName())) {
							if(tribeManager.CheckForElder(demotedPlayerTribe, demotedPlayer) == true) {
								tribeManager.removeElder(playerTribe);
								tribeManager.sendMessageToMembers(playerTribe, ChatColor.RED + demotedPlayer.getName() + " has been demoted from elder!");
							} else {
								p.sendMessage(ChatColor.RED + demotedPlayer.getName() + " is not an elder!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "You can not promote yourself!");
						}
					} else {
						p.sendMessage(ChatColor.RED + demotedPlayer.getName() + " is not in your tribe!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Only chiefs can demote players!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Player " + args[1] + " not found!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}

