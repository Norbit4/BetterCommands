package pl.norbit.bettercommands.Settings;

import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.model.ExecuteCommand;

import java.util.List;

public class Config {

    public static List<String> BLOCKED_COMMANDS, BLOCKED_MSG;
    public static String BLOCKED_PERM;

    public static void load() {
        var instance = BetterCommands.getInstance();

//        var config = instance.getConfig();

        instance.saveDefaultConfig();

        var config = instance.getConfig();

        var commandsSection = config.getConfigurationSection("commands");
        var blockedSection = config.getConfigurationSection("blocked");

        if(commandsSection == null) return;

        List<ExecuteCommand> commands = ConfigUtils.getCommands(commandsSection);

        commands.forEach(ExecuteCommand::register);

        if(blockedSection == null) return;

        BLOCKED_COMMANDS = blockedSection.getStringList("commands");
        BLOCKED_PERM = blockedSection.getString("perm");
        BLOCKED_MSG = blockedSection.getStringList("message");
    }
}
