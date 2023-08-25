package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;

import net.md_5.bungee.api.ChatColor;

public class inviteCommand extends SubCommand {
	
	private static inviteCommand instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	TribeManager tribeManager = new TribeManager();
	
	public Map<String, Player> TribeInvites = new HashMap<String, Player>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "invite";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Invite a player to your tribe";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes invite [player]";
	}

	@SuppressWarnings("null")
	@Override
	public void perform(Player p, String[] args) {
		instance = this;
		
		String playerTribe = tribeManager.getPlayerTribe(p);
		String tribeName = tribeManager.getTribeShowName(playerTribe);
		
		if (args.length == 2) {
			if(tribeManager.CheckForElder(playerTribe, p) == true || tribeManager.CheckForChief(playerTribe, p) == true) {
				int playerCap = tribeManager.getTribeMembers(playerTribe).size();
				if(tribeManager.getMaxPlayers(playerTribe) > playerCap) {
					String playerIGN = args[1];
					if(!playerIGN.equals(p.getName())) {
						Player invitee = Bukkit.getServer().getPlayer(playerIGN);
						
						boolean alreadyInvited = false;
						String admin = "ADMIN_10";
						if(invitee != null || !invitee.getName().equals(admin)) {
							 
							for(Map.Entry<String, Player> entry : TribeInvites.entrySet()) {
								String tribe = entry.getKey();
								Player player = entry.getValue();
								
								if(tribe.equals(playerTribe) && player.equals(invitee)) {
									alreadyInvited = true;
									break;
								}
							}
							
							if(tribeManager.getPlayerTribe(invitee).equals("none")) {
								if(alreadyInvited == false) {
									TribeInvites.put(playerTribe, invitee);
									
									invitee.sendMessage(mainClass.tribesColor + "You have been invited to join " + mainClass.lightGreen + tribeName
											+ ChatColor.GRAY + "\nType " + ChatColor.DARK_GREEN + "/tribes accept " + playerTribe + ChatColor.GRAY + " to accept the invite! "
											+ ChatColor.GRAY + "\nType " + ChatColor.DARK_RED + "/tribes decline " + playerTribe + ChatColor.GRAY + " to decline the invite! "
											+ ChatColor.DARK_GRAY + "\nThis request will expire in 3 minutes!");
									
									p.sendMessage(ChatColor.GREEN + "Invitation successfully sent to " + playerIGN);
									
									new BukkitRunnable() {
										
										@Override
										public void run() {
											if(CheckForActiveInvite(TribeInvites, playerTribe.toLowerCase(), invitee) == true) {
												TribeInvites.remove(playerTribe, invitee);
												p.sendMessage(ChatColor.RED + "Your tribe invite to " + playerIGN + " has expired!");
												invitee.sendMessage(ChatColor.RED + "Your invitation to join " + playerTribe + " has expired!");
											}
										}
									}.runTaskLater(mainClass, 3600);
									
								} else {
									p.sendMessage(ChatColor.RED + playerIGN + " already has an active invite from your tribe!");
								}
							} else {
								p.sendMessage(ChatColor.RED + playerIGN + " is already in a tribe!");
							}	
						} else {
							p.sendMessage(ChatColor.RED + playerIGN + " is not online!");
						}	
					} else {
						p.sendMessage(ChatColor.RED + "You can not invite yourself!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Your tribe has a max player cap of " + playerCap + "!"
							+ " Level up your tribe to raise the cap!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "This command can only be used by Chiefs and Elders!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
	
	public boolean CheckForActiveInvite(Map<String, Player> TribeInvites, String playerTribe, Player invitee) {
		for(Map.Entry<String, Player> entry : TribeInvites.entrySet()) {
			String tribe = entry.getKey().toLowerCase();
			Player player = entry.getValue();
			
			if(tribe.equals(playerTribe) && player.equals(invitee)) {
				return true;
			}
		}
		return false;
	}
	
	public static inviteCommand getInstance() {
		return instance;
	}
}
