package org.thefruitbox.fbtribes.commands.subcommands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.thefruitbox.fbtribes.Main;
import org.thefruitbox.fbtribes.commands.SubCommand;
import org.thefruitbox.fbtribes.managers.TribeManager;
import org.thefruitbox.fbtribes.utilities.ChatUtilities;
import org.thefruitbox.fbtribes.utilities.GeneralUtilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class listCommand extends SubCommand {
	
	//Main instance
	private Main mainClass = Main.getInstance();

	private final ChatUtilities cu = new ChatUtilities();
			
	TribeManager tribeManager = new TribeManager();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "list";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "List all active tribes";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/tribes list [page #]";
	}

	@Override
	public void perform(Player p, String[] args) {

		//Slow process so run asynchronously
		Bukkit.getScheduler().runTaskAsynchronously(mainClass, new Runnable()
		{
			@Override
			public void run()
			{
				int pageNum = 0;
				int firstEntry = 0;
				int lastEntry = 0;

				if(args.length == 1) {
					pageNum = 1;
					firstEntry = 1;
					lastEntry = 10;
				} else if(args.length == 2) {
					pageNum = Integer.parseInt(args[1]);
					firstEntry = (pageNum*10)-9;
					lastEntry = (pageNum*10);
				}

				Map<String, Double> sortedList = GeneralUtilities.sortByComparator(tribeManager.generateLeaderboardJson(), false);
				System.out.println(sortedList);
				int count = 1;
				int lastPageNum = (int)Math.ceil((sortedList.size()/10.0));
				if(firstEntry <= sortedList.size()) {
					p.sendMessage(ChatColor.DARK_AQUA + "Tribes:");

					for(Map.Entry<String, Double> entry : sortedList.entrySet()) {
						if(count >= firstEntry && count <= lastEntry) {
							String tribeName = entry.getKey();
							Double powerScore = entry.getValue();

							TextComponent tribeInfo = new TextComponent(cu.lightGreen.toString() + count + ". " +
									cu.lighterGreen.toString() + tribeName + ChatColor.DARK_PURPLE + " ["
									+ ChatColor.LIGHT_PURPLE + powerScore + ChatColor.DARK_PURPLE + "]");
							tribeInfo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tribes info " + tribeName));
							tribeInfo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View " + tribeName)));
							p.spigot().sendMessage(tribeInfo);
						}
						count++;
					}

					if(lastPageNum > pageNum) {
						TextComponent nextPage = new TextComponent(ChatColor.GREEN + "Next Page >>>");
						nextPage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tribes list " + (pageNum+1)));
						nextPage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to view next page")));
						p.spigot().sendMessage(nextPage);
					}

				} else {
					p.sendMessage(ChatColor.RED + "Last page number is " + (int)Math.ceil((sortedList.size()/10.0)));
				}
			}
		});
	}
}
