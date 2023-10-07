package pl.norbit.bettercommands;

import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.bettercommands.Settings.Config;
import pl.norbit.bettercommands.listeners.CmdListener;

public final class BetterCommands extends JavaPlugin {

    private static BetterCommands instance;

    @Override
    public void onEnable() {
        instance = this;

        Config.load();
        getServer().getPluginManager().registerEvents(new CmdListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BetterCommands getInstance() {
        return instance;
    }
}
