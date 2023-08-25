package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class kickCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "kick";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Kick a player from a tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes kick [player]";
	}

	@Override
	public void perform(Player p, String[] args) {
		
		if (args.length == 2) {
			Player kickedPlayer = Bukkit.getServer().getPlayer(args[1]);
			if(kickedPlayer != null) {
				String kickedPlayerTribe = tribeManager.getPlayerTribe(kickedPlayer);
				String playerTribe = tribeManager.getPlayerTribe(p);
				if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
					if(tribeManager.CheckForChief(playerTribe, kickedPlayer) == false) {
						if(playerTribe.equals(kickedPlayerTribe)) {
							List<String> members = tribeManager.getTribeMembers(playerTribe);
							members.remove(kickedPlayer.getUniqueId().toString());
							tribeManager.setTribeMembers(playerTribe, members);
							
							if(tribeManager.CheckForChief(playerTribe, p) == true) {
								if(tribeManager.CheckForElder(playerTribe, kickedPlayer) == true) {
									tribeManager.removeElder(playerTribe);
								}
							}
	
							kickedPlayer.sendMessage(ChatColor.RED + "You have been kicked from " + kickedPlayerTribe + "!");
							tribeManager.sendMessageToMembers(playerTribe, ChatColor.RED + kickedPlayer.getName() + " has been kicked from the tribe!");
						} else {
							p.sendMessage(ChatColor.RED + kickedPlayer.getName() + " is not in your tribe!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "You can not kick the tribe chief!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You must be a chief or an elder to use this command!");
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
