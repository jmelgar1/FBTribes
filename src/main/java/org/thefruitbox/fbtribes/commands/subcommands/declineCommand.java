package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class declineCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "decline";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Decline a tribe invite";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes decline [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		inviteCommand invCmd = inviteCommand.getInstance();
		
		if (args.length == 2) {
			String otherTribe = args[1];
			
			if(invCmd != null) {
				if(invCmd.CheckForActiveInvite(invCmd.TribeInvites, otherTribe.toLowerCase(), p) == true) {
					p.sendMessage(ChatColor.RED + "You have declined the invitation to join " + otherTribe);
					invCmd.TribeInvites.remove(otherTribe, p);
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
}
