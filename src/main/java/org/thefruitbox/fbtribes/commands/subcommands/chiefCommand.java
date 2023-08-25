package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class chiefCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

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
		p.sendMessage(ChatColor.GRAY + "----- " + mainClass.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "These commands are restricted to Chiefs only!");
		p.sendMessage(mainClass.lightGreen + "1. " + mainClass.lighterGreen + "/tribes promote" +
				ChatColor.GRAY + " (Promote a player to tribe elder)");
		p.sendMessage(mainClass.lightGreen + "2. " + mainClass.lighterGreen + "/tribes ownership" +
				ChatColor.GRAY + " (Transfer tribe ownership to another player)");
		p.sendMessage(mainClass.lightGreen + "3. " + mainClass.lighterGreen + "/tribes demote" +
				ChatColor.GRAY + " (Demote an elder rank player)");
		p.sendMessage(mainClass.lightGreen + "4. " + mainClass.lighterGreen + "/tribes delete" +
				ChatColor.GRAY + " (Delete a tribe)");
		p.sendMessage(mainClass.lightGreen + "5. " + mainClass.lighterGreen + "/tribes rename" +
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
