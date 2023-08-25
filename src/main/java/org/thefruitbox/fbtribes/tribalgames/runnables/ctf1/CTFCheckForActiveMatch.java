package org.thefruitbox.fbtribes.tribalgames.runnables.ctf1;

import org.bukkit.scheduler.BukkitRunnable;
import org.thefruitbox.fbtribes.tribalgames.managers.CTF1Manager;

public class CTFCheckForActiveMatch extends BukkitRunnable implements CTF1Manager {

	@Override
	public void run() {
		boolean activeMatch = mainClass.getCTF().getBoolean("event");
		
		if(activeMatch == true) {
			CTF1EndEvent ctf1endevent = new CTF1EndEvent();
			ctf1endevent.run();
			
			System.out.println(mainClass.tgPrefix + "Ending active match from console...");
		}
	}

}
