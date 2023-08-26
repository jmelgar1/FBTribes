package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;

public class helpCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	private final ChatUtilities cu = new ChatUtilities();

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
		p.sendMessage(ChatColor.GRAY + "----- " + cu.tribesColor + "TRIBES" + ChatColor.GRAY + " -----");
		p.sendMessage(ChatColor.DARK_RED + "See /tribes elder & /tribes chief to see other commands!");
		p.sendMessage(cu.lightGreen + "1. " + cu.lighterGreen + "/tribes create" +
		ChatColor.GRAY + " (Create a new tribe)");
		p.sendMessage(cu.lightGreen + "2. " + cu.lighterGreen + "/tribes compound" +
				ChatColor.GRAY + " (Warp to your tribe's compound)");
		p.sendMessage(cu.lightGreen + "3. " + cu.lighterGreen + "/tribes info" +
				ChatColor.GRAY + " (See tribe info)");
		p.sendMessage(cu.lightGreen + "4. " + cu.lighterGreen + "/tribes who" +
				ChatColor.GRAY + " (Get player's tribe info)");
		p.sendMessage(cu.lightGreen + "5. " + cu.lighterGreen + "/tribes list" +
				ChatColor.GRAY + " (List tribes)");
		p.sendMessage(cu.lightGreen + "6. " + cu.lighterGreen + "/tribes deposit" +
				ChatColor.GRAY + " (Deposit sponges to tribe bank)");
		p.sendMessage(cu.lightGreen + "7. " + cu.lighterGreen + "/tribes upgrade" +
				ChatColor.GRAY + " (Upgrade your tribe)");
		p.sendMessage(cu.lightGreen + "8. " + cu.lighterGreen + "/tribes accept" +
				ChatColor.GRAY + " (Accept a tribe invite request)");
		p.sendMessage(cu.lightGreen + "9. " + cu.lighterGreen + "/tribes decline" +
				ChatColor.GRAY + " (Decline a tribe invite request)");
		p.sendMessage(cu.lightGreen + "10. " + cu.lighterGreen + "/tribes leave" +
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
