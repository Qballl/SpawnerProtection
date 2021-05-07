package me.qball.spawnerprotection;

import me.qball.spawnerprotection.commands.SpawnerTab;
import me.qball.spawnerprotection.commands.SpawnersCommand;
import me.qball.spawnerprotection.listeners.*;
import me.qball.spawnerprotection.utils.SpawnerFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.stream.Stream;

public final class SpawnerProtection extends JavaPlugin {

    private static SpawnerProtection instance;
    private static Economy economy;
    private SpawnerFile spawnerFile;

    public static SpawnerProtection getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        instance = this;

        registerListeners();

        getConfig().options().copyDefaults(true);
        saveConfig();

        getCommand("spawner").setExecutor(new SpawnersCommand(this));
        getCommand("spawner").setTabCompleter(new SpawnerTab(this));

        spawnerFile = new SpawnerFile(this);
        spawnerFile.createFile();

        if (!setupEconomy()) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        spawnerFile.saveFile();
    }

    public SpawnerFile getSpawnerFile() {
        return spawnerFile;
    }

    private void registerListeners() {
        Stream.of(
                new SpawnerClick(this),
                new SpawnerPlace(this),
                new SpawnerManagementGUIClick(this),
                new SpawnerBreak(this),
                new ShopClick(this),
                new BlockPlace(this),
                new SpawnerExplode(this)
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        economy = rsp.getProvider();
        return economy != null;
    }

}
