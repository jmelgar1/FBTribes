package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class helpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "OVTribes help guide";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes help";
	}

	@Override
	public void perform(Player p, String[] args) {
		p.sendMessage(ChatColor.GRAY + "----- " + mainClass.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "See /tribes elder & /tribes chief to see other commands!");
		p.sendMessage(mainClass.lightGreen + "1. " + mainClass.lighterGreen + "/tribes create" +
		ChatColor.GRAY + " (Create a new tribe)");
		p.sendMessage(mainClass.lightGreen + "2. " + mainClass.lighterGreen + "/tribes compound" +
				ChatColor.GRAY + " (Warp to your tribe's compound)");
		p.sendMessage(mainClass.lightGreen + "3. " + mainClass.lighterGreen + "/tribes info" +
				ChatColor.GRAY + " (See tribe info)");
		p.sendMessage(mainClass.lightGreen + "4. " + mainClass.lighterGreen + "/tribes who" +
				ChatColor.GRAY + " (Get player's tribe info)");
		p.sendMessage(mainClass.lightGreen + "5. " + mainClass.lighterGreen + "/tribes list" +
				ChatColor.GRAY + " (List tribes)");
		p.sendMessage(mainClass.lightGreen + "6. " + mainClass.lighterGreen + "/tribes deposit" +
				ChatColor.GRAY + " (Deposit sponges to tribe bank)");
		p.sendMessage(mainClass.lightGreen + "7. " + mainClass.lighterGreen + "/tribes upgrade" +
				ChatColor.GRAY + " (Upgrade your tribe)");
		p.sendMessage(mainClass.lightGreen + "8. " + mainClass.lighterGreen + "/tribes accept" +
				ChatColor.GRAY + " (Accept a tribe invite request)");
		p.sendMessage(mainClass.lightGreen + "9. " + mainClass.lighterGreen + "/tribes decline" +
				ChatColor.GRAY + " (Decline a tribe invite request)");
		p.sendMessage(mainClass.lightGreen + "10. " + mainClass.lighterGreen + "/tribes leave" +
				ChatColor.GRAY + " (Leave a tribe)");
		
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
