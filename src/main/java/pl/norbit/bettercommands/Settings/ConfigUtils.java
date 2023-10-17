package pl.norbit.bettercommands.Settings;

import org.bukkit.configuration.ConfigurationSection;
import pl.norbit.bettercommands.model.CommandAction;
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

            var executeCommand = new ExecuteCommand(name);

            var perm = cmdSec.getString("perm");
            var permMessage = cmdSec.getString("perm-message");

            executeCommand.setPerm(perm);
            executeCommand.setPermMessage(permMessage);

            var actions = cmdSec.getConfigurationSection("actions");

            if(actions == null) return;

            executeCommand.setActions(getActions(actions));

            commands.add(executeCommand);
        });
        return commands;
    }

    private static List<CommandAction> getActions(ConfigurationSection sec){

        List<CommandAction> actions = new ArrayList<>();

        sec.getKeys(false).forEach(name -> {
            var actionSec = sec.getConfigurationSection(name);

            if(actionSec == null) return;

            var type = actionSec.getString("type");

            if(type == null) return;

            CommandType commandType;
            try {
                commandType = CommandType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return;
            }

            var action = new CommandAction();

            action.setType(commandType);
            action.setAction(actionSec.getStringList("action"));

            actions.add(action);
        });

        return actions;
    }
}
