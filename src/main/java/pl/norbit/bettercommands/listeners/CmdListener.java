package pl.norbit.bettercommands.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.norbit.bettercommands.settings.Config;
import pl.norbit.bettercommands.utils.MessageUtils;
import pl.norbit.bettercommands.utils.PermissionUtils;

public class CmdListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String[] cmdArray = e.getMessage().split(" ");

        String cmd = cmdArray[0].replace("/", "");

        if(!Config.BLOCKED_COMMANDS.contains(cmd)) return;

        if(PermissionUtils.hasPermission(Config.BLOCKED_PERM, e.getPlayer())) return;

        e.setCancelled(true);

        MessageUtils.toSender(e.getPlayer(), Config.PERM_MESSAGE);
    }
}
