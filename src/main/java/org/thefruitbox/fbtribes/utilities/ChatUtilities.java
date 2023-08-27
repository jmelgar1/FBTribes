package org.thefruitbox.fbtribes.utilities;

import org.bukkit.ChatColor;
import org.thefruitbox.fbtribes.Main;

public class ChatUtilities {

    public net.md_5.bungee.api.ChatColor tribesColor = net.md_5.bungee.api.ChatColor.of("#33F24D");
    public net.md_5.bungee.api.ChatColor lightGreen = net.md_5.bungee.api.ChatColor.of("#95bf56");
    public net.md_5.bungee.api.ChatColor lighterGreen = net.md_5.bungee.api.ChatColor.of("#b4ba82");
    public net.md_5.bungee.api.ChatColor spongeColor = net.md_5.bungee.api.ChatColor.of("#dfff00");
    public net.md_5.bungee.api.ChatColor tribalGames = net.md_5.bungee.api.ChatColor.of("#47b347");

    public String tgPrefix = tribalGames.toString() + net.md_5.bungee.api.ChatColor.BOLD + "TRIBAL GAMES: ";

    public String tribesPrefix = tribesColor + ChatColor.BOLD.toString() + "TRIBES: ";

    public String sendError(String message) {
        return ChatColor.GRAY + "ERROR: " + message;
    }

    public String sendSuccess(String message){
        return ChatColor.GREEN + "SUCCESS: " + message;
    }

    public String sendMajorError(String message) {
        return ChatColor.RED + "ERROR: " + ChatColor.RED + message;
    }
}
