package org.thefruitbox.fbtribes.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.managers.SpongeManager;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;

public class CheckForUnclaimed extends BukkitRunnable {

    //Main instance
    private Main mainClass = Main.getInstance();

    private final ChatUtilities cu = new ChatUtilities();

    SpongeManager spongeManager = new SpongeManager();

    @Override
    public void run() {

        System.out.println("Checking for unclaimed");

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(spongeManager.checkForUnclaimed(p) != 0) {
                int amount = spongeManager.checkForUnclaimed(p);
                p.sendMessage(cu.spongeColor + "You have " + amount + " unclaimed sponges! /claimsponges");
            }
        }
    }
}
