package org.thefruitbox.fbtribes.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.InventoryManager;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class setCompoundCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	WarpManager warpManager = new WarpManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setcompound";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set the tribe compound";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes setcompound";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToSetCompound = mainClass.getPrices().getInt("setwarp");
		
		if (args.length == 1) {
			String playerTribe = tribeManager.getPlayerTribe(p);
			if(tribeManager.CheckForElder(playerTribe, p) || tribeManager.CheckForChief(playerTribe, p)) {
				if(!warpManager.compoundExists(playerTribe)) {
					int vault = tribeManager.getVault(playerTribe);
					if(vault >= priceToSetCompound) {
						tribeManager.removeFromVault(playerTribe, priceToSetCompound, p);
						warpManager.setCompound(playerTribe, p);
						tribeManager.generateScorePerTribe(playerTribe);
					} else {
						p.sendMessage(ChatColor.RED + "You need at least " + priceToSetCompound + " sponges in the tribe vault to set the tribe compound!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "The tribe's compound is already set!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Only chiefs and elders can set the compound!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
