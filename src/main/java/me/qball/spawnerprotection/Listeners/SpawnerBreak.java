package me.qball.spawnerprotection.Listeners;

import me.qball.spawnerprotection.Utils.SpawnerFile;
import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class SpawnerBreak implements Listener {
    private final SpawnerProtection spawnerProtection;
    public SpawnerBreak(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e){
        SpawnerFile spawnerFile = new SpawnerFile(spawnerProtection);
        if(e.getBlock().getState() instanceof CreatureSpawner) {
            if (spawnerFile.getSpawner(e.getPlayer().getUniqueId(), e.getBlock().getLocation())){
                    if(!spawnerProtection.getConfig().getBoolean("SilkSpawner")) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("UseGui")));
                    }else if(e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)||
                            e.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)){
                    CreatureSpawner creatureSpawner = (CreatureSpawner)e.getBlock().getState();
                    e.getPlayer().getInventory().addItem(spawnerProtection.makeSpawner(creatureSpawner));
                    spawnerFile.removeSpawner(e.getBlock().getLocation());
                }
            } else {
                if(!spawnerFile.lookUpSpawner(e.getBlock().getLocation())&&
                        spawnerProtection.getConfig().getBoolean("SilkNaturalSpawner")){
                    if(e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)||
                            e.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)){
                        CreatureSpawner creatureSpawner = (CreatureSpawner)e.getBlock().getState();
                        e.getPlayer().getInventory().addItem(spawnerProtection.makeSpawner(creatureSpawner));
                    }
                }else{
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("NotYourSpawner")));
                }
            }
        }
    }
}
