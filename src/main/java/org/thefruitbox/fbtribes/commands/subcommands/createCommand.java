package org.thefruitbox.fbtribes.commands.subcommands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;
import org.thefruitbox.fbtribes.utilities.JsonUtilities;

public class createCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	private final ChatUtilities cu = new ChatUtilities();
	private JsonUtilities json = new JsonUtilities();

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Create a tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes create [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		if(args.length == 2) {
			String tribeName = args[1];
			if(tribeName.matches("[a-zA-Z]+")) {
				if(tribeName.length() <= 16 && tribeName.length() >= 4) {
				//FileConfiguration tribesFile = mainClass.getTribes();

				JsonObject tribesJson = mainClass.getTribesJson();
				
				String playerUUID = p.getUniqueId().toString();
				
				boolean inTribe = false;

				//Loop through tribes in tribes.json
				for(String tribe : tribesJson.keySet()){
					JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
					JsonArray membersArray = tribeObject.getAsJsonArray("members");
					String chiefUUID = tribeObject.get("chief").getAsString();

					TribeManager tm = new TribeManager();
					List<String> currentMembers = json.JsonArrayToStringList(membersArray);

					//check if player is a member or a chief in a tribe
					try{
						if(currentMembers.contains(playerUUID) || chiefUUID.equals(playerUUID)) {
							inTribe = true;
							break;
						}
					} catch (JsonIOException e){
						e.printStackTrace();
					}
				}
				
				boolean tribeExists = false;
				for(String tribe : tribesJson.keySet()) {
					if (tribe.equalsIgnoreCase(tribeName)) {
						tribeExists = true;
						break;
					}
				}
				
				if(!inTribe) {
					if(tribesJson.get(tribeName) == null && !tribeExists) {
						JsonObject newTribe = new JsonObject();

						newTribe.addProperty("level", 1);
						newTribe.addProperty("chief", playerUUID);
						newTribe.addProperty("elder", "");
						newTribe.addProperty("vault", 0);
						newTribe.addProperty("showname", tribeName);
						newTribe.addProperty("currentWarps", 0);
						newTribe.addProperty("maxWarps", 1);
						newTribe.addProperty("maxPlayers", 3);
						newTribe.addProperty("requiredSponges", 50);
						newTribe.addProperty("totalSponges", 0);
						newTribe.addProperty("economyScore", 0);

						JsonObject tribalGamesSection = new JsonObject();
						tribalGamesSection.addProperty("ctf", 0);
						tribalGamesSection.addProperty("koth", 0);
						tribalGamesSection.addProperty("tott", 0);
						tribalGamesSection.addProperty("rating", 0);
						newTribe.add("tribalgameswins", tribalGamesSection);

						SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
						Date now = new Date();
						newTribe.addProperty("dateCreated", format.format(now));
						
						JsonArray tribeMembers = new JsonArray();
						tribeMembers.add(playerUUID);
						newTribe.add("members", tribeMembers);

						//add power_score and rating
						newTribe.addProperty("powerScore", 0);
						newTribe.addProperty("rating", 0);

						//try catch to add new tribe to system
						try{
							tribesJson.add(tribeName.toLowerCase(), newTribe);
							mainClass.saveTribesFileJson();

							Bukkit.broadcastMessage(cu.tribesColor + tribeName + " has been created by " + p.getName() + "!");
							for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
								pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 0);
							}
						} catch (Exception e){
							p.sendMessage(cu.sendMajorError(e.toString()));
						}
						
					} else {
						p.sendMessage(ChatColor.RED + "That tribe name is already in use!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are already in a tribe!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Tribe names must be between 4 and 16 characters long!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Tribe names must only contain upper or lower case letters!");
		}
	} else {
		p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
}
