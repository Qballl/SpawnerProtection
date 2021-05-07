package me.qball.spawnerprotection.listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerFile;
import me.qball.spawnerprotection.utils.SpawnerType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerPlace implements Listener {

    private final SpawnerProtection plugin;

    public SpawnerPlace(SpawnerProtection plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent event) {
        if (event.getBlock().getState() instanceof CreatureSpawner) {
            CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            String entity = event.getItemInHand().getItemMeta().getLore().get(0).toUpperCase();
            SpawnerFile spawnerFile = plugin.getSpawnerFile();
            spawnerFile.saveSpawner(event.getPlayer().getUniqueId(), event.getBlock().getLocation());
            spawner.setSpawnedType(EntityType.valueOf(SpawnerType.valueOf(entity).getType().toUpperCase()));
            spawner.update();
        }
    }

}
