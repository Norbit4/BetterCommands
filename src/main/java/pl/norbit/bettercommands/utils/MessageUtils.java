package pl.norbit.bettercommands.utils;

import org.bukkit.command.CommandSender;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.placeholders.PlaceholderService;

public class MessageUtils {

    public static void toSender(CommandSender sender, String message) {
        sender.sendMessage(ChatUtils.format(PlaceholderService.replace(message, sender)));
    }
    public static void toAll(CommandSender sender,String message) {
        BetterCommands.getInstance().getServer().getOnlinePlayers().forEach(p ->
                p.sendMessage(ChatUtils.format(PlaceholderService.replace(message, sender))));
    }
}
