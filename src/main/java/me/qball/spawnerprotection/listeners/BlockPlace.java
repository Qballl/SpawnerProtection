package me.qball.spawnerprotection.listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerFile;
import me.qball.spawnerprotection.utils.SpawnerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BlockPlace implements Listener {
    private SpawnerProtection spawnerProtection;

    public BlockPlace(SpawnerProtection spawnerProtection) {
        this.spawnerProtection = spawnerProtection;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlockAgainst().getState() instanceof CreatureSpawner && !e.getPlayer().isSneaking()) {
            SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
            if (spawnerFile.getSpawner(e.getPlayer().getUniqueId(), e.getBlockAgainst().getLocation()) || e.getPlayer().hasPermission("spawnerprotection.protect.admin")) {
                CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlockAgainst().getState();
                SpawnerClick.spawner.put(e.getPlayer().getUniqueId(), creatureSpawner);
                Inventory spawnerMan = Bukkit.createInventory(e.getPlayer(), 9, "Spawner Management");
                ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta meta = star.getItemMeta();
                meta.setDisplayName("Spawner Info");
                ArrayList<String> spawnerInfo = new ArrayList<>();
                String owner;
                if (!spawnerFile.lookUpSpawner(creatureSpawner.getLocation()))
                    owner = "No Owner";
                else
                    owner = spawnerFile.getSpawner(e.getBlockAgainst().getLocation()).getDisplayName();
                spawnerInfo.add(0, "Owner: " + owner);
                spawnerInfo.add(1, "Type: " + SpawnerType.findName(creatureSpawner.getSpawnedType().name()));
                meta.setLore(spawnerInfo);
                star.setItemMeta(meta);
                ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
                ItemMeta greenGlass = glass.getItemMeta();
                greenGlass.setDisplayName("Pickup spawner");
                glass.setItemMeta(greenGlass);
                ItemStack redGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta redGlassMeta = redGlass.getItemMeta();
                redGlassMeta.setDisplayName("Cancel");
                redGlass.setItemMeta(redGlassMeta);
                spawnerMan.setItem(0, star);
                spawnerMan.setItem(2, glass);
                spawnerMan.setItem(8, redGlass);
                e.getPlayer().openInventory(spawnerMan);
                e.setCancelled(true);
            }
        }
    }
}
