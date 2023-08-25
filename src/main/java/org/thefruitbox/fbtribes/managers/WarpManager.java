package org.thefruitbox.fbtribes.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.Main;

import net.md_5.bungee.api.ChatColor;

public class WarpManager {
	
    private WarpCancelListener listener;
	
	//Main instance
	private static WarpManager instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	
	public List<Player> inWarp = new ArrayList<Player>();
	
	public static WarpManager getInstance() {
		return instance;
	}
	
//	public int getNumberOfWarps(String tribe) {
//		FileConfiguration tribesFile = mainClass.getTribes();
//		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
//		int currentWarps = tribeSection.getInt("currentWarps");
//		return currentWarps;
//	}
//	
//	public void setNumberOfWarps(String tribe, int num) {
//		FileConfiguration tribesFile = mainClass.getTribes();
//		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
//		tribeSection.set("currentWarps", num);
//	}
//	
//	public int getMaxWarps(String tribe) {
//		FileConfiguration tribesFile = mainClass.getTribes();
//		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
//		int maxWarps = tribeSection.getInt("maxWarps");
//		return maxWarps;
//	}
	
	public void setCompound(String tribe, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = tribeManager.getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		
		Location loc = p.getLocation();
		ConfigurationSection compound = tribeSection.createSection("compound");
		compound.set("world", loc.getWorld().getName() + "");
		compound.set("x", loc.getX() + "");
		compound.set("y", loc.getY() + "");
		compound.set("z", loc.getZ() + "");
		compound.set("yaw", loc.getYaw() + "");
		compound.set("pitch", loc.getPitch() + "");
		
		tribeManager.sendMessageToMembers(playerTribe, ChatColor.GREEN + "Tribe compound has been set!");
		//setNumberOfWarps(playerTribe, 1);
        
		mainClass.saveTribesFile();
	}
	
	public void deleteCompound(String tribe, Player p) {
		FileConfiguration tribesFile = mainClass.getTribes();
		String playerTribe = tribeManager.getPlayerTribe(p);
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
		
		if(compoundExists(playerTribe)) {
			tribeSection.set("compound", null);
			tribeManager.sendMessageToMembers(playerTribe, ChatColor.RED + "Tribe compound has been removed!");
			//setNumberOfWarps(playerTribe, 0);
			
			mainClass.saveTribesFile();
		} else {
			p.sendMessage(ChatColor.RED + "Tribe compound is not set!");
		}
	}
	
	public Boolean compoundExists(String tribe) {
		FileConfiguration tribesFile = mainClass.getTribes();
		ConfigurationSection tribeSection = tribesFile.getConfigurationSection(tribe.toLowerCase());
		ConfigurationSection compound = tribeSection.getConfigurationSection("compound");
		
		if(compound == null) {
			return false;
		}
		
		return true;
	}
	
	public void warpPlayer(String tribe, Player p) {
	    inWarp.add(p);

	    FileConfiguration tribesFile = mainClass.getTribes();
	    String playerTribe = tribeManager.getPlayerTribe(p);
	    ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe.toLowerCase());
	    ConfigurationSection compound = tribeSection.getConfigurationSection("compound");
	   
	    if (compound != null) {
	        p.sendMessage(ChatColor.GREEN + "Warping in 5 seconds...");

	        BukkitRunnable warpTask = new BukkitRunnable() {
	        	
	            @Override
	            public void run() {
	                if (!inWarp.contains(p)) {
	                    // Player has cancelled the warp by moving
	                    return;
	                }           

	                // Retrieve warp location from config
	                double x = Double.parseDouble(compound.getString("x"));
	                double y = Double.parseDouble(compound.getString("y"));
	                double z = Double.parseDouble(compound.getString("z"));
	                int yaw = (int) Double.parseDouble(compound.getString("yaw"));
	                int pitch = (int) Double.parseDouble(compound.getString("pitch"));
	                String world = compound.getString("world");
	                Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

	                // Teleport player
	                p.teleport(loc);

	                int priceToWarp = mainClass.getPrices().getInt("gotowarp");
	                tribeManager.removeFromVault(playerTribe, priceToWarp, p);

	                p.sendMessage(ChatColor.GREEN + "Warp successful!");
	                inWarp.remove(p);
	                
	                // Unregister the listener and remove the played from the inWarp list
	                HandlerList.unregisterAll(listener);
	                inWarp.remove(p);
	            }
	        };
	        
            listener = new WarpCancelListener(p, warpTask, inWarp);
            Bukkit.getPluginManager().registerEvents(listener, mainClass);
	        
	        // Schedule the warp task to run after 5 seconds
	        warpTask.runTaskLater(mainClass, 100);

	    } else {
	        p.sendMessage(ChatColor.RED + "Compound does not exist!");
	    }
	}
}
