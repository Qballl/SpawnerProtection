package me.qball.spawnerprotection.utils;

import me.qball.spawnerprotection.SpawnerProtection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class Utils {

    private static final SpawnerProtection PLUGIN = SpawnerProtection.getInstance();

    public static String toColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static SpawnerType[] getAvailableMobs() {
        String[] tmp = Bukkit.getVersion().split("MC: ");
        Version version = Version.getVersion(tmp[1]);

        Set<SpawnerType> types = SpawnerType.getByVersion(version);
        return types.toArray(new SpawnerType[types.size()]);
    }

    public static String convertToString(Location location) {
        return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }

    public static ItemStack makeSpawner(String mob) {
        String mobSpawner = "";
        String[] tmp = Bukkit.getVersion().split("MC: ");
        int ver = Integer.parseInt(tmp[tmp.length - 1].substring(0, 4).split("\\.")[1]);
        if(ver>=13) {
            mobSpawner = "SPAWNER";
        }else
            mobSpawner = "MOB_SPAWNER";
        ItemStack spawner = new ItemStack(Material.valueOf(mobSpawner));
        ItemMeta meta = spawner.getItemMeta();

        Arrays.stream(Utils.getAvailableMobs()).filter(type -> Objects.equals(type.getType(), mob))
                .forEach(type -> meta.setDisplayName(toColor(PLUGIN.getConfig().getString("SpawnerNameFormat")) + type.getDisplayName() + " Spawner"));

        meta.setLore(Collections.singletonList(mob));
        spawner.setItemMeta(meta);
        return spawner;
    }

    public static ItemStack makeSpawner(CreatureSpawner creatureSpawner) {
        return makeSpawner(SpawnerType.findName(creatureSpawner.getSpawnedType().name()));
    }

    public static void sendMsg(CommandSender sender, String string) {
        sender.sendMessage(toColor(string));
    }

}
