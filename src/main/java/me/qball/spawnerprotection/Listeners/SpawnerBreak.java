package me.qball.spawnerprotection.Listeners;

import me.qball.spawnerprotection.Utils.SpawnerFile;
import me.qball.spawnerprotection.SpawnerProtection;
import org.bukkit.ChatColor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class SpawnerBreak implements Listener {
    private final SpawnerProtection spawnerProtection;
    public SpawnerBreak(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e){
        SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
        if(e.getBlock().getState() instanceof CreatureSpawner) {
            if (spawnerFile.getSpawner(e.getPlayer().getUniqueId(), e.getBlock().getLocation())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',spawnerProtection.getConfig().getString("UseGui")));
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',spawnerProtection.getConfig().getString("NotYourSpawner")));
            }
        }
    }
}
