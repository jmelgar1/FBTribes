package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.managers.WarpManager;

import net.md_5.bungee.api.ChatColor;

public class infoCommand extends SubCommand implements Listener {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	static TribeManager tribeManager = new TribeManager();
	static WarpManager warpManager = new WarpManager();
	
	private static Inventory inv;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "info";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "See tribe info";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes info [tribe]";
	}

	@Override
	public void perform(Player p, String[] args) {
		tribeManager.generateScore();
		String playerTribe = tribeManager.getPlayerTribe(p);
		FileConfiguration tribesFile = mainClass.getTribes();
		
		//player's tribe
		if(args.length == 1) {
			if(!playerTribe.equals("none")) {
      	        
	        	inv = Bukkit.createInventory(null, 54, mainClass.tribalGames.toString() + ChatColor.BOLD + playerTribe.toUpperCase() + " Tribe Profile");
	        	openInventory(p);
	        	initializeItems(p, playerTribe);
				
				ConfigurationSection tribeSection = tribesFile.getConfigurationSection(playerTribe);
				tribeManager.getTribeInfo(tribesFile, tribeSection, playerTribe, p, false);
				
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a tribe! To view other tribes use /tribes info [tribe]");
			}
			
			//other players tribe
		} else if (args.length == 2) {
			String otherTribe = args[1];
			
			ConfigurationSection tribeSection = tribesFile.getConfigurationSection(otherTribe.toLowerCase());
			tribeManager.getTribeInfo(tribesFile, tribeSection, otherTribe, p, true);
		} else {
			p.sendMessage(ChatColor.RED + "Correct usage: " + getSyntax());
		}
	}
	
	public void initializeItems(Player p, String tribe) {
		String dateCreated = tribeManager.getFoundedDate(tribe);
		int level = tribeManager.getLevel(tribe);
		int vault = tribeManager.getVault(tribe);
		int requiredSponges = tribeManager.getRequiredAmountForLevelUp(tribe);
		int ratingScore = tribeManager.getRating(tribe);
		double economyScore = tribeManager.getEconomyScore(tribe);
		double powerScore = tribeManager.getPowerScore(tribe);
		//OfflinePlayer chief = Bukkit.getServer().getOfflinePlayer(tribeManager.getChief(tribe));
		
		String chief = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeManager.getChief(tribe))).getName();
		
		System.out.println(chief);
		
		String elder = "";
		if(!tribeManager.getElder(tribe).isEmpty()) {
			elder = Bukkit.getServer().getOfflinePlayer(UUID.fromString(tribeManager.getElder(tribe))).getName();
		} else {
			elder = "NONE";
		}

		List<String> tribeMembers = tribeManager.getTribeMembers(tribe);
		
		Material[] levelItems = {Material.COAL, Material.COPPER_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT, Material.REDSTONE
				, Material.LAPIS_LAZULI, Material.EMERALD, Material.DIAMOND, Material.NETHERITE_INGOT, Material.NETHER_STAR};
		
		if(requiredSponges != -1) {
			inv.setItem(4, createGuiItem(levelItems[level-1], mainClass.tribalGames.toString() + ChatColor.BOLD + tribe.toUpperCase(), 
					ChatColor.GRAY + "Date Founded: " + ChatColor.WHITE + dateCreated, 
					ChatColor.GRAY + "Level: " + ChatColor.WHITE + level,
					ChatColor.GRAY + "Vault: " + ChatColor.WHITE + vault,
					ChatColor.GRAY + "Cost to upgrade: " + mainClass.spongeColor + requiredSponges,
					"",
					ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "Scores:",
					ChatColor.YELLOW + "Rating: " + ratingScore,
					ChatColor.GREEN + "Economy Score: " + economyScore,
					ChatColor.LIGHT_PURPLE + "Power Score: " + powerScore));
		} else {
			inv.setItem(4, createGuiItem(levelItems[level-1], mainClass.tribalGames.toString() + ChatColor.BOLD + tribe.toUpperCase(), 
					ChatColor.GRAY + "Date Founded: " + ChatColor.WHITE + dateCreated, 
					ChatColor.GRAY + "Level: " + ChatColor.WHITE + level,
					ChatColor.GRAY + "Vault: " + ChatColor.WHITE + vault,
					ChatColor.GRAY + "Cost to upgrade: " + mainClass.spongeColor + "MAX LEVEL",
					"",
					ChatColor.YELLOW + "Rating: " + ratingScore,
					ChatColor.DARK_GREEN + "Economy: " + economyScore,
					ChatColor.DARK_PURPLE + "Power Score: " + powerScore));
		}
		
		String status;
		if(warpManager.compoundExists(tribe)) {
			status = ChatColor.GREEN + "ACTIVE";
		} else {
			status = ChatColor.RED + "INACTIVE";
		}
		
		inv.setItem(19, createGuiItem(Material.COMPASS, ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "TRIBE COMPOUND",
				ChatColor.GRAY + "Click to Warp",
				"",
				ChatColor.GRAY + "Status: " + status));
		
		inv.setItem(21, createGuiItem(Material.DIAMOND_HELMET, ChatColor.GOLD.toString() + ChatColor.BOLD + "TRIBE LEADERSHIP", 
				ChatColor.GRAY + "Chief: " + chief,
				ChatColor.GRAY + "Elder: " + elder));
		
		inv.setItem(23, createGuiItem(Material.SLIME_BALL, mainClass.tribalGames.toString() + ChatColor.BOLD + "TRIBAL GAMES",
				ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + "Wins:",
				ChatColor.GRAY + "CTF: " + tribeManager.getCTFWins(tribe),
				ChatColor.GRAY + "KOTH: " + 0,
				ChatColor.GRAY + "TOTT: " + 0));
		
		inv.setItem(25, createGuiItem(Material.SPONGE, mainClass.spongeColor.toString() + ChatColor.BOLD + "TRIBE SHOP", 
				ChatColor.DARK_PURPLE+ "Coming Soon..."));
		
		inv.setItem(8, createGuiItem(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD + "EXIT", 
				ChatColor.GRAY + "Click to exit"));
		
		String[] memberArray = tribeMembers.toArray(new String[0]);
		
		getTribeMemberSkull(38, chief, ChatColor.GOLD + chief);
		int counter = 1;
		for(int i = 39; i < 53; i++) {
			
			if(i == 43) {
				i = 46;
			}
			
			if(counter < memberArray.length) {
				String memberIGN = Bukkit.getServer().getOfflinePlayer(UUID.fromString(memberArray[counter])).getName();
				getTribeMemberSkull(i, memberIGN, ChatColor.GOLD + memberIGN);
				//compare the counter value and level
			} else if(counter < tribeManager.getLevel(tribe)+2){
				getTribeMemberSkull(i, "Trajan", ChatColor.GREEN + "EMPTY");
			} else {
				getTribeMemberSkull(i, "MHF_Redstone", ChatColor.RED+ "LOCKED", ChatColor.RED + "Unlock at level " + (counter-1));
			}
			
			counter++;
		}
	}
	
    public static void getTribeMemberSkull(int i, String IGN, String displayName, final String...lore) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(IGN);
		meta.setDisplayName(displayName);
		meta.setLore(Arrays.asList(lore));
		skull.setItemMeta(meta);
		
    	inv.setItem(i, createGuiSkull(IGN, displayName, lore));
    }
	
	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
    protected static ItemStack createGuiSkull(final String IGN, final String displayName, final String... lore) {
    	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    	final ItemMeta meta = skull.getItemMeta();
    	
    	((SkullMeta) meta).setOwner(IGN);
    	
    	meta.setDisplayName(displayName);
    	
    	meta.setLore(Arrays.asList(lore));
    	
    	skull.setItemMeta(meta);
    	
    	return skull;
    }
	
	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if(!event.getInventory().equals(inv)) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		final Player p = (Player) event.getWhoClicked();
		String inventoryTitle = ChatColor.stripColor(event.getView().getTitle());
		String playerTribe = tribeManager.getPlayerTribe(p);
		
		if(clickedItem.getType() == Material.COMPASS) {
			warpManager.warpPlayer(playerTribe, p);
			p.closeInventory();
			
//			if(inventoryTitle.equals(playerTribe.toUpperCase() + " Tribe Profile")) {
//				inv = Bukkit.createInventory(null, 9, mainClass.tribesColor.toString() + ChatColor.BOLD + playerTribe.toUpperCase() + " Warps");
//				openInventory(p);
//				
//				List<String> tribeWarps = warpManager.getWarpList(playerTribe, p);
//				String[] tribeWarpArray = tribeWarps.toArray(new String[0]);
//				
//				inv.setItem(8, createGuiItem(Material.BARRIER, ChatColor.RED.toString() + ChatColor.BOLD + "EXIT", 
//						ChatColor.GRAY + "Click to exit"));
//				
//				int[] unlockLevels = {1,4,7,10};
//				
//				int counter = 0;
//				for(int i = 2; i < 6; i++) {
//					if(counter < tribeWarpArray.length) {
//						inv.setItem(i, createGuiItem(Material.COMPASS, ChatColor.GOLD + tribeWarpArray[counter], 
//								ChatColor.GRAY + "Click to warp"));
//					} else if(counter < tribeManager.getLevel(playerTribe) && tribeManager.getLevel(playerTribe) >= unlockLevels[counter]) {
//						getTribeMemberSkull(i, "Trajan", ChatColor.GREEN + "EMPTY");
//					} else {
//						getTribeMemberSkull(i, "MHF_Redstone", ChatColor.RED+ "LOCKED", ChatColor.RED + "Unlock at level " + unlockLevels[counter]);
//					}
//					
//					counter++;
//				}
//			} else if(inventoryTitle.contains(ChatColor.stripColor("Warps"))){
//				String warp = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
//				p.performCommand("tribes warp " + warp);
//			}
		}
		
		if(clickedItem.getType() == Material.BARRIER) {
			if(inventoryTitle.contains("Tribe Profile")) {
				p.closeInventory();
			} else {
	        	inv = Bukkit.createInventory(null, 54, mainClass.tribalGames.toString() + ChatColor.BOLD + playerTribe.toUpperCase() + " Tribe Profile");
	        	openInventory(p);
	        	initializeItems(p, playerTribe);
			}
		}
		
		//maybe add join function
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inv)) {
			event.setCancelled(true);
		}
	}
	
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e, Player p) {
    	HandlerList.unregisterAll(this);
    	Bukkit.getServer().getScheduler().runTaskLater(Main.getPlugin(Main.class), p::updateInventory, 1L);
    }
}
