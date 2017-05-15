package me.qball.spawnerprotection.Listeners;

import me.qball.spawnerprotection.Utils.SpawnerFile;
import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.EntityType;

public class SpawnerPlace implements Listener {
    private SpawnerProtection spawnerProtection;
    public SpawnerPlace(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent e){
        if(e.getBlock().getState() instanceof CreatureSpawner) {
                CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                String entity = e.getItemInHand().getItemMeta().getLore().get(0).toUpperCase();
                SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
                spawnerFile.saveSpawner(e.getPlayer().getUniqueId(), e.getBlock().getLocation());
                spawner.setSpawnedType(EntityType.valueOf(SpawnerTypes.valueOf(entity).getType().toUpperCase()));

            }

    }
}
