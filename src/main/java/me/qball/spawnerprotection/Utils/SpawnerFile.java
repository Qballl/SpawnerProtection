package me.qball.spawnerprotection.Utils;


import me.qball.spawnerprotection.SpawnerProtection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SpawnerFile {
    private final File file;
    private YamlConfiguration spawners;
    public SpawnerFile(SpawnerProtection spawnerProtection){
        file = new File(spawnerProtection.getDataFolder()+"/Spawners.yml");
    }

    public void createFile(){
        if(!file.exists()){
            try{
                file.createNewFile();
                spawners = YamlConfiguration.loadConfiguration(file);
                spawners.createSection("Spawners");
                spawners.save(file);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            spawners = YamlConfiguration.loadConfiguration(file);
            if(spawners.getConfigurationSection("Spawners")==null){
                spawners.createSection("Spawners");
                try {
                    spawners.save(file);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void saveFile(){
        try {
            if(spawners==null)
                spawners = YamlConfiguration.loadConfiguration(file);
            spawners.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveSpawner(UUID id, Location loc){
        spawners = YamlConfiguration.loadConfiguration(file);
        String location = SpawnerProtection.convertToString(loc);
        spawners.set("Spawners."+ location, id.toString());
        saveFile();
    }
    public boolean getSpawner(UUID id,Location loc){
            spawners = YamlConfiguration.loadConfiguration(file);
            for (String location : spawners.getConfigurationSection("Spawners").getKeys(false)) {
                String spawnerLocation = SpawnerProtection.convertToString(loc);
                if (spawnerLocation.equals(location)) {
                    if (spawners.get("Spawners."+location).equals(id.toString())) {
                        return true;
                    }
                }
            }

        return false;
    }

    public Player getSpawner(Location loc){
        spawners = YamlConfiguration.loadConfiguration(file);
        for (String location : spawners.getConfigurationSection("Spawners").getKeys(false)) {
            String spawnerLocation = SpawnerProtection.convertToString(loc);
            if (spawnerLocation.equals(location)) {
                String uuid = String.valueOf(spawners.get("Spawners."+location));
                return Bukkit.getPlayer(UUID.fromString(uuid));
            }
        }
        return null;
    }
    public boolean lookUpSpawner(Location loc){
        spawners = YamlConfiguration.loadConfiguration(file);
        for(String location : spawners.getConfigurationSection("Spawners").getKeys(false)){
            String spawnerLocation = SpawnerProtection.convertToString(loc);
            if(spawnerLocation.equals(location))
                return true;
        }
        return false;
    }

    public void removeSpawner(Location loc){
        spawners = YamlConfiguration.loadConfiguration(file);
        for(String location : spawners.getConfigurationSection("Spawners").getKeys(false)){
            String spawnerLocation = SpawnerProtection.convertToString(loc);
            if(location.equals(spawnerLocation)){
                spawners.set("Spawners."+location,null);
                saveFile();
                return;
            }
        }
    }
}
