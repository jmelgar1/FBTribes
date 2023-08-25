package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class acceptCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "accept";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Accept a tribe invite";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes accept [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		inviteCommand invCmd = inviteCommand.getInstance();
		
		if (args.length == 2) {
			String otherTribe = args[1];
			
			if(invCmd != null) {
				if(invCmd.CheckForActiveInvite(invCmd.TribeInvites, otherTribe.toLowerCase(), p) == true) {
					List<String> currentMembers = tribeManager.getTribeMembers(otherTribe);
					currentMembers.add(p.getUniqueId().toString());
					tribeManager.setTribeMembers(otherTribe, currentMembers);
					
					
					p.sendMessage(ChatColor.GREEN + "You have joined " + tribeManager.getTribeShowName(otherTribe) + "!");
					tribeManager.sendMessageToMembers(otherTribe, ChatColor.GREEN + p.getName() + " has joined the tribe!");
					
					invCmd.TribeInvites.remove(otherTribe, p);
					mainClass.saveTribesFile();
				} else {
					p.sendMessage(ChatColor.RED + "You do not have an active tribe invite!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You do not have an active tribe invite!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
	
	public boolean CheckForActiveInvite(Map<String, Player> TribeInvites, String playerTribe, Player invitee) {
		for(Map.Entry<String, Player> entry : TribeInvites.entrySet()) {
			String tribe = entry.getKey();
			Player player = entry.getValue();
			
			if(tribe.equals(playerTribe) && player.equals(invitee)) {
				return true;
			}
		}
		return false;
	}
}
