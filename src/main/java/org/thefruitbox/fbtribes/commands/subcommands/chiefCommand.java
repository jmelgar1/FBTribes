package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;

public class chiefCommand extends SubCommand {
	
	//Main instance
	private final ChatUtilities cu = new ChatUtilities();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "chief";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes chief help guide";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes chief";
	}

	@Override
	public void perform(Player p, String[] args) {
		p.sendMessage(ChatColor.GRAY + "----- " + cu.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "These commands are restricted to Chiefs only!");
		p.sendMessage(cu.lightGreen + "1. " + cu.lighterGreen + "/tribes promote" +
				ChatColor.GRAY + " (Promote a player to tribe elder)");
		p.sendMessage(cu.lightGreen + "2. " + cu.lighterGreen + "/tribes ownership" +
				ChatColor.GRAY + " (Transfer tribe ownership to another player)");
		p.sendMessage(cu.lightGreen + "3. " + cu.lighterGreen + "/tribes demote" +
				ChatColor.GRAY + " (Demote an elder rank player)");
		p.sendMessage(cu.lightGreen + "4. " + cu.lighterGreen + "/tribes delete" +
				ChatColor.GRAY + " (Delete a tribe)");
		p.sendMessage(cu.lightGreen + "5. " + cu.lighterGreen + "/tribes rename" +
				ChatColor.GRAY + " (Rename your tribe)");
		
//		tribes create [name]
//				tribes delete [name]
//				tribes add [name]
//				tribes kick [name]
//				tribes promote [name]
//				tribes demote [name]
//				tribes ownership [name]
//				tribes warp
//				tribes setwarp
//				tribes list
//				tribes info
//				tribes deposit
	}

}
