package pl.norbit.bettercommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.norbit.bettercommands.settings.Config;
import pl.norbit.bettercommands.utils.ChatUtils;

import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            sendInfo(sender);
            return true;
        }

        if(args[0].equalsIgnoreCase("reload")){
            sender.sendMessage(ChatUtils.format(""));
            sender.sendMessage(ChatUtils.format("&7Reloading plugin..."));
            Config.load(true, sender);
            sender.sendMessage(ChatUtils.format(""));
            return true;
        }

        sendInfo(sender);
        return true;
    }

    private static void sendInfo(CommandSender sender){
        sender.sendMessage(ChatUtils.format(""));
        sender.sendMessage(ChatUtils.format("&7BetterCommands by &aNorbit4!"));
        sender.sendMessage(ChatUtils.format("&7Website: &fhttps://n0rbit.pl/"));
        sender.sendMessage(ChatUtils.format(""));
        sender.sendMessage(ChatUtils.format("&7Type: &8/&bbc reload &7to reload plugin!"));
        sender.sendMessage(ChatUtils.format(""));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of("reload");
    }
}
