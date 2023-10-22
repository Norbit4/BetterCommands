package pl.norbit.bettercommands.utils;

import org.bukkit.command.CommandSender;

public class PermissionUtils {

    public static boolean hasPermission(String permission, CommandSender sender) {

        if(permission == null || permission.isEmpty()) return true;

        if(sender.isOp()) return true;

        return sender.hasPermission(permission);
    }
}
