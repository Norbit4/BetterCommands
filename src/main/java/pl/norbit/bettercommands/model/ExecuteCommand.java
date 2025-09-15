package pl.norbit.bettercommands.model;

import lombok.*;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.settings.Config;
import pl.norbit.bettercommands.placeholders.PlaceholderService;
import pl.norbit.bettercommands.utils.ChatUtils;
import pl.norbit.bettercommands.utils.MessageUtils;
import pl.norbit.bettercommands.utils.PermissionUtils;

import java.util.*;

@Getter
@Setter
public class ExecuteCommand extends BukkitCommand {

    private static Server server = BetterCommands.getInstance().getServer();
    private static final HashMap<String, ExecuteCommand> COMMANDS = new HashMap<>();

    private String cmdName;
    private List<CommandAction> actions;
    private String perm;
    private String permMessage;
    private String cooldownMessage;

    private String argsMessage;
    private int minArgs;

    private boolean completer;

    private Map<String, List<CommandAction>> subCommands;

    public ExecuteCommand(@NotNull String name) {
        super(name);
        this.actions = new ArrayList<>();
        this.cmdName = name;
        this.subCommands = new HashMap<>();
    }

    public void addSubCommand(String subCommand, List<CommandAction> actions){
        this.subCommands.put(subCommand, actions);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!PermissionUtils.hasPermission(perm, sender)){
            if(permMessage != null && !permMessage.isEmpty()) MessageUtils.toSender(sender, permMessage);
            else MessageUtils.toSender(sender, Config.PERM_MESSAGE);
            return true;
        }

        if(args.length < minArgs){
            MessageUtils.toSender(sender, argsMessage);
            return true;
        }

        //execute default actions
        if(args.length < 1){
            actions.forEach(action -> executeAction(action, sender, args));
            return true;
        }

        String arg = args[0];
        //execute sub commands
        List<CommandAction> commandActions = subCommands.get(arg);
        if(commandActions == null){
            actions.forEach(action -> executeAction(action, sender, args));
            return true;
        }

        commandActions.forEach(action -> executeAction(action, sender, args));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if(!completer){
            return Collections.emptyList();
        }

        return new ArrayList<>(subCommands.keySet());
    }

    public void register(){
        server.getCommandMap().register("", this);
        COMMANDS.put(cmdName, this);
    }

    public static void updateCommands(List<ExecuteCommand> updatedCommands, CommandSender sender){
        boolean reloadWarn = false;
        MessageUtils.toSender(sender, "");

        for (ExecuteCommand cmd : updatedCommands) {
            var executeCommand = COMMANDS.get(cmd.getCmdName());

            if(executeCommand == null){
                MessageUtils.toSender(sender,
                        "&cCommand &e" + cmd.getCmdName() + " &cwas not previously registered!");
                reloadWarn = true;
                continue;
            }else{
                MessageUtils.toSender(sender,
                        "&7Command &a" + cmd.getCmdName() + " &7has been reloaded!");
            }

            executeCommand.setPerm(cmd.getPerm());
            executeCommand.setCompleter(cmd.isCompleter());
            executeCommand.setPermMessage(cmd.getPermMessage());
            executeCommand.setSubCommands(cmd.getSubCommands());

            executeCommand.setActions(cmd.getActions());
        }
        MessageUtils.toSender(sender, "");
        if(reloadWarn) {
            MessageUtils.toSender(sender,
                    "&cUnable to reload change detected!");
            MessageUtils.toSender(sender,
                    "&cTo reload changing the name of a command or adding a new command you must reload the server!");
        }else{
            MessageUtils.toSender(sender, "&aAll commands have been successfully reloaded!");
        }
    }

    private void executeAction(CommandAction cmdAction, CommandSender sender, @NotNull String[] args){
        List<String> actions = cmdAction.getAction();

        if(actions.isEmpty()) return;

        switch (cmdAction.getType()) {
            case REPLACE -> {
                var usageCommand = new StringBuilder(actions.get(0));

                Arrays.stream(args).forEach(arg -> usageCommand.append(" ").append(arg));

                server.dispatchCommand(sender, usageCommand.toString());
            }
            case TEXT -> actions.forEach(m -> MessageUtils.toSender(sender, m, args));
            case PLAYER_COMMAND ->
                actions.forEach(c -> {
                    String command = ChatUtils.replace(PlaceholderService.replace(c, sender), args);
                    server.dispatchCommand(sender, command);
                });
            case SERVER_COMMAND ->
                actions.forEach(c -> {
                    String command = ChatUtils.replace(PlaceholderService.replace(c, sender), args);
                    server.dispatchCommand(server.getConsoleSender(), command);
                });
            case BROADCAST -> actions.forEach(m -> MessageUtils.toAll(sender, m, args));
        }
    }
}
