package pl.norbit.bettercommands.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.norbit.bettercommands.Settings.Config;
import pl.norbit.bettercommands.utils.ChatUtils;

public class CmdListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String[] cmdArray = e.getMessage().split(" ");

        String cmd = cmdArray[0].replace("/", "");

        if(!Config.BLOCKED_COMMANDS.contains(cmd)) return;

        e.setCancelled(true);

        Config.BLOCKED_MSG.forEach(msg -> e.getPlayer().sendMessage(ChatUtils.format(msg)));
    }
}
