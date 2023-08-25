package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class deleteCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	
	@Override
	public String getName() {
		return "delete";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Delete a tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes delete [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			String deletedTribe = args[1];
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(!playerTribe.equals("none")) {
				if(tribeManager.CheckForChief(playerTribe, p) == true) {
					if(deletedTribe.equalsIgnoreCase(playerTribe)) {
					String tribe = tribeManager.getPlayerTribe(p);
					FileConfiguration tribesFile = mainClass.getTribes();
					tribeManager.sendMessageToMembers(tribe, ChatColor.RED.toString() + ChatColor.BOLD + "The tribe has been deleted!");
					tribesFile.set(tribe, null);
					mainClass.saveTribesFile();
					} else {
						p.sendMessage(ChatColor.RED + "You are not in that tribe!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are not a chief!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: /tribes delete [yourtribe]");
		}
	}
}
