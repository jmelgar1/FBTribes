package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;

public class elderCommand extends SubCommand {
	
	//Main instance
	private final ChatUtilities cu = new ChatUtilities();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "elder";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes Elder help guide";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes elder";
	}

	@Override
	public void perform(Player p, String[] args) {
		p.sendMessage(ChatColor.GRAY + "----- " + cu.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "These commands are restricted to Elders & Chiefs only!");
		p.sendMessage(cu.lightGreen + "1. " + cu.lighterGreen + "/tribes invite" +
				ChatColor.GRAY + " (Invite a player to your tribe)");
		p.sendMessage(cu.lightGreen + "2. " + cu.lighterGreen + "/tribes withdraw" +
				ChatColor.GRAY + " (Withdraw sponges from vault)");
		p.sendMessage(cu.lightGreen + "3. " + cu.lighterGreen + "/tribes kick" +
				ChatColor.GRAY + " (Kick a player from your tribe)");
		p.sendMessage(cu.lightGreen + "4. " + cu.lighterGreen + "/tribes setcompound" +
				ChatColor.GRAY + " (Set a tribe compound)");
		p.sendMessage(cu.lightGreen + "5. " + cu.lighterGreen + "/tribes delcompound" +
				ChatColor.GRAY + " (Delete a tribe compound)");
		
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
