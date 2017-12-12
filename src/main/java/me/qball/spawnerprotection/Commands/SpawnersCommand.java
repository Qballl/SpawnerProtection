package me.qball.spawnerprotection.Commands;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.Gui;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;


public class SpawnersCommand implements CommandExecutor {
    private SpawnerProtection spawnerProtection;
    public Economy econ;

    public SpawnersCommand(SpawnerProtection spawnerProtection) {
        this.spawnerProtection = spawnerProtection;
        this.econ = SpawnerProtection.getEcon();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String noPerm = spawnerProtection.getConfig().getString("NoPermission");
        String costMsg = spawnerProtection.getConfig().getString("CostMsg");
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Correct usage is /spawners <get,buy,list,gui> <spawner> for a list of spawners do /spawners info");
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    SpawnerTypes[] types = SpawnerProtection.getAvailableMobs();
                    String spawners = "The list of spawners is: \n";
                    for (SpawnerTypes type : types)
                        spawners += " " + type + ",";
                    spawners += "\n dont worry about case they are case insensitive";
                    p.sendMessage(ChatColor.GREEN + spawners);
                } else if (args[0].equalsIgnoreCase("get")) {
                    if (args.length == 1)
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("SpecifySpawner")));
                    else if (p.hasPermission("spawnerprotection.protect.admin") || p.hasPermission("spawnerprotection.spawners.get")) {
                        String spawner = args[1];
                        SpawnerTypes type = null;
                        try {
                            type = SpawnerTypes.valueOf(spawner.toUpperCase());
                            ItemStack stack = new ItemStack(Material.MOB_SPAWNER);
                            ItemMeta meta = stack.getItemMeta();
                            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("SpawnerNameFormat")) +
                                    type.getDisplayName() + " Spawner");
                            meta.setLore(Collections.singletonList(type.getDisplayName()));
                            stack.setItemMeta(meta);
                            p.getInventory().addItem(stack);
                            p.sendMessage(ChatColor.GREEN + "Added " + type.getDisplayName() + " spawner to your inventory");
                        } catch (IllegalArgumentException e) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("NotAcceptable")));
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "You dont have permission for that command");
                    }
                } else if (args[0].equalsIgnoreCase("buy")) {
                    if (p.hasPermission("spawnerprotection.spawners.buy")) {
                        if (args.length == 1)
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("SpecifySpawner")));
                        String spawner = args[1];
                        SpawnerTypes type = null;
                        try {
                            type = SpawnerTypes.valueOf(spawner.toUpperCase());
                            if (p.hasPermission("spawnerprotection.spawners.buy." + type.name().toLowerCase()) || p.hasPermission("spawnerprotection.spawners.buy.*")) {
                                int cost = spawnerProtection.getConfig().getInt("Spawner_Costs." + type.toString().toLowerCase() + "_spawner");
                                if (SpawnerProtection.getEcon().getBalance(p) >= cost) {
                                    EconomyResponse response = SpawnerProtection.getEcon().withdrawPlayer(p, cost);
                                    if (response.transactionSuccess()) {
                                        ItemStack stack = new ItemStack(Material.MOB_SPAWNER);
                                        ItemMeta meta = stack.getItemMeta();
                                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("SpawnerNameFormat")) +
                                                type.getDisplayName() + " Spawner");
                                        meta.setLore(Collections.singletonList(type.getDisplayName()));
                                        stack.setItemMeta(meta);
                                        p.getInventory().addItem(stack);
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', costMsg.replaceAll("\\{cost}", String.valueOf(cost)).replaceAll("\\{mob}", type.getDisplayName())));
                                    } else
                                        p.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to give you the spawner :( ");
                                } else {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("NotEnoughMoney")));
                                }
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm.replaceAll("\\{mob}", type.getDisplayName())));
                            }
                        } catch (IllegalArgumentException e) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("NotAcceptable")));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("gui")) {
                    Gui gui = new Gui(spawnerProtection);
                    gui.createShop(p);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (p.hasPermission("spawnerprotection.protect.reload")) {
                        spawnerProtection.getServer().getPluginManager().getPlugin("SpawnerProtection").reloadConfig();
                        p.sendMessage(ChatColor.GREEN + "Config has been reloaded");
                    }
                }
            }
        } else {
            if(args.length>=3){
                if(args[0].equalsIgnoreCase("give")){
                    if(Bukkit.getServer().getPlayer(args[1]) != null){
                        Player p = Bukkit.getServer().getPlayer(args[1]);
                        SpawnerTypes type;
                        try {
                            type = SpawnerTypes.valueOf(args[2].toUpperCase());
                            ItemStack stack = new ItemStack(Material.MOB_SPAWNER);
                            ItemMeta meta = stack.getItemMeta();
                            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', spawnerProtection.getConfig().getString("SpawnerNameFormat")) +
                                    type.getDisplayName() + " Spawner");
                            meta.setLore(Collections.singletonList(type.getDisplayName()));
                            stack.setItemMeta(meta);
                            p.getInventory().addItem(stack);
                            p.sendMessage(ChatColor.GREEN + "Added " + type.getDisplayName() + " spawner to your inventory");
                        } catch (IllegalArgumentException e) {
                            sender.sendMessage(spawnerProtection.getConfig().getString("NotAcceptable"));
                        }

                    }
                }
            }
        }
        return false;
    }

}