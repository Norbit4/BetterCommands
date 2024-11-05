package pl.norbit.bettercommands.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderUtil {

    private PlaceholderUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String format(String message, Player p){
        return PlaceholderAPI.setPlaceholders(p, message);
    }
}
