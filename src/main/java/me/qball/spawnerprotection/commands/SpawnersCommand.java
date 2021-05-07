package me.qball.spawnerprotection.commands;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.Gui;
import me.qball.spawnerprotection.utils.SpawnerType;
import me.qball.spawnerprotection.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This could probably still be alot better, but at least now it is readable... somewhat.
 */
public class SpawnersCommand implements CommandExecutor {

    private final SpawnerProtection plugin;
    private final Economy economy = SpawnerProtection.getEconomy();

    public SpawnersCommand(SpawnerProtection plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String noPerm = "&cYou do not have permission to use that command.";
        String noPermSpawner = plugin.getConfig().getString("NoPermission");
        String costMsg = plugin.getConfig().getString("CostMsg");

        if (args.length == 0) {
            Utils.sendMsg(sender, "&cUsage: /spawner <get|buy|list|shop> <spawner>.\n&cFor a list of spawners use /spawners info.");
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            SpawnerType[] types = Utils.getAvailableMobs();
            StringBuilder spawners = new StringBuilder("&aCurrently available spawners:\n   &f");

            Arrays.asList(types).forEach(type -> spawners.append(type).append("&a, &f"));
            spawners.setLength(spawners.length() - 6);
            spawners.append("\n&aNote: Spawner names are case-insensitive.");

            Utils.sendMsg(sender, spawners.toString());
            return true;
        }

        // /spawners give <spawner>
        if (sender instanceof Player && args[0].equalsIgnoreCase("get")) {
            Player player = (Player) sender;

            if (args.length == 1) {
                Utils.sendMsg(player, plugin.getConfig().getString("SpecifySpawner"));
                return true;
            }

            if (!player.hasPermission("spawnerprotection.protect.admin")
                    && !player.hasPermission("spawnerprotection.spawners.get")) {
                Utils.sendMsg(player, noPerm);
                return true;
            }

            SpawnerType type;
            try {
                type = SpawnerType.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                Utils.sendMsg(player, plugin.getConfig().getString("NotAcceptable"));
                return true;
            }

            obtainSpawner(sender, player, type, -1);
            return true;
        }

        // /spawners buy <spawner>
        if (sender instanceof Player && args[0].equalsIgnoreCase("buy")) {
            Player player = (Player) sender;

            if (args.length == 1) {
                Utils.sendMsg(player, plugin.getConfig().getString("SpecifySpawner"));
                return true;
            }

            if (!player.hasPermission("spawnerprotection.spawners.buy")) {
                Utils.sendMsg(player, noPerm);
                return true;
            }

            SpawnerType type;
            try {
                type = SpawnerType.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                Utils.sendMsg(player, plugin.getConfig().getString("NotAcceptable"));
                return true;
            }

            if (!player.hasPermission("spawnerprotection.spawners.buy." + type.getType())
                    && !player.hasPermission("spawnerprotection.spawners.buy.*")) {
                Utils.sendMsg(player, noPermSpawner.replace("{mob}", type.getDisplayName()));
                return true;
            }

            double cost = plugin.getConfig().getInt("Spawner_Costs." + type.getType() + "_spawner");
            obtainSpawner(sender, player, type, cost);
            Utils.sendMsg(player, costMsg.replace("{cost}", "" + cost).replace("{mob}", type.getDisplayName()));
            return true;
        }

        // /spawners gui
        if (sender instanceof Player && args[0].equalsIgnoreCase("shop")) {
            Player player = (Player) sender;
            Gui gui = new Gui(plugin);
            gui.createShop(player);
            return true;
        }


        // /spawners reload
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("spawnerprotection.protect.reload")) {
                Utils.sendMsg(sender, noPerm);
                return true;
            }

            plugin.reloadConfig();
            Utils.sendMsg(sender, "&aYou have reloaded the configuration file.");
            return true;
        }

        // /spawners give <player> <spawner>
        if (!(sender instanceof Player) && args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                Utils.sendMsg(sender, "&cCould not find player matching &f" + args[1] + "&c.");
                return true;
            }

            SpawnerType type;
            try {
                type = SpawnerType.valueOf(args[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                Utils.sendMsg(sender, plugin.getConfig().getString("NotAcceptable"));
                return true;
            }
            obtainSpawner(sender, target, type, -1);
            return true;
        }
        return true;
    }

    private void obtainSpawner(CommandSender sender, Player player, SpawnerType type, double amount) {
        ItemStack stack = Utils.makeSpawner(type.getType());

        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == -1) {
            Utils.sendMsg(sender, plugin.getConfig().getString("NotEnoughSpace"));
            return;
        }

        if (amount != -1) {
            EconomyResponse response = economy.withdrawPlayer(player, amount);
            if (!response.transactionSuccess()) {
                Utils.sendMsg(player, plugin.getConfig().getString("NotEnoughMoney"));
                return;
            }
        }

        inventory.addItem(stack);
        Utils.sendMsg(sender, "&aAdded 1x &f" + type.getDisplayName() + " &cspawner to &f" + player.getName() + "&c's inventory.");
    }

}
