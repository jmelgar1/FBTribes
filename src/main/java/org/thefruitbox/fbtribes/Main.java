package org.thefruitbox.fbtribes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.thefruitbox.fbtribes.commands.CommandManager;
import org.thefruitbox.fbtribes.commands.beginCTF;
import org.thefruitbox.fbtribes.commands.claimspongesCommand;
import org.thefruitbox.fbtribes.commands.subcommands.infoCommand;
import org.thefruitbox.fbtribes.commands.subcommands.admin.addspongesCommand;
import org.thefruitbox.fbtribes.commands.subcommands.admin.setspongesCommand;
import org.thefruitbox.fbtribes.events.BreakRareOre;
import org.thefruitbox.fbtribes.events.CatchTreasure;
import org.thefruitbox.fbtribes.events.KillEvent;
import org.thefruitbox.fbtribes.runnables.CheckForUnclaimed;
import org.thefruitbox.fbtribes.tribalgames.events.ProtectionEvents;
import org.thefruitbox.fbtribes.tribalgames.runnables.ctf1.CTF1Countdown;

import com.google.gson.*;

public class Main extends JavaPlugin implements Listener {

    //Main instance
    private static Main instance;

    //player data file
    private File tribesFile;
    private FileConfiguration tribes;

    //unclaimed rewards file
    private File rewardsFile;
    private FileConfiguration rewards;

    //tribe prices file
    private File pricesFile;
    private FileConfiguration prices;

    //tribe prices file
    private File ctfFile;
    private FileConfiguration ctf;

    //player data file
    private File tribesFileJson;
    private JsonObject tribesJson;

    @Override
    public void onEnable() {
        System.out.println("FBTribes Enabled!");

        instance = this;

        getCommand("tribes").setExecutor(new CommandManager());
        getCommand("claimsponges").setExecutor(new claimspongesCommand());
        getCommand("setsponges").setExecutor(new setspongesCommand());
        getCommand("addsponges").setExecutor(new addspongesCommand());
        getCommand("beginctf").setExecutor(new beginCTF());

        createTribesFile();
        createRewardsFile();
        createPricesFile();
        createCTFFile();

        createTribesFileJson();

        getServer().getPluginManager().registerEvents(new BreakRareOre(), this);
        getServer().getPluginManager().registerEvents(new CatchTreasure(), this);
        getServer().getPluginManager().registerEvents(new KillEvent(), this);

        getServer().getPluginManager().registerEvents(new CTF1Countdown(), this);
        getServer().getPluginManager().registerEvents(new ProtectionEvents(), this);

        getServer().getPluginManager().registerEvents(new infoCommand(), this);

        getCTF().set("event", false);

        CheckForUnclaimed checkForUnclaimed = new CheckForUnclaimed();
        checkForUnclaimed.runTaskTimer(this, 0L, 12000);
    }

    public void onDisable() {
        System.out.println("FBTribes Disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    //PLAYER DATA FILE
    public void saveTribesFile() {
        try {
            tribes.save(tribesFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save tribes.yml");
        }
    }

    public FileConfiguration getTribes() {
        return this.tribes;
    }

    private void createTribesFile() {
        tribesFile = new File(getDataFolder(), "tribes.yml");
        if(!tribesFile.exists()) {
            tribesFile.getParentFile().mkdirs();
            saveResource("tribes.yml", false);
            System.out.println("(!) tribes.yml created");
        }

        tribes = new YamlConfiguration();
        try {
            tribes.load(tribesFile);
            System.out.println("(!) tribes.yml loaded");
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    //REWARDS FILE
    public void saveRewardsFile() {
        try {
            rewards.save(rewardsFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save unclaimedRewards.yml");
        }
    }

    public FileConfiguration getRewards() {
        return this.rewards;
    }

    private void createRewardsFile() {
        rewardsFile = new File(getDataFolder(), "unclaimedRewards.yml");
        if(!rewardsFile.exists()) {
            rewardsFile.getParentFile().mkdirs();
            saveResource("unclaimedRewards.yml", false);
            System.out.println("(!) unclaimedRewards.yml created");
        }

        rewards = new YamlConfiguration();
        try {
            rewards.load(rewardsFile);
            System.out.println("(!) unclaimedRewards.yml loaded");
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    //prices file
    public void savePricesFile() {
        try {
            prices.save(pricesFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save prices.yml");
        }
    }

    public FileConfiguration getPrices() {
        return this.prices;
    }

    private void createPricesFile() {
        pricesFile = new File(getDataFolder(), "prices.yml");
        if(!pricesFile.exists()) {
            pricesFile.getParentFile().mkdirs();
            saveResource("prices.yml", false);
            System.out.println("(!) prices.yml created");
        }

        prices = new YamlConfiguration();
        try {
            prices.load(pricesFile);
            System.out.println("(!) prices.yml loaded");
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    //ctf file
    public void saveCTFFile() {
        try {
            ctf.save(ctfFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save ctf.yml");
        }
    }

    public FileConfiguration getCTF() {
        return this.ctf;
    }

    public void createCTFFile() {
        ctfFile = new File(getDataFolder(), "ctf.yml");
        if(!ctfFile.exists()) {
            ctfFile.getParentFile().mkdirs();
            saveResource("ctf.yml", false);
            System.out.println("(!) ctf.yml created");
        }

        ctf = new YamlConfiguration();
        try {
            ctf.load(ctfFile);
            System.out.println("(!) ctf.yml loaded");
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createTribesFileJson() {
        tribesFileJson = new File(getDataFolder(), "tribes_json.json");

        if (!tribesFileJson.exists()) {
            tribesFileJson.getParentFile().mkdirs();

            try {
                tribesFileJson.createNewFile();
                saveResource("tribes_json.json", false);
                System.out.println("(!) tribe_json.json created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tribesJson = new JsonObject();
        try {
            FileReader reader = new FileReader(tribesFileJson);
            tribesJson = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println("(!) tribes_json.json loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTribesFileJson() {
        try {
            FileWriter writer = new FileWriter(tribesFileJson);
            new GsonBuilder().setPrettyPrinting().create().toJson(tribesJson, writer);
            writer.close();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save tribes_json.json");
            e.printStackTrace();
        }
    }

    public JsonObject getTribesJson() {
        return this.tribesJson;
    }
}
