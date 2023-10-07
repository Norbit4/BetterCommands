package pl.norbit.bettercommands.Settings;

import org.bukkit.configuration.ConfigurationSection;
import pl.norbit.bettercommands.model.CommandType;
import pl.norbit.bettercommands.model.ExecuteCommand;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    public static List<ExecuteCommand> getCommands(ConfigurationSection sec) {

        List<ExecuteCommand> commands = new ArrayList<>();

        sec.getKeys(false).forEach(name -> {
            var cmdSec = sec.getConfigurationSection(name);

            if(cmdSec == null) return;

            var type = cmdSec.getString("type");

            if(type == null) return;

            var commandType = CommandType.valueOf(type.toUpperCase());

            var executeCommand = new ExecuteCommand(name, commandType);

            if(commandType == CommandType.REPLACE) executeCommand.setUsageCommand(cmdSec.getString("cmd"));
            else if(commandType == CommandType.NORMAL) executeCommand.setMessage(cmdSec.getStringList("message"));

            commands.add(executeCommand);
        });
        return commands;
    }
}
