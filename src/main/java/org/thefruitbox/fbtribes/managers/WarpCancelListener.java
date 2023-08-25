package org.thefruitbox.fbtribes.managers;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WarpCancelListener implements Listener {
    private final Player player;
    private final BukkitRunnable warpTask;
    private final List<Player> inWarp;

    public WarpCancelListener(Player player, BukkitRunnable warpTask, List<Player> inWarp) {
        this.player = player;
        this.warpTask = warpTask;
        this.inWarp = inWarp;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().equals(player)) {
            // Check if the player's head position has changed
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            // Ignore head movements
            if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
                return;
            }

            // Player has moved their body, cancel the warp
            player.sendMessage(ChatColor.RED + "Warp cancelled... please stand still when warping!");
            inWarp.remove(player);
            warpTask.cancel();
            HandlerList.unregisterAll(this);
           }
       }
   }

