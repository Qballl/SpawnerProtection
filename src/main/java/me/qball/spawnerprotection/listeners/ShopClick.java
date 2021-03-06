package me.qball.spawnerprotection.listeners;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerType;
import me.qball.spawnerprotection.utils.Utils;
import me.qball.spawnerprotection.utils.Version;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShopClick implements Listener {

    private SpawnerProtection spawnerProtection;

    public ShopClick(SpawnerProtection spawnerProtection) {
        this.spawnerProtection = spawnerProtection;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null || e.getSlotType() == InventoryType.SlotType.OUTSIDE)
            return;
        String noPerm = spawnerProtection.getConfig().getString("NoPermission");
        String costMsg = spawnerProtection.getConfig().getString("CostMsg");
        if (e.getInventory() == null || !e.getView().getTitle().equalsIgnoreCase("Spawner Shop"))
            return;
        e.setCancelled(true);
        String entity = e.getCurrentItem().getItemMeta().getDisplayName();
        SpawnerType type = SpawnerType.valueOf(ChatColor.stripColor(entity.toUpperCase()));
        int cost = spawnerProtection.getConfig().getInt("Spawner_Costs." + type.toString().toLowerCase() + "_spawner");
        if (!e.getWhoClicked().hasPermission("spawner.spawnerprotection.spawners.buy." + type.getDisplayName().toLowerCase()) || !e.getWhoClicked().hasPermission("spawnerprotection.spawners.buy.*")) {
            e.getWhoClicked().sendMessage(Utils.toColor(noPerm.replaceAll("\\{mob}", type.getDisplayName())));
        } else {
            Economy econ = SpawnerProtection.getEconomy();
            if (econ.getBalance((Player) e.getWhoClicked()) >= cost) {
                EconomyResponse r = econ.withdrawPlayer((Player) e.getWhoClicked(), cost);
                if (r.transactionSuccess()) {
                    e.getWhoClicked().sendMessage(Utils.toColor(costMsg.replaceAll("\\{cost}", String.valueOf(cost)).replaceAll("\\{mob}", type.getDisplayName())));
                    String mobSpawner = "";
                    String[] tmp = Bukkit.getVersion().split("MC: ");
                    int ver = Integer.parseInt(tmp[tmp.length - 1].substring(0, 4).split("\\.")[1]);
                    if(ver>=13)
                        mobSpawner = "SPAWNER";
                    else
                        mobSpawner = "MOB_SPAWNER";
                    ItemStack spawner = new ItemStack(Material.valueOf(mobSpawner));
                    ItemMeta meta = spawner.getItemMeta();
                    meta.setDisplayName(Utils.toColor(spawnerProtection.getConfig().getString("SpawnerNameFormat")) +
                            type.getDisplayName() + " Spawner");
                    meta.setLore(Collections.singletonList(type.getType()));
                    spawner.setItemMeta(meta);
                    e.getWhoClicked().getInventory().addItem(spawner);
                    e.getWhoClicked().closeInventory();
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to give you the spawner :( ");
                }

            } else {
                e.getWhoClicked().sendMessage(Utils.toColor(spawnerProtection.getConfig().getString("NotEnoughMoney")));
            }
        }

    }
}
