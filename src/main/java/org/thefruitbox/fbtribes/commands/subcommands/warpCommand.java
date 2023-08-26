package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class warpCommand extends SubCommand {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "warp";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Unused command";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes warp";
	}

	@Override
	public void perform(Player p, String[] args) {
		p.sendMessage(ChatColor.RED + "Tribe warps no longer exist. Existing tribe warps have been"
				+ " converted to personal homes. Check your " + "\"" + "/homes" + "\"" + " or " + "\"" + "/tribes compound" + "\"" + " to go to your warps.");
	}

}
