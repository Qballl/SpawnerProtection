package me.qball.spawnerprotection;

import me.qball.spawnerprotection.Commands.SpawnerTab;
import me.qball.spawnerprotection.Commands.SpawnersCommand;
import me.qball.spawnerprotection.Listeners.*;
import me.qball.spawnerprotection.Utils.SpawnerFile;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class SpawnerProtection extends JavaPlugin implements Listener {
    private static Economy econ = null;
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new SpawnerClick(this),this);
        this.getServer().getPluginManager().registerEvents(new SpawnerPlace(this),this);
        this.getServer().getPluginManager().registerEvents(new SpawnerManagementGUIClick(this),this);
        this.getServer().getPluginManager().registerEvents(new SpawnerBreak(this),this);
        this.getServer().getPluginManager().registerEvents(new ShopClick(this),this);
        this.getServer().getPluginManager().registerEvents(new BlockPlace(this),this);
        this.getServer().getPluginManager().registerEvents(new SpawnerExplode(this),this);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("Spawners").setExecutor(new SpawnersCommand(this));
        this.getCommand("Spawners").setTabCompleter(new SpawnerTab(this));
        SpawnerFile spawnerFile = new SpawnerFile(this);
        spawnerFile.createFile();
        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        SpawnerFile spawnerFile = new SpawnerFile(this);
        spawnerFile.saveFile();
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEcon(){return econ;}
    public static String convertToString(Location location){
        return location.getWorld().getName() + "," + location.getBlockX() + "," +location.getBlockY()+","+location.getBlockZ();
    }
    public static SpawnerTypes[] getAvailableMobs(){
        ArrayList<SpawnerTypes> types  = new ArrayList<>();
        String[] tmp = Bukkit.getVersion().split("MC: ");
        if(tmp[1].contains("1.8")){
            for(int i = 0; i<=SpawnerTypes.values().length-5;i++)
                types.add(SpawnerTypes.values()[i]);
        }else if(tmp[1].contains("1.9")){
            for(int i = 0; i<=SpawnerTypes.values().length-4;i++)
                types.add(SpawnerTypes.values()[i]);
        }else if(tmp[1].contains("1.10")){
            for(int i = 0; i<=SpawnerTypes.values().length-3;i++)
                types.add(SpawnerTypes.values()[i]);
        }else if(tmp[1].contains("1.11")){
            for(int i = 0; i<=SpawnerTypes.values().length-2;i++)
                types.add(SpawnerTypes.values()[i]);
        }else if(tmp[1].contains("1.12")){
            for(int i = 0; i<=SpawnerTypes.values().length-1;i++)
                types.add(SpawnerTypes.values()[i]);
        }
      return types.toArray(new SpawnerTypes[types.size()]);
    }

}
