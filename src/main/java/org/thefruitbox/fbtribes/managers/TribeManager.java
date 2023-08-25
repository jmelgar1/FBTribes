package org.thefruitbox.fbtribes.managers;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.thefruitbox.fbtribes.utilities.UnicodeCharacters;

public class TribeManager {

	//Main instance
	private Main mainClass = Main.getInstance();

	UnicodeCharacters uc = new UnicodeCharacters();
	
	public String getPlayerTribe(Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerUUID = p.getUniqueId().toString();
		
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			if(tribeSection.getStringList("members").contains(playerUUID) || tribeSection.get("chief").equals(playerUUID)) {
				return tribe;
			}
		}
		return "none";
	}
	
	public String getOfflinePlayerTribe(OfflinePlayer p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerUUID = p.getUniqueId().toString();
		
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			if(tribeSection.getStringList("members").contains(playerUUID) || tribeSection.get("chief").equals(playerUUID)) {
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
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		List<String> currentMembers = tribeSection.getStringList("members");
		return currentMembers;
	}
	
	public void setTribeMembers(String tribe, List<String> updatedMemberList) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("members", updatedMemberList);
		mainClass.saveTribesFile();
	}
	
	public String getTribeShowName(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String storageName = tribeSection.getString("showname");
		return storageName;
	}
	
	public boolean CheckForChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String chief = tribeSection.getString("chief");
		
		if(chief.equals(playerUUID)) {
			return true;
		}
		return false;
	}
	
	public boolean CheckForElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String elder = tribeSection.getString("elder");
		
		if(elder.equals(playerUUID)) {
			return true;
		}
		return false;
	}
	
	public boolean CheckSameTribe(String tribeOne, String tribeTwo) {
		if(tribeOne.equals(tribeTwo)) {
			return true;
		}
		return false;
	}
	
	public void setElder(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("elder", playerUUID);
		mainClass.saveTribesFile();
	}
	
	public void removeElder(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("elder", "");
		mainClass.saveTribesFile();
	}
	
	public String getElder(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String elder = tribeSection.getString("elder");
		return elder;
	}
	
	public void setChief(String tribe, Player p) {
		String playerUUID = p.getUniqueId().toString();
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		tribeSection.set("chief", playerUUID);
		mainClass.saveTribesFile();
	}
	
	public String getChief(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String chief = tribeSection.getString("chief");
		return chief;
	}
	
	public int getVault(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = tribeSection.getInt("vault");
		return vault;
	}
	
	public int getCTFWins(String tribe) {
		int ctf = 0;
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		ConfigurationSection tribalGamesSection = tribeSection.getConfigurationSection("tribalgameswins");
		
		if(tribalGamesSection != null) {
			ctf = tribalGamesSection.getInt("ctf");
		}

		return ctf;
	}
	
	public void addToVault(String tribe, int amount, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault + amount;
		ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
		ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#72C06C");
		ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
		ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#4BD613");
		String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
		sendMessageToMembers(tribe, ChatColor.GREEN + "(" + ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + ") " + transactionMessage);
		tribeSection.set("vault", newAmount);
		
		int totalSponges = tribeSection.getInt("totalSponges");
		totalSponges += amount;
		tribeSection.set("totalSponges", totalSponges);
		
		mainClass.saveTribesFile();
	}
	
	public void removeFromVault(String tribe, int amount, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int vault = getVault(tribe);
		int newAmount = vault - amount;
		
		if(newAmount >= 0) {
			ChatColor transactionColor0 = net.md_5.bungee.api.ChatColor.of("#E7761E");
			ChatColor transactionColor1 = net.md_5.bungee.api.ChatColor.of("#D69213");
			ChatColor transactionColor2 = net.md_5.bungee.api.ChatColor.of("#767166");
			ChatColor transactionColor3 = net.md_5.bungee.api.ChatColor.of("#C0A66C");
			String transactionMessage = transactionColor0 + "Tribe Vault: " + transactionColor1 + vault + transactionColor2 + " -> " + transactionColor3 + newAmount;
			sendMessageToMembers(tribe, ChatColor.GOLD + "(" + ChatColor.YELLOW + p.getName() + ChatColor.GOLD + ") " + transactionMessage);
			tribeSection.set("vault", newAmount);
			mainClass.saveTribesFile();
		} else {
			p.sendMessage(ChatColor.RED + "You can not afford this!");
		}
	}
	
	public void upgradeTribe(String tribe, int vault, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		
		if(vault >= 500 && getLevel(tribe) == 9) {
			tribeSection.set("level", 10);
			tribeSection.set("maxPlayers", 12);
			tribeSection.set("requiredSponges", -1);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 10!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 10!");
		} else if(vault >= 300 && getLevel(tribe) == 8) {
			tribeSection.set("level", 9);
			tribeSection.set("maxPlayers", 11);
			tribeSection.set("requiredSponges", 1500);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 9!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 9!");
		} else if(vault >= 200 && getLevel(tribe) == 7) {
			tribeSection.set("level", 8);
			tribeSection.set("maxPlayers", 10);
			tribeSection.set("requiredSponges", 1100);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 8!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 8!");
		} else if(vault >= 150 && getLevel(tribe) == 6) {
			tribeSection.set("level", 7);
			tribeSection.set("maxPlayers", 9);
			tribeSection.set("requiredSponges", 800);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 7!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 7!");
		} else if(vault >= 125 && getLevel(tribe) == 5) {
			tribeSection.set("level", 6);
			tribeSection.set("maxPlayers", 8);
			tribeSection.set("requiredSponges", 500);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 6!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 6!");
		} else if(vault >= 100 && getLevel(tribe) == 4) {
			tribeSection.set("level", 5);
			tribeSection.set("maxPlayers", 7);
			tribeSection.set("requiredSponges", 300);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 5!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 5!");
		} else if(vault >= 75 && getLevel(tribe) == 3) {
			tribeSection.set("level", 4);
			tribeSection.set("maxPlayers", 6);
			tribeSection.set("requiredSponges", 200);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 4!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 4!");
		} else if(vault >= 50 && getLevel(tribe) == 2) {
			tribeSection.set("level", 3);
			tribeSection.set("maxPlayers", 5);
			tribeSection.set("requiredSponges", 100);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 3!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 3!");
		} else if(vault >= 25 && getLevel(tribe) == 1) {
			tribeSection.set("level", 2);
			tribeSection.set("maxPlayers", 4);
			tribeSection.set("requiredSponges", 50);
			sendMessageToMembers(tribe, ChatColor.GREEN + p.getName() + " has upgraded the tribe to level 2!");
			Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + tribe + " has upgraded to level 2!");
		} else if(vault < 25 && getLevel(tribe) == 1) {
			tribeSection.set("level", 1);
			tribeSection.set("maxPlayers", 3);
			tribeSection.set("requiredSponges", 25);
		}
		
		mainClass.saveTribesFile();
	}

	public int getMaxPlayers(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int maxPlayers = tribeSection.getInt("maxPlayers");
		return maxPlayers;
	}
	
	public int getLevel(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int level = tribeSection.getInt("level");
		return level;
	}
	
	public String getFoundedDate(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		String dateCreated = tribeSection.getString("dateCreated");
		return dateCreated;
	}
	
	public int getRequiredAmountForLevelUp(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int requiredSponges = tribeSection.getInt("requiredSponges");
		return requiredSponges;
	}
	
	public int getRating(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		ConfigurationSection tribalGamesSection = tribeSection.getConfigurationSection("tribalgameswins");
		
		int rating = 0;
		
		if(tribalGamesSection != null) {
			rating = tribalGamesSection.getInt("rating");
		} else {
			tribeSection.createSection("tribalgameswins");
			tribeSection.getConfigurationSection("tribalgameswins").set("rating", 0);
		}
		
		return rating;
	}
	
	public double getEconomyScore(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		int economyScore = tribeSection.getInt("economyScore");
		return economyScore;
	}
	
	public double getPowerScore(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		double powerScore = tribeSection.getDouble("powerScore");
		return powerScore;
	}
	
	public void getTribeInfo(FileConfiguration tribesFile, ConfigurationSection tribeSection, String tribe, Player p, boolean bool) {
		WarpManager warpManager = new WarpManager();
		if(tribesFile.getConfigurationSection(tribe.toLowerCase()) != null) {
			String tribeName = tribeSection.getString("showname");
			int level = tribeSection.getInt("level");
			int vault = tribeSection.getInt("vault");
			double powerScore = tribeSection.getDouble("powerScore");
			OfflinePlayer chief = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("chief")));
			
			String elder = "";
			if(!tribeSection.getString("elder").isEmpty()) {
				elder = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeSection.getString("elder"))).getName();
			}
	
			String dateCreated = tribeSection.getString("dateCreated");
			
			
			p.sendMessage(ChatColor.GRAY + "---------[ " + mainClass.tribesColor + tribeName + ChatColor.GRAY + " ]---------");
			p.sendMessage(mainClass.lightGreen + uc.foundedDate + "Date Founded: " + mainClass.lighterGreen + dateCreated);
			p.sendMessage(mainClass.lightGreen + uc.level + "Level: " + mainClass.lighterGreen + level);
			p.sendMessage(mainClass.lightGreen + uc.vault + "Vault: " + mainClass.lighterGreen + vault);
			p.sendMessage(mainClass.lightGreen + uc.powerScore + "Power Score: " + mainClass.lighterGreen + powerScore);
			p.sendMessage(mainClass.lightGreen + uc.chiefCrown + "Chief: " + mainClass.lighterGreen + chief.getName());
			p.sendMessage(mainClass.lightGreen + uc.elderFace + "Elder: " + mainClass.lighterGreen + elder);
			
			List<String> membersUUID = tribeSection.getStringList("members");
			List<String> membersIGN = new ArrayList<String>();
			for(String member : membersUUID) {
				membersIGN.add(Bukkit.getServer().getOfflinePlayer(UUID.fromString(member)).getName());
			}
			
			String members = membersIGN.stream().collect(Collectors.joining(", "));
			
			p.sendMessage(mainClass.lightGreen + uc.member + "Members (" + membersIGN.size() + "/" + getMaxPlayers(tribe) + "): " + mainClass.lighterGreen + "" + members);
			
			if(bool == false) {
				if(warpManager.compoundExists(tribeName)) {
					p.sendMessage(mainClass.lightGreen + uc.compound + "Compound: " + ChatColor.GREEN + "ACTIVE");
				} else {
					p.sendMessage(mainClass.lightGreen + uc.compound + "Compound: " + ChatColor.RED + "INACTIVE");
				}
				
			}
		} else {
			p.sendMessage(ChatColor.RED + "That tribe name does not exist or the selected player is not in a tribe!");
		}
	}
	
	public void generateEconomy() {
		FileConfiguration tribesFile = mainClass.getTribes();
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			int totalSponges = tribeSection.getInt("totalSponges");
			int currentMembers = tribeSection.getStringList("members").size();
			double economyScore = (double)totalSponges * (Math.pow(currentMembers, 0.27895));
			tribeSection.set("economyScore", economyScore);
			mainClass.saveTribesFile();
		}
	}
	
	public void generateRating() {
		FileConfiguration tribesFile = mainClass.getTribes();
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);		
			ConfigurationSection tribalGamesSection = tribeSection.getConfigurationSection("tribalgameswins");
			int totalWins = tribalGamesSection.getInt("ctf") + tribalGamesSection.getInt("koth") + tribalGamesSection.getInt("tott");
			int rating = totalWins*100;
			tribeSection.set("rating", rating);	
			mainClass.saveTribesFile();
		}
	}
	
	public void generatePowerScore() {
		FileConfiguration tribesFile = mainClass.getTribes();
		for(String tribe : tribesFile.getKeys(false)) {
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe);
			double economyScore = tribeSection.getDouble("economyScore");
			ConfigurationSection tribalGamesSection = tribeSection.getConfigurationSection("tribalgameswins");
			
			double ratingScore = tribalGamesSection.getDouble("rating");
			double powerScore = economyScore + ratingScore;
			
			double powerScoreRounded = new BigDecimal(powerScore).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			tribeSection.set("powerScore", powerScoreRounded);	
			mainClass.saveTribesFile();
		}
	}
	
	public HashMap<String, Double> generateLeaderboard() {	
		HashMap<String, Double> tribeAndScore = new HashMap<>();
		FileConfiguration tribesFile = mainClass.getTribes();
		for(String tribe : tribesFile.getKeys(false)) {
			double powerScore = getPowerScore(tribe);
			String showName = getTribeShowName(tribe);
			tribeAndScore.put(showName, powerScore);
		}
		return tribeAndScore;
	}

	public HashMap<String, Double> generateLeaderboardJson() {
		HashMap<String, Double> tribeAndScore = new HashMap<>();
		FileConfiguration tribesFile = mainClass.getTribes();
		for(String tribe : tribesFile.getKeys(false)) {
			double powerScore = getPowerScore(tribe);
			String showName = getTribeShowName(tribe);
			tribeAndScore.put(showName, powerScore);
		}
		return tribeAndScore;
	}
	
	public void generateScore() {
		generateEconomy();
		generateRating();
		generatePowerScore();
	}
}
