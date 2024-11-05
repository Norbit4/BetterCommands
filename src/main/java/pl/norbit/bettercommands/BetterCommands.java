package pl.norbit.bettercommands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.bettercommands.settings.Config;
import pl.norbit.bettercommands.commands.ReloadCommand;
import pl.norbit.bettercommands.listeners.CmdListener;

import java.util.logging.Logger;

public final class BetterCommands extends JavaPlugin {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static BetterCommands instance;

    @Override
    public void onEnable() {
        setInstance(this);
        Config.load(false, null);

        checkPapi();
        infoMessage();

        getCommand("bettercommands").setExecutor(new ReloadCommand());
        getServer().getPluginManager().registerEvents(new CmdListener(), this);
    }

    private void infoMessage(){
        Logger log = getServer().getLogger();
        log.info("");
        log.info("BetterCommands by Norbit4!");
        log.info("Website: https://n0rbit.pl/");
        log.info("");
    }


    private void checkPapi() {
        var pM = getServer().getPluginManager();
        var papiPlugin = pM.getPlugin("PlaceholderAPI");

        Config.PAPI_ENABLE = papiPlugin != null && papiPlugin.isEnabled();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
