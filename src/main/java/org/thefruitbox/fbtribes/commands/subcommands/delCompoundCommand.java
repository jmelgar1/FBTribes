package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class delCompoundCommand extends SubCommand {
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "delcompound";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Unset the tribe compound";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes delcompound";
	}

	@Override
	public void perform(Player p, String[] args) {
		if (args.length == 1) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				warpManager.deleteCompound(playerTribe, p);
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set warps!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}

}
