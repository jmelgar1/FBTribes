package org.thefruitbox.fbtribes.tribalgames.managers;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.thefruitbox.fbtribes.Main;

public class CTFEvents implements Listener {
	
	//Main instance
	protected Main mainClass = Main.getInstance();
	
	public void registerEvents() {
		try {
			Bukkit.getServer().getPluginManager().registerEvents(this, mainClass);
			System.out.println("FBCTF: Events Registered");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void unregisterEvents() {
		try {
			HandlerList.unregisterAll(this);
			System.out.println("FBCTF: Events Unregistered");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
