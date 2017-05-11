package me.qball.spawnerprotection.Utils;


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
    private SpawnerProtection spawnerProtection;
    public Gui(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    public void createShop(Player p){
        Inventory inventory = Bukkit.createInventory(p,54,"Spawner Shop");
        createItems(inventory,spawnerProtection);
        p.openInventory(inventory);

    }
    private static void createItems(Inventory inventory, SpawnerProtection spawnerProtection){
        for(int i = 0; i<= SpawnerProtection.getAvailableMobs().length - 1; i++){
            SpawnerTypes type = SpawnerTypes.values()[i];
            int cost = spawnerProtection.getConfig().getInt("Spawner_Costs." + type.toString().toLowerCase()+"_spawner");
            ItemStack stack = createStack(type.getDisplayName(), Collections.singletonList("Cost $"+cost));
            inventory.setItem(i,stack);
        }
    }
    private static ItemStack createStack(String name, List<String> lore){
        ItemStack stack = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
