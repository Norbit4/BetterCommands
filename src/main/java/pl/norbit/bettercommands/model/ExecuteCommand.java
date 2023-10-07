package pl.norbit.bettercommands.model;

import lombok.*;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.utils.ChatUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ExecuteCommand extends BukkitCommand {

    private static Server server = BetterCommands.getInstance().getServer();

    private String usageCommand;
    private List<String> message;

    private final CommandType commandType;

    public ExecuteCommand(@NotNull String name, CommandType commandType) {
        super(name);
        this.commandType = commandType;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(commandType == CommandType.NORMAL) {
            message.forEach(m -> sender.sendMessage(ChatUtils.format(m)));
            return true;
        }

        var usageCommand = new StringBuilder(this.usageCommand);

        Arrays.stream(args).forEach(arg -> usageCommand.append(" ").append(arg));

        server.dispatchCommand(sender, usageCommand.toString());

        return true;
    }

    public void register(){
        server.getCommandMap().register("", this);
    }
}
