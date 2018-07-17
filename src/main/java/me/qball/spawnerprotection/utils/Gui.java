package me.qball.spawnerprotection.utils;

import me.qball.spawnerprotection.SpawnerProtection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class Gui {

    private final SpawnerProtection plugin;

    public Gui(SpawnerProtection plugin) {
        this.plugin = plugin;
    }

    public void createShop(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "Spawner Shop");
        createItems(inventory);
        player.openInventory(inventory);
    }

    private void createItems(Inventory inventory) {
        for (int i = 0; i <= Utils.getAvailableMobs().length - 1; i++) {
            SpawnerType type = Utils.getAvailableMobs()[i];

            int cost = plugin.getConfig().getInt("Spawner_Costs." + type.toString().toLowerCase() + "_spawner");
            ItemStack stack = createStack(type.getDisplayName(), Collections.singletonList("Cost $" + cost));
            inventory.setItem(i, stack);
        }
    }

    private ItemStack createStack(String name, List<String> lore) {
        String spawner = "";
        String[] tmp = Bukkit.getVersion().split("MC: ");
        Version version = Version.getVersion(tmp[1]);
        if(version.getId().equalsIgnoreCase("1.13"))
            spawner = "SPAWNER";
        else
           spawner = "MOB_SPAWNER";
        ItemStack stack = new ItemStack(Material.valueOf(spawner));
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.toColor(plugin.getConfig().getString("SpawnerNameFormat")) + name);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

}
