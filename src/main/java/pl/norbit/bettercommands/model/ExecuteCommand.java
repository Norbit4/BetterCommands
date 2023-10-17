package pl.norbit.bettercommands.model;

import lombok.*;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.Settings.Config;
import pl.norbit.bettercommands.placeholders.PlaceholderService;
import pl.norbit.bettercommands.utils.MessageUtils;
import pl.norbit.bettercommands.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ExecuteCommand extends BukkitCommand {

    private static Server server = BetterCommands.getInstance().getServer();
    private static final HashMap<String, ExecuteCommand> COMMANDS = new HashMap<>();

    private String cmdName;
    private List<CommandAction> actions;
    private String perm, permMessage;

    public ExecuteCommand(@NotNull String name) {
        super(name);
        this.actions = new ArrayList<>();
        this.cmdName = name;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if(!PermissionUtils.hasPermission(perm, sender)){
            if(permMessage != null && !permMessage.isEmpty()) MessageUtils.toSender(sender, permMessage);
            else MessageUtils.toSender(sender, Config.PERM_MESSAGE);
            return true;
        }
        actions.forEach(action -> executeAction(action, sender, args));

        return true;
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
            executeCommand.setPermMessage(cmd.getPermMessage());

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
            case TEXT -> actions.forEach(m -> MessageUtils.toSender(sender, m));
            case PLAYER_COMMAND -> actions.forEach(c -> server.dispatchCommand(sender, PlaceholderService.replace(c, sender)));
            case SERVER_COMMAND -> actions.forEach(c -> server.dispatchCommand(server.getConsoleSender(), PlaceholderService.replace(c, sender)));
            case BROADCAST -> actions.forEach(m -> MessageUtils.toAll(sender, m));
        }
    }
}
