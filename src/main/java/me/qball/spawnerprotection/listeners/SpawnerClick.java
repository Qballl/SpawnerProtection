package me.qball.spawnerprotection.listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerFile;
import me.qball.spawnerprotection.utils.SpawnerType;
import me.qball.spawnerprotection.utils.Utils;
import me.qball.spawnerprotection.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SpawnerClick implements Listener {
    public static final HashMap<UUID, CreatureSpawner> spawner = new HashMap<>();
    private final SpawnerProtection spawnerProtection;

    public SpawnerClick(SpawnerProtection spawnerProtection) {
        this.spawnerProtection = spawnerProtection;
    }

    @EventHandler
    public void onSpawnerClick(PlayerInteractEvent e) {
        SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getState() instanceof CreatureSpawner) {
            if (e.getItem() != null) {
                if (e.getItem().getType().toString().contains("MONSTER_EGG") || e.getItem().getType().toString().contains("MONSTER_EGGS") ||
                        e.getItem().getType().name().contains("spawn")) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Utils.toColor(spawnerProtection.getConfig().getString("SpawnerChangeMsg")));
                }
            } else if (spawnerFile.getSpawner(e.getPlayer().getUniqueId(), e.getClickedBlock().getLocation()) || e.getPlayer().hasPermission("spawnerprotection.protect.admin")) {
                e.setCancelled(true);
                CreatureSpawner creatureSpawner = (CreatureSpawner) e.getClickedBlock().getState();
                spawner.put(e.getPlayer().getUniqueId(), (CreatureSpawner) e.getClickedBlock().getState());
                Inventory spawnerMan = Bukkit.createInventory(e.getPlayer(), 9, "Spawner Management");
                ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta meta = star.getItemMeta();
                meta.setDisplayName("Spawner Info");
                ArrayList<String> spawnerInfo = new ArrayList<>();
                String owner = "";
                if (!spawnerFile.lookUpSpawner(creatureSpawner.getLocation()))
                    owner = "No Owner";
                else
                    owner = spawnerFile.getSpawner(e.getClickedBlock().getLocation()).getDisplayName();
                spawnerInfo.add(0, "Owner: " + owner);
                spawnerInfo.add(1, "Type: " + SpawnerType.findName(creatureSpawner.getSpawnedType().name()));
                meta.setLore(spawnerInfo);
                star.setItemMeta(meta);
                String[] tmp = Bukkit.getVersion().split("MC: ");
                Version version = Version.getVersion(tmp[1]);
                if(!version.getId().equalsIgnoreCase("1.13")) {
                    ItemStack glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (byte) 5);
                    ItemMeta greenGlass = glass.getItemMeta();
                    greenGlass.setDisplayName("Pickup spawner");
                    glass.setItemMeta(greenGlass);
                    ItemStack redGlass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (byte) 14);
                    ItemMeta redGlassMeta = redGlass.getItemMeta();
                    redGlassMeta.setDisplayName("Cancel");
                    redGlass.setItemMeta(redGlassMeta);
                    spawnerMan.setItem(0, star);
                    spawnerMan.setItem(2, glass);
                    spawnerMan.setItem(8, redGlass);
                }else{
                    ItemStack glass = new ItemStack(Material.valueOf("GREEN_STAINED_GLASS_PANE"), 1, (byte) 5);
                    ItemMeta greenGlass = glass.getItemMeta();
                    greenGlass.setDisplayName("Pickup spawner");
                    glass.setItemMeta(greenGlass);
                    ItemStack redGlass = new ItemStack(Material.valueOf("RED_STAINED_GLASS_PANE"), 1, (byte) 14);
                    ItemMeta redGlassMeta = redGlass.getItemMeta();
                    redGlassMeta.setDisplayName("Cancel");
                    redGlass.setItemMeta(redGlassMeta);
                    spawnerMan.setItem(0, star);
                    spawnerMan.setItem(2, glass);
                    spawnerMan.setItem(8, redGlass);
                }
                e.getPlayer().openInventory(spawnerMan);
            } else {
                String notYourClick = spawnerProtection.getConfig().getString("NotYourSpawnerClick");
                if (!spawnerFile.lookUpSpawner((e.getClickedBlock().getState().getLocation())))
                    e.getPlayer().sendMessage(Utils.toColor(spawnerProtection.getConfig().getString("NoOwnerClick")));
                else {
                    notYourClick = notYourClick.replaceAll("\\{owner}", spawnerFile.getSpawner(e.getClickedBlock().getLocation()).getDisplayName());
                    e.getPlayer().sendMessage(Utils.toColor(notYourClick));
                }
            }
        }
    }

}
