package pl.norbit.bettercommands.utils;

import org.bukkit.command.CommandSender;
import pl.norbit.bettercommands.BetterCommands;
import pl.norbit.bettercommands.placeholders.PlaceholderService;

public class MessageUtils {

    private MessageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void toSender(CommandSender sender, String message, String... args) {
        sender.sendMessage(ChatUtils.format(PlaceholderService.replace(message, sender), args));
    }
    public static void toAll(CommandSender sender,String message, String... args) {
        BetterCommands.getInstance().getServer().getOnlinePlayers().forEach(p ->
                p.sendMessage(ChatUtils.format(PlaceholderService.replace(message, sender), args)));
    }
}
