package org.thefruitbox.fbtribes.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;

import net.md_5.bungee.api.ChatColor;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;
import org.thefruitbox.fbtribes.utilities.JsonUtilities;
import org.thefruitbox.fbtribes.utilities.UnicodeCharacters;

import javax.json.Json;

public class TribeManager {

	//Main instance
	private Main mainClass = Main.getInstance();

	private UnicodeCharacters uc = new UnicodeCharacters();
	private ChatUtilities cu = new ChatUtilities();
	private JsonUtilities json = new JsonUtilities();
	
	public String getPlayerTribe(Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		String playerUUID = p.getUniqueId().toString();
		
		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			JsonArray memberArray = tribeObject.getAsJsonArray("members");
			if(json.JsonArrayToStringList(memberArray).contains(playerUUID)) {
				return tribe;
			}
		}
		return "none";
	}

	public String getPlayerTribe(OfflinePlayer p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		String playerUUID = p.getUniqueId().toString();

		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			JsonArray memberArray = tribeObject.getAsJsonArray("members");
			if(json.JsonArrayToStringList(memberArray).contains(playerUUID)) {
				return tribe;
			}
		}
		return "none";
	}
	
	public void sendMessageToMembers(String tribe, String message) {
		 List<String> members = getTribeMembers(tribe);
		 
		 for(String member : members) {
			 UUID playerUUID = UUID.fromString(member);
			 Player p = Bukkit.getServer().getPlayer(playerUUID);
			 if(p != null) {
				 p.sendMessage(message);
			 }
		 }
	}
	
	public List<String> getTribeMembers(String tribe){
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		JsonArray tribeMembers = tribeObject.getAsJsonArray("members");

		return json.JsonArrayToStringList(tribeMembers);
	}
	
	public void setTribeMembers(String tribe, List<String> updatedMemberList) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());

		Gson gson = new Gson();
		tribeObject.add("members", gson.toJsonTree(updatedMemberList).getAsJsonArray());
		mainClass.saveTribesFileJson();
	}
	
	public String getTribeShowName(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("showname").getAsString();
	}
	
	public boolean CheckForChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		String chief = tribeObject.get("chief").getAsString();

		return chief.equals(playerUUID);
	}
	
	public boolean CheckForElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		String elder = tribeObject.get("elder").getAsString();

		return elder.equals(playerUUID);
	}
	
	public boolean CheckSameTribe(String tribeOne, String tribeTwo) {
		return tribeOne.equals(tribeTwo);
	}

	public void setChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		tribeObject.addProperty("chief", playerUUID);
		mainClass.saveTribesFileJson();
	}

	public String getChief(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("chief").getAsString();
	}
	
	public void setElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		tribeObject.addProperty("elder", playerUUID);
		mainClass.saveTribesFileJson();
	}
	
	public String getElder(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("elder").getAsString();
	}

	public void removeElder(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		tribeObject.addProperty("elder", "");
		mainClass.saveTribesFileJson();
	}
	
	public int getVault(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("vault").getAsInt();
	}
	
	public int getCTFWins(String tribe) {
		int ctf = 0;
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		JsonObject tribalGamesSection = tribeObject.getAsJsonObject("tribalgameswins");
		
		if(tribalGamesSection != null) {
			ctf = tribalGamesSection.get("ctf").getAsInt();
		}

		return ctf;
	}
	
	public void addToVault(String tribe, int amount, Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault + amount;
		ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
		ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#72C06C");
		ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
		ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#4BD613");
		String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
		sendMessageToMembers(tribe, ChatColor.GREEN + "(" + ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + ") " + transactionMessage);
		tribeObject.addProperty("vault", newAmount);
		
		int totalSponges = tribeObject.get("totalSponges").getAsInt();
		totalSponges += amount;
		tribeObject.addProperty("totalSponges", totalSponges);
		
		mainClass.saveTribesFileJson();
	}
	
	public void removeFromVault(String tribe, int amount, Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault - amount;
		
		if(newAmount >= 0) {
			ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
			ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#D69213");
			ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
			ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#C0A66C");
			String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
			sendMessageToMembers(tribe, ChatColor.GOLD + "(" + ChatColor.YELLOW + p.getName() + ChatColor.GOLD + ") " + transactionMessage);
			tribeObject.addProperty("vault", newAmount);
			mainClass.saveTribesFileJson();
		} else {
			p.sendMessage(ChatColor.RED + "You can not afford this!");
		}
	}
	
	public void upgradeTribe(String tribe, int vault, Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		
		if(vault >= 500 && getLevel(tribe) == 9) {
			tribeObject.addProperty("level", 10);
			tribeObject.addProperty("maxPlayers", 12);
			tribeObject.addProperty("requiredSponges", -1);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 10!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 10!");
		} else if(vault >= 300 && getLevel(tribe) == 8) {
			tribeObject.addProperty("level", 9);
			tribeObject.addProperty("maxPlayers", 11);
			tribeObject.addProperty("requiredSponges", 1500);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 9!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 9!");
		} else if(vault >= 200 && getLevel(tribe) == 7) {
			tribeObject.addProperty("level", 8);
			tribeObject.addProperty("maxPlayers", 10);
			tribeObject.addProperty("requiredSponges", 1100);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 8!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 8!");
		} else if(vault >= 150 && getLevel(tribe) == 6) {
			tribeObject.addProperty("level", 7);
			tribeObject.addProperty("maxPlayers", 9);
			tribeObject.addProperty("requiredSponges", 800);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 7!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 7!");
		} else if(vault >= 125 && getLevel(tribe) == 5) {
			tribeObject.addProperty("level", 6);
			tribeObject.addProperty("maxPlayers", 8);
			tribeObject.addProperty("requiredSponges", 500);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 6!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 6!");
		} else if(vault >= 100 && getLevel(tribe) == 4) {
			tribeObject.addProperty("level", 5);
			tribeObject.addProperty("maxPlayers", 7);
			tribeObject.addProperty("requiredSponges", 300);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 5!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 5!");
		} else if(vault >= 75 && getLevel(tribe) == 3) {
			tribeObject.addProperty("level", 4);
			tribeObject.addProperty("maxPlayers", 6);
			tribeObject.addProperty("requiredSponges", 200);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 4!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 4!");
		} else if(vault >= 50 && getLevel(tribe) == 2) {
			tribeObject.addProperty("level", 3);
			tribeObject.addProperty("maxPlayers", 5);
			tribeObject.addProperty("requiredSponges", 100);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 3!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 3!");
		} else if(vault >= 25 && getLevel(tribe) == 1) {
			tribeObject.addProperty("level", 2);
			tribeObject.addProperty("maxPlayers", 4);
			tribeObject.addProperty("requiredSponges", 50);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 2!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 2!");
		} else if(vault < 25 && getLevel(tribe) == 1) {
			tribeObject.addProperty("level", 1);
			tribeObject.addProperty("maxPlayers", 3);
			tribeObject.addProperty("requiredSponges", 25);
		}
		
		mainClass.saveTribesFileJson();
	}

	public int getMaxPlayers(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("maxPlayers").getAsInt();
	}
	
	public int getLevel(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("level").getAsInt();
	}
	
	public String getFoundedDate(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("dateCreated").getAsString();
	}
	
	public int getRequiredAmountForLevelUp(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("requiredSponges").getAsInt();
	}
	
	public int getRating(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		JsonObject tribalGamesObject = tribeObject.getAsJsonObject("tribalgameswins");
		
		int rating = 0;
		
		if(tribalGamesObject != null) {
			rating = tribalGamesObject.get("rating").getAsInt();
		}
		
		return rating;
	}
	
	public double getEconomyScore(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("economyScore").getAsInt();
	}

	public double getPowerScore(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		return tribeObject.get("powerScore").getAsDouble();
	}
	
	public void getTribeInfo(JsonObject tribesJson, JsonObject tribeObject, String tribe, Player p, boolean bool) {
		WarpManager warpManager = new WarpManager();
		if(tribesJson.getAsJsonObject(tribe.toLowerCase()) != null) {
			String tribeName = tribeObject.get("showname").getAsString();
			int level = tribeObject.get("level").getAsInt();
			int vault = tribeObject.get("vault").getAsInt();
			double powerScore = tribeObject.get("powerScore").getAsDouble();
			OfflinePlayer chief = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeObject.get("chief").getAsString()));
			
			String elder = "";
			if(!tribeObject.get("elder").getAsString().isEmpty()) {
				elder = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeObject.get("elder").getAsString())).getName();
			}
	
			String dateCreated = tribeObject.get("dateCreated").getAsString();
			
			
			p.sendMessage(ChatColor.GRAY + "---------[ " + cu.tribesColor + tribeName + ChatColor.GRAY + " ]---------");
			p.sendMessage(cu.lightGreen + uc.foundedDate + "Date Founded: " + cu.lighterGreen + dateCreated);
			p.sendMessage(cu.lightGreen + uc.level + "Level: " + cu.lighterGreen + level);
			p.sendMessage(cu.lightGreen + uc.vault + "Vault: " + cu.lighterGreen + vault);
			p.sendMessage(cu.lightGreen + uc.powerScore + "Power Score: " + cu.lighterGreen + powerScore);
			p.sendMessage(cu.lightGreen + uc.chiefCrown + "Chief: " + cu.lighterGreen + chief.getName());
			p.sendMessage(cu.lightGreen + uc.elderFace + "Elder: " + cu.lighterGreen + elder);
			
			List<String> membersUUID = json.JsonArrayToStringList(tribeObject.get("members").getAsJsonArray());
			List<String> membersIGN = new ArrayList<String>();
			for(String member : membersUUID) {
				membersIGN.add(Bukkit.getServer().getOfflinePlayer(UUID.fromString(member)).getName());
			}
			
			String members = membersIGN.stream().collect(Collectors.joining(", "));
			
			p.sendMessage(cu.lightGreen + uc.member + "Members (" + membersIGN.size() + "/" + getMaxPlayers(tribe) + "): " + cu.lighterGreen + members);
			
			if(!bool) {
				if(warpManager.compoundExists(tribeName)) {
					p.sendMessage(cu.lightGreen + uc.compound + "Compound: " + ChatColor.GREEN + "ACTIVE");
				} else {
					p.sendMessage(cu.lightGreen + uc.compound + "Compound: " + ChatColor.RED + "INACTIVE");
				}
				
			}
		} else {
			p.sendMessage(ChatColor.RED + "That tribe name does not exist or the selected player is not in a tribe!");
		}
	}
	
	public void generateEconomy() {
		JsonObject tribesJson = mainClass.getTribesJson();
		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			int totalSponges = tribeObject.get("totalSponges").getAsInt();
			int currentMembers = tribeObject.get("members").getAsJsonArray().size();
			double economyScore = (double)totalSponges * (Math.pow(currentMembers, 0.27895));
			tribeObject.addProperty("economyScore", economyScore);
			mainClass.saveTribesFileJson();
		}
	}

	//disabled for now
	public void generateRating() {
		JsonObject tribesJson = mainClass.getTribesJson();
		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			JsonObject tribalGamesObject = tribeObject.getAsJsonObject("tribalgameswins");
			int totalWins = tribalGamesObject.get("ctf").getAsInt() +
					tribalGamesObject.get("koth").getAsInt() + tribalGamesObject.get("tott").getAsInt();
			int rating = totalWins*100;
			tribeObject.addProperty("rating", rating);
			mainClass.saveTribesFileJson();
		}
	}
	
	public void generatePowerScore() {
		JsonObject tribesJson = mainClass.getTribesJson();
		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			double economyScore = tribeObject.get("economyScore").getAsDouble();
			JsonObject tribalGamesObject = tribeObject.getAsJsonObject("tribalgameswins");
			
			double ratingScore = tribalGamesObject.get("rating").getAsDouble();
			double powerScore = economyScore + ratingScore;
			
			double powerScoreRounded = new BigDecimal(powerScore).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

			tribeObject.addProperty("powerScore", powerScoreRounded);
			mainClass.saveTribesFileJson();
		}
	}

	public void generateEconomyPerTribe(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
		int totalSponges = tribeObject.get("totalSponges").getAsInt();
		int currentMembers = tribeObject.get("members").getAsJsonArray().size();
		double economyScore = (double)totalSponges * (Math.pow(currentMembers, 0.27895));
		tribeObject.addProperty("economyScore", economyScore);
		mainClass.saveTribesFileJson();
	}

	public void generatePowerScorePerTribe(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
		double economyScore = tribeObject.get("economyScore").getAsDouble();
		JsonObject tribalGamesObject = tribeObject.getAsJsonObject("tribalgameswins");

		double ratingScore = tribalGamesObject.get("rating").getAsDouble();
		double powerScore = economyScore + ratingScore;

		double powerScoreRounded = new BigDecimal(powerScore).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		tribeObject.addProperty("powerScore", powerScoreRounded);
		mainClass.saveTribesFileJson();
	}

	public HashMap<String, Double> generateLeaderboardJson() {
		HashMap<String, Double> tribeAndScore = new HashMap<>();
		JsonObject tribesJson = mainClass.getTribesJson();
		for(String tribe : tribesJson.keySet()) {
			JsonObject tribeObject = tribesJson.getAsJsonObject(tribe);
			double powerScore = tribeObject.get("powerScore").getAsDouble();
			String showName = tribeObject.get("showname").getAsString();
			tribeAndScore.put(showName, powerScore);
		}
		return tribeAndScore;
	}

	public void generateScorePerTribe(String tribe) {
		generateEconomyPerTribe(tribe);
		generatePowerScorePerTribe(tribe);
	}
	
	public void generateScore() {
		generateEconomy();

		//disabled for now
		//generateRating();
		generatePowerScore();
	}
}
