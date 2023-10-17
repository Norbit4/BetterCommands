package pl.norbit.bettercommands.placeholders;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.norbit.bettercommands.Settings.Config;

public class PlaceholderService {
    public static String replace(String message, CommandSender commandSender) {
        if(!(commandSender instanceof Player p)) return PlaceholderUtil.format(message, null);

        if(Config.PAPI_ENABLE) message = PlaceholderUtil.format(message, p);

        return message.replace("{PLAYER}", p.getName());
    }
}
