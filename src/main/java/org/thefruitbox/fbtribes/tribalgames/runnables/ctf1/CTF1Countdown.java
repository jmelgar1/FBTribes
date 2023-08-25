package org.thefruitbox.fbtribes.tribalgames.runnables.ctf1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.tribalgames.managers.CTF1Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CTF1Countdown extends BukkitRunnable implements Listener, CTF1Manager {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//tribe manager
	TribeManager tm = new TribeManager();
	
	public List<Player> playersInArena = new ArrayList<Player>();
	
	//DEFAULT 3600
	int seconds = 3600;
	
	BossBar bar = Bukkit.createBossBar("countdown", BarColor.YELLOW, BarStyle.SEGMENTED_10);
	
	Map<Player, BossBar> playerBossbar = new HashMap<Player, BossBar>();
	
    DecimalFormat dFormat = new DecimalFormat("00");

	public void run() {
		
		double minutes = seconds/60.0;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(ctf1.contains(p.getLocation().getBlockX(), 
							   p.getLocation().getBlockY(),
							   p.getLocation().getBlockZ())) {
				addPlayerToArena(p, playersInArena);
			} else {
				removePlayerFromArena(p, playersInArena);
			}	
			
			//only send notification on certain time intervals
			if(minutes == 60 || minutes == 45 || minutes == 30 || minutes == 20 || minutes == 10 ||
					minutes == 5 || minutes == 3 || minutes == 1) {
						
				sendReminder(p, minutes);
			}
		}
		
		if(seconds <= 5) {
			for(Player p : playersInArena) {
				if(p != null) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0F);
				}
			}
		}
		
		if((seconds -= 1) == 0) {
			//CHANGE BACK
			if(playersInArena.size() > 4) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 0F);
				}
				
				Bukkit.broadcastMessage(mainClass.tgPrefix + ChatColor.RED + "Not enough players are in the CTF arena. This event will be skipped!");
				removeAndCancel();
				
			} else {
				
				//create list of tribes and participants
				List<String> participants = new ArrayList<String>();
				List<String> tribes = new ArrayList<String>();
				List<String> chosenTribes = new ArrayList<String>();
				
				//convert player to participants and add to tribe list
				for(Player p : playersInArena) {
					String tribe = tm.getPlayerTribe(p);
					if(!tribes.contains(tribe) && !tribe.equals("none")) {
						tribes.add(tribe);
					}
				}
				
				//check if there are 2 or more different tribes in the arena
				if(tribes.size() < 2) {
					
					Bukkit.broadcastMessage(mainClass.tgPrefix + ChatColor.RED + "At least 2 tribes must be present in the arena! This event will be skipped!");
					removeAndCancel();
				
					//if all requirements are met
				} else {
					
					//get two random tribes from list
					for(int i = 0; i < 2; i++) {
						chosenTribes.add(getRandomTribe(tribes));
					}
					
					//add players in chosen tribes to list of participants
					for(Player p : playersInArena) {
						if(chosenTribes.contains(tm.getPlayerTribe(p))) {
							participants.add(p.getName());
						}
					}
	
					//set the red team
					CTF1Manager.getRedTeam().set("tribe", chosenTribes.get(0));
					
					//set blue tea,
					CTF1Manager.getBlueTeam().set("tribe", chosenTribes.get(1));
					
					//get players and their respective team
					List<String> redPlayers = new ArrayList<String>();
					List<String> bluePlayers = new ArrayList<String>();
					for(String player : participants) {
						Player p = Bukkit.getPlayer(player);
						if(tm.getPlayerTribe(p).equals(chosenTribes.get(0))) {
							redPlayers.add(player);
						} else {
							bluePlayers.add(player);
						}
					}
					
					//add players to cfg
					CTF1Manager.getRedTeam().set("players", redPlayers);
					CTF1Manager.getBlueTeam().set("players", bluePlayers);
					
					mainClass.saveCTFFile();

					//send start message
					Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "=========" + 
					mainClass.tribalGames + ChatColor.BOLD + "TRIBAL GAMES" + 
							ChatColor.DARK_GRAY + "=========");
					
					Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "         CTF Match Started");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("      " + ChatColor.RED.toString() + ChatColor.BOLD + CTF1Manager.getRedTeamName() + 
							ChatColor.YELLOW + " VS " + ChatColor.BLUE.toString() + ChatColor.BOLD + CTF1Manager.getBlueTeamName());
					Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "===============================");
					
					CTF1Manager.sendParticipantsMessage(mainClass.tgPrefix + ChatColor.GOLD + "Let the games begin!", Sound.ITEM_GOAT_HORN_SOUND_2, 0.5F, 0F);
					
					ctf.set("participants", participants);
					ctf.set("playersInArena", playersInArena);
					mainClass.saveCTFFile();
					
					//run event runnable
					CTF1BeginEvent ctf1 = new CTF1BeginEvent();
					ctf1.run();
					
					//remove bossbar and cancel event
					removeAndCancel();
				}
			}
			
			} else {
				
				//continue the countdown DEFAULT 3600D
				bar.setProgress(seconds / 3600D);
				String minutesTimer = String.valueOf(seconds/60);
				String secondsTimer = dFormat.format(seconds % 60);
				bar.setTitle("CTF starts in " + minutesTimer + ":" + secondsTimer);
			}
	}
	
	String getRandomTribe(List<String> list){
		Random rand = new Random();
		String chosenTribe = list.get(rand.nextInt(list.size())); 
		list.remove(chosenTribe);
        return chosenTribe;
	}
	
	void removeAndCancel() {
		bar.removeAll();
		cancel();
	}
	
	//add a player to the bossbar
	public void addPlayer(Player p) {
		bar.addPlayer(p);
	}
	
	@EventHandler
	void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		playerBossbar.put(p, Bukkit.createBossBar("Event will begin shortly", BarColor.PINK, BarStyle.SEGMENTED_10));
	}
	
	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		BossBar bar = playerBossbar.get(p);
		if(bar != null) {
			bar.addPlayer(p);
		}
	}
	 
	@SuppressWarnings("deprecation")
	void sendReminder(Player p, double minutes) {
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 0.84F);
		
		String time;
		
		int newMinutes = (int)minutes;
		
		if(minutes == 60) {
			time = "1 Hour";
		} else if(minutes == 1){
			time = newMinutes + " Minute";
		} else {
			time = newMinutes + " Minutes";
		}
		
		p.sendMessage(ChatColor.GRAY + "-----" +
				mainClass.tribalGames + ChatColor.BOLD.toString() + "TRIBAL GAMES" +
				ChatColor.GRAY + "-----");
		p.sendMessage(ChatColor.GRAY + "Game: ");
		p.sendMessage(ChatColor.YELLOW + "Capture The Flag - Site 1");
		p.sendMessage("");
		p.sendMessage(ChatColor.GRAY + "Location: ");
		p.sendMessage(ChatColor.AQUA + "/warp ctf1");
		p.sendMessage("");
		p.sendMessage(ChatColor.GRAY + "Time Until Event: ");
		p.sendMessage(ChatColor.GREEN + time);
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD.toString() + ChatColor.ITALIC + "How to participate:");
		
		TextComponent infoLink = new TextComponent(ChatColor.BLUE.toString() + ChatColor.BOLD + ChatColor.ITALIC + "Click Here");
		infoLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://thefruitbox.net/ctf.html"));
		infoLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("View CTF Info!").create()));
		p.spigot().sendMessage(infoLink);
		p.sendMessage("");
		p.sendMessage(ChatColor.RED + "This feature is currently in beta testing! Please report bugs!");
		p.sendMessage(ChatColor.GRAY + "-----------------------------------");
	}
	
	//add a player to the arena list
	void addPlayerToArena(Player p, List<Player> list) {
		if(!list.contains(p)) {
			list.add(p);
			bar.addPlayer(p);
		}
	}
	
	
	//remove a player from the arena list
	void removePlayerFromArena(Player p, List<Player> list) {
		if(list.contains(p)) {
			list.remove(p);
			bar.removePlayer(p);
		}
	}
}
