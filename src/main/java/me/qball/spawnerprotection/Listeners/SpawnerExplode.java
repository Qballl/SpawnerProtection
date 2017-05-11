package me.qball.spawnerprotection.Listeners;


import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerFile;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;

public class SpawnerExplode implements Listener {
    private SpawnerProtection spawnerProtection;
    public SpawnerExplode(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        Iterator<Block> iterator = e.blockList().iterator();
        SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.getState() instanceof CreatureSpawner && spawnerProtection.getConfig().getBoolean("PreventSpawnerExplosion")) {
               if(!spawnerProtection.getConfig().getBoolean("PreventNaturalSpawnerExplosion")){
                   if(spawnerFile.lookUpSpawner(block.getLocation()))
                       iterator.remove();
               }
               else
                iterator.remove();
            }
        }
    }
}
