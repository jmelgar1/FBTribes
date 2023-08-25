package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class elderCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

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
		p.sendMessage(ChatColor.GRAY + "----- " + mainClass.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "These commands are restricted to Elders & Chiefs only!");
		p.sendMessage(mainClass.lightGreen + "1. " + mainClass.lighterGreen + "/tribes invite" +
				ChatColor.GRAY + " (Invite a player to your tribe)");
		p.sendMessage(mainClass.lightGreen + "2. " + mainClass.lighterGreen + "/tribes withdraw" +
				ChatColor.GRAY + " (Withdraw sponges from vault)");
		p.sendMessage(mainClass.lightGreen + "3. " + mainClass.lighterGreen + "/tribes kick" +
				ChatColor.GRAY + " (Kick a player from your tribe)");
		p.sendMessage(mainClass.lightGreen + "4. " + mainClass.lighterGreen + "/tribes setcompound" +
				ChatColor.GRAY + " (Set a tribe compound)");
		p.sendMessage(mainClass.lightGreen + "5. " + mainClass.lighterGreen + "/tribes delcompound" +
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
