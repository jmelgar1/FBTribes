package org.thefruitbox.fbtribes.managers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.thefruitbox.fbtribes.Main;

public class SpongeManager {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public int checkForUnclaimed(Player p) {
		ConfigurationSection rewards = mainClass.getRewards();
		String playerUUID = p.getUniqueId().toString();
		if(rewards.getConfigurationSection(playerUUID) != null) {
			ConfigurationSection rewardSection = rewards.getConfigurationSection(playerUUID);
			if(rewardSection.getInt("unclaimed") != 0) {
				int amount = rewardSection.getInt("unclaimed");
				return amount;
			}
		}
		return 0;
	}	
	
	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max-min)) + min);
	}
}
