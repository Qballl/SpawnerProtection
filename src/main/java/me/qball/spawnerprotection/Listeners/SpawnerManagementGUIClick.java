package me.qball.spawnerprotection.Listeners;

import me.qball.spawnerprotection.Utils.SpawnerFile;
import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SpawnerManagementGUIClick implements Listener{
    private SpawnerProtection spawnerProtection;
    public SpawnerManagementGUIClick(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() ==null || !e.getInventory().getName().equals("Spawner Management"))
            return;
        if(e.getInventory() == null)
            return;
        if(!e.getCurrentItem().hasItemMeta())
            return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("Pickup spawner")){
            e.setCancelled(true);
            CreatureSpawner creatureSpawner = SpawnerClick.spawner.get(e.getWhoClicked().getUniqueId());
            ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
            ItemMeta meta = spawner.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            String mob = creatureSpawner.getSpawnedType().name().toLowerCase();
            for(SpawnerTypes type : SpawnerProtection.getAvailableMobs()) {
                if (type.getType().equalsIgnoreCase(mob))
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',spawnerProtection.getConfig().getString("SpawnerNameFormat"))+
                            type.getDisplayName() + " Spawner");
            }
            lore.add(0,SpawnerTypes.findName(mob));
            meta.setLore(lore);
            spawner.setItemMeta(meta);
            e.getWhoClicked().getInventory().addItem(spawner);
            SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
            spawnerFile.removeSpawner(SpawnerClick.spawner.get(e.getWhoClicked().getUniqueId()).getLocation());
            e.getWhoClicked().closeInventory();
            creatureSpawner.getBlock().setType(Material.AIR);
        }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("Cancel")){
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("Spawner Info"))
            e.setCancelled(true);
        else
            e.setCancelled(true);
    }
}
