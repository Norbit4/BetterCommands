package pl.norbit.bettercommands.settings;

import org.bukkit.configuration.ConfigurationSection;
import pl.norbit.bettercommands.model.CommandAction;
import pl.norbit.bettercommands.model.CommandType;
import pl.norbit.bettercommands.model.ExecuteCommand;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private ConfigUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<ExecuteCommand> getCommands(ConfigurationSection sec) {

        List<ExecuteCommand> commands = new ArrayList<>();

        sec.getKeys(false).forEach(name -> {
            var cmdSec = sec.getConfigurationSection(name);

            if(cmdSec == null) return;

            var executeCommand = new ExecuteCommand(name);

            var completer = cmdSec.getBoolean("completer");
            var perm = cmdSec.getString("perm");
            var permMessage = cmdSec.getString("perm-message", Config.getDefaultPermissionMessage());

            String cooldownMessage = cmdSec.getString("cooldown-message", Config.getDefaultCooldownMessage());
            String argsMessage = cmdSec.getString("arg-message", Config.getDefaultArgsMessage());
            int minArgs = cmdSec.getInt("min-args", 0);

            executeCommand.setPerm(perm);
            executeCommand.setCooldownMessage(cooldownMessage);
            executeCommand.setArgsMessage(argsMessage);
            executeCommand.setMinArgs(minArgs);
            executeCommand.setCompleter(completer);
            executeCommand.setPermMessage(permMessage);

            var actions = cmdSec.getConfigurationSection("actions");

            //sub commands
            ConfigurationSection configurationSection = cmdSec.getConfigurationSection("sub-commands");

            if(configurationSection != null){
                configurationSection.getKeys(false).forEach(subCommand -> {
                    var subCommandSec = cmdSec.getConfigurationSection("sub-commands." + subCommand);
                    if(subCommandSec == null){
                        System.out.println("Sub command section is null");
                        return;
                    }

                    var subActions = subCommandSec.getConfigurationSection("actions");
                    if(subActions == null){
                        System.out.println("Sub actions section is null");
                        return;
                    }
                    executeCommand.addSubCommand(subCommand, getActions(subActions));
                });
            }

            //default actions
            if(actions != null){
                executeCommand.setActions(getActions(actions));
            }

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
