package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.InventoryManager;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

import javax.json.JsonValue;

public class renameCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "rename";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Rename your tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes rename [new name]";
	}

	@Override
	public void perform(Player p, String[] args) {
		int priceToRename = mainClass.getPrices().getInt("changename");
		
		String playerTribe = tribeManager.getPlayerTribe(p);
		JsonObject tribesJson = mainClass.getTribesJson();
		
		if(args.length == 2) {
			
			String databaseName = args[1].toLowerCase();
			String showName = args[1];
			
			if(!playerTribe.equals("none")) {
				if(tribeManager.CheckForChief(playerTribe, p)) {
					if(!playerTribe.equals(databaseName)) {
						int vault = tribeManager.getVault(playerTribe);
						if(vault >= priceToRename) {
							tribeManager.removeFromVault(playerTribe, priceToRename, p);
							copyConfigSection(tribesJson, playerTribe, databaseName, showName);
							
							mainClass.saveTribesFileJson();
							p.sendMessage(ChatColor.GREEN + "Tribe renamed!");
						} else {
							p.sendMessage(ChatColor.RED + "You need at least " + priceToRename + " sponges to in the tribe vault change the tribe name!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Please choose a different name.");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are not tribe chief!");
				}
				
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe! To view other tribes use /tribes info [tribe]");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
	
	//NEEDS TO BE FIXED. YOU CAN RENAME TO ANY TRIBE (EVEN EXISTING) AND IT JUST DELETES PREVIOUS
	public static void copyConfigSection(JsonObject jsonObject, String fromPath, String toPath, String showName) {
		JsonObject sourceSection = jsonObject.getAsJsonObject(fromPath);
		if (sourceSection == null) {
			return;
		}

		JsonObject destinationSection = jsonObject.getAsJsonObject(toPath);
		if (destinationSection == null) {
			destinationSection = new JsonObject();
			jsonObject.add(toPath, destinationSection);
		}

		for (Map.Entry<String, JsonElement> entry : sourceSection.entrySet()) {
			String key = entry.getKey();
			JsonElement value = entry.getValue();

			if (value.isJsonArray()) {
				JsonArray jsonArray = value.getAsJsonArray();
				JsonArray newArray = new JsonArray();
				for (JsonElement element : jsonArray) {
					newArray.add(element);
				}
				destinationSection.add(key, newArray);
			} else {
				destinationSection.add(key, value);
			}
		}

		destinationSection.addProperty("showname", showName);

		jsonObject.remove(fromPath);
	}
}
