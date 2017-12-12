package me.qball.spawnerprotection.Listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerTypes;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShopClick implements Listener{

    private SpawnerProtection spawnerProtection;
    public ShopClick(SpawnerProtection spawnerProtection){
        this.spawnerProtection = spawnerProtection;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        String noPerm = spawnerProtection.getConfig().getString("NoPermission");
        String costMsg = spawnerProtection.getConfig().getString("CostMsg");
        if(e.getInventory() == null  || !e.getInventory().getName().equalsIgnoreCase("Spawner Shop"))
            return;
        e.setCancelled(true);
        String entity = e.getCurrentItem().getItemMeta().getDisplayName();
        SpawnerTypes type = SpawnerTypes.valueOf(ChatColor.stripColor(entity.toUpperCase()));
        int cost = spawnerProtection.getConfig().getInt("Spawner_Costs." + type.toString().toLowerCase()+"_spawner");
        if(!e.getWhoClicked().hasPermission("spawner.spawnerprotection.spawners.buy."+type.getDisplayName().toLowerCase())||!e.getWhoClicked().hasPermission("spawnerprotection.spawners.buy.*")) {
            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',noPerm.replaceAll("\\{mob}",type.getDisplayName())));
        }else {
            Economy econ = SpawnerProtection.getEcon();
            if (econ.getBalance((Player) e.getWhoClicked()) >= cost) {
                EconomyResponse r = econ.withdrawPlayer((Player) e.getWhoClicked(), cost);
                if (r.transactionSuccess()) {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',costMsg.replaceAll("\\{cost}",String.valueOf(cost)).replaceAll("\\{mob}",type.getDisplayName())));
                    ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
                    ItemMeta meta = spawner.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',spawnerProtection.getConfig().getString("SpawnerNameFormat"))+
                            type.getDisplayName() + " Spawner");
                    meta.setLore(Collections.singletonList(type.getDisplayName()));
                    spawner.setItemMeta(meta);
                    e.getWhoClicked().getInventory().addItem(spawner);
                    e.getWhoClicked().closeInventory();
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to give you the spawner :( ");
                }

            } else {
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',spawnerProtection.getConfig().getString("NotEnoughMoney")));
            }
        }

    }
}
