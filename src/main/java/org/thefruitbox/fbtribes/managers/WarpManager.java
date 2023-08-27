package org.thefruitbox.fbtribes.managers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
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

	public void setCompound(String tribe, Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		String playerTribe = tribeManager.getPlayerTribe(p);
		JsonObject tribeObject = tribesJson.getAsJsonObject(playerTribe.toLowerCase());
		
		Location loc = p.getLocation();
		JsonObject compound = new JsonObject();
		compound.addProperty("world", loc.getWorld().getName() + "");
		compound.addProperty("x", loc.getX() + "");
		compound.addProperty("y", loc.getY() + "");
		compound.addProperty("z", loc.getZ() + "");
		compound.addProperty("yaw", loc.getYaw() + "");
		compound.addProperty("pitch", loc.getPitch() + "");

		tribeObject.add("compound", compound);
		
		tribeManager.sendMessageToMembers(playerTribe, ChatColor.GREEN + "Tribe compound has been set!");
		//setNumberOfWarps(playerTribe, 1);
        
		mainClass.saveTribesFileJson();
	}
	
	public void deleteCompound(String tribe, Player p) {
		JsonObject tribesJson = mainClass.getTribesJson();
		String playerTribe = tribeManager.getPlayerTribe(p);
		JsonObject tribeObject = tribesJson.getAsJsonObject(playerTribe.toLowerCase());
		
		if(compoundExists(playerTribe)) {
			tribeObject.add("compound", null);
			tribeManager.sendMessageToMembers(playerTribe, ChatColor.RED + "Tribe compound has been removed!");
			//setNumberOfWarps(playerTribe, 0);
			
			mainClass.saveTribesFileJson();
		} else {
			p.sendMessage(ChatColor.RED + "Tribe compound is not set!");
		}
	}
	
	public Boolean compoundExists(String tribe) {
		JsonObject tribesJson = mainClass.getTribesJson();
		JsonObject tribeObject = tribesJson.getAsJsonObject(tribe.toLowerCase());
		JsonObject compoundObject = tribeObject.getAsJsonObject("compound");

		return compoundObject != null;
	}
	
	public void warpPlayer(String tribe, Player p) {
	    inWarp.add(p);

		JsonObject tribesJson = mainClass.getTribesJson();
	    String playerTribe = tribeManager.getPlayerTribe(p);
		JsonObject tribeObject = tribesJson.getAsJsonObject(playerTribe.toLowerCase());
		JsonObject compoundObject = tribeObject.getAsJsonObject("compound");
	   
	    if (compoundObject != null) {
	        p.sendMessage(ChatColor.GREEN + "Warping in 5 seconds...");

	        BukkitRunnable warpTask = new BukkitRunnable() {
	        	
	            @Override
	            public void run() {
	                if (!inWarp.contains(p)) {
	                    // Player has cancelled the warp by moving
	                    return;
	                }           

	                // Retrieve warp location from config
	                double x = Double.parseDouble(compoundObject.get("x").getAsString());
	                double y = Double.parseDouble(compoundObject.get("y").getAsString());
	                double z = Double.parseDouble(compoundObject.get("x").getAsString());
	                int yaw = (int) Double.parseDouble(compoundObject.get("yaw").getAsString());
	                int pitch = (int) Double.parseDouble(compoundObject.get("pitch").getAsString());
	                String world = compoundObject.get("world").getAsString();
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
