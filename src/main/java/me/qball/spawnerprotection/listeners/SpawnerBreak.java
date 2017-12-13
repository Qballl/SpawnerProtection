package me.qball.spawnerprotection.listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerFile;
import me.qball.spawnerprotection.utils.Utils;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SpawnerBreak implements Listener {

    private final SpawnerProtection plugin;

    public SpawnerBreak(SpawnerProtection plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        SpawnerFile spawnerFile = new SpawnerFile(plugin);
        if (e.getBlock().getState() instanceof CreatureSpawner) {
            if (spawnerFile.getSpawner(e.getPlayer().getUniqueId(), e.getBlock().getLocation())) {
                if (!plugin.getConfig().getBoolean("SilkSpawner")) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Utils.toColor(plugin.getConfig().getString("UseGui")));
                } else if (e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) ||
                        e.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlock().getState();
                    e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.makeSpawner(creatureSpawner));
                    spawnerFile.removeSpawner(e.getBlock().getLocation());
                }
            } else {
                if (!spawnerFile.lookUpSpawner(e.getBlock().getLocation()) &&
                        plugin.getConfig().getBoolean("SilkNaturalSpawner")) {
                    if (e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) ||
                            e.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                        CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlock().getState();
                        e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), Utils.makeSpawner(creatureSpawner));
                    }
                } else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Utils.toColor(plugin.getConfig().getString("NotYourSpawner")));
                }
            }
        }
    }
}
