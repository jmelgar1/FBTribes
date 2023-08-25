package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class compoundCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "compound";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Go to tribe compound";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes compound";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToWarp = mainClass.getPrices().getInt("gotowarp");
		
		if (args.length == 1) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			
			if(!playerTribe.equals("none")) {
				int vault = tribeManager.getVault(playerTribe);
				if(vault >= priceToWarp) {
					//List<String> lowerCaseWarps = new ArrayList<String>();
					
//					for(String warp : warpManager.getWarpList(playerTribe, p)) {
//						lowerCaseWarps.add(warp.toLowerCase());
//					}
					
					if(warpManager.compoundExists(playerTribe)) {
						if(!warpManager.inWarp.contains(p)) {
							warpManager.warpPlayer(playerTribe, p);
						} else {
							p.sendMessage(ChatColor.RED + "Please wait before doing that again!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Tribe compound is not set!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You need at least " + priceToWarp + " sponges in the tribe vault to warp to the compound!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
