package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.List;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class leaveCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "leave";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Leave a tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes leave [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			String leftTribe = args[1];
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(!playerTribe.equals("none")) {
				if(tribeManager.CheckForChief(playerTribe, p) == false) {
					if(leftTribe.equalsIgnoreCase(playerTribe)) {
						
						List<String> members = tribeManager.getTribeMembers(playerTribe);
						members.remove(p.getUniqueId().toString());
						tribeManager.setTribeMembers(playerTribe, members);

						tribeManager.generateScorePerTribe(playerTribe);
						
						if(tribeManager.CheckForElder(playerTribe, p) == true) {
							tribeManager.removeElder(playerTribe);
						}
						
						p.sendMessage(ChatColor.GREEN + "You have left " + playerTribe + "!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Please use /tribe delete");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes leave [tribe]");
		}
	}
}
