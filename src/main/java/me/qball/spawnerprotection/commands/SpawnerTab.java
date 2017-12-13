package me.qball.spawnerprotection.commands;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.utils.SpawnerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpawnerTab implements TabCompleter {

    private final List<String> types = new ArrayList<>();
    private final List<String> commands = Arrays.asList("gui", "get", "list", "buy");

    public SpawnerTab(SpawnerProtection plugin) {
        Arrays.asList(SpawnerType.values()).forEach(spawnerType -> types.add(spawnerType.getDisplayName()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length <= 1) {
            if (args[0].isEmpty()) return commands;
            else commands.stream().filter(s -> s.startsWith(args[0].toLowerCase())).forEach(completions::add);

            Collections.sort(completions);
            return completions;
        }

        if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("buy")) {
            types.forEach(s -> {
                if (args[1].isEmpty()) completions.add(s);
                else if (s.startsWith(args[1].toLowerCase())) completions.add(s);
            });

            Collections.sort(completions);
            return completions;
        }

        return Collections.emptyList();
    }

}
