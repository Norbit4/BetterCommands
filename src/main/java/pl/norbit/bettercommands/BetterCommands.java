package pl.norbit.bettercommands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.bettercommands.settings.Config;
import pl.norbit.bettercommands.commands.ReloadCommand;

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
    }

    private void infoMessage(){
        Logger log = getServer().getLogger();
        log.info("");
        log.info("BetterCommands by Norbit4!");
        log.info("Website: https://github.com/Norbit4");
        log.info("");
    }
    
    private void checkPapi() {
        PluginManager pm = getServer().getPluginManager();
        Plugin papiPlugin = pm.getPlugin("PlaceholderAPI");

        if(papiPlugin != null){
            Config.setPapiEnable(papiPlugin.isEnabled());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
