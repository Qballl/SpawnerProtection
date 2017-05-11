package me.qball.spawnerprotection.Commands;

import me.qball.spawnerprotection.SpawnerProtection;
import me.qball.spawnerprotection.Utils.SpawnerTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 3039528 on 4/4/17.
 */
public class SpawnerTab implements TabCompleter {
    private SpawnerProtection spawnerProtection;
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<String> commands = new ArrayList<>();
    public SpawnerTab(SpawnerProtection spawnerProtection){
        commands.add("gui");
        commands.add("get");
        commands.add("list");
        commands.add("buy");
        Collections.sort(commands);
        this.spawnerProtection = spawnerProtection;
        for(SpawnerTypes type : SpawnerTypes.values())
            types.add(type.getDisplayName());
       
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("spawners")) {
            if(args.length<=1){
                if(args[0].equals(""))
                    return commands;
                else{
                    for(String command : commands){
                        if(command.toLowerCase().startsWith(args[0].toLowerCase()))
                            completions.add(command);
                    }
                    Collections.sort(completions);
                    return completions;
                }
            }
            else if(args[0].equalsIgnoreCase("get")||args[0].equalsIgnoreCase("buy")){
                for(String type : types) {
                    if(args.length < 2)
                        return null;
                    if (!args[1].equals("")) {
                        if (type.toLowerCase().startsWith(args[1].toLowerCase()))
                            completions.add(type);
                    }else{
                        completions.add(type);
                    }
                }
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
