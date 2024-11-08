package pl.norbit.bettercommands.settings;

import org.bukkit.command.CommandSender;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.model.ExecuteCommand;

import java.util.List;

public class Config {

    public static List<String> BLOCKED_COMMANDS;
    public static String BLOCKED_PERM, PERM_MESSAGE;
    public static boolean PAPI_ENABLE;

    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload, CommandSender sender){
        var instance = BetterCommands.getInstance();

        if(!reload) instance.saveDefaultConfig();
        else instance.reloadConfig();

        var config = instance.getConfig();

        var commandsSection = config.getConfigurationSection("commands");
        var blockedSection = config.getConfigurationSection("blocked");

        if(commandsSection == null){
            return;
        }

        List<ExecuteCommand> commands = ConfigUtils.getCommands(commandsSection);

        if(reload) {
            ExecuteCommand.updateCommands(commands, sender);
        }
        else{
            commands.forEach(ExecuteCommand::register);
        }

        if(blockedSection == null){
            return;
        }

        BLOCKED_COMMANDS = blockedSection.getStringList("commands");
        BLOCKED_PERM = blockedSection.getString("perm");
        PERM_MESSAGE = blockedSection.getString("message");
    }
}
