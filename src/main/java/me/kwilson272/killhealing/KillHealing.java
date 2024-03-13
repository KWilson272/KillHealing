package me.kwilson272.killhealing;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class KillHealing extends JavaPlugin {

    private StateFlag healFlag;

    @Override
    public void onLoad() {
        String flagName = getConfig().getString("WorldGuard.FlagName");
        boolean flagDefault = getConfig().getBoolean("WorldGuard.FlagDefault");

        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag(flagName, flagDefault);
            registry.register(flag);
            healFlag = flag;

            getLogger().log(Level.INFO, "Successfully initialized WorldGuard flag with name: "
                    + flagName + ", and default value: " + flagDefault);

        } catch (FlagConflictException e) {
            getLogger().log(Level.WARNING, "A WorldGuard flag with the same name: '" + flagName +
                    "' has already been registered. Please change the 'WorldGuard.FlagName' " +
                    "value in the config.yml. \n The plugin will now disable.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new WorldGuardListener(healFlag), this);
        getLogger().log(Level.INFO, "KillHealing by KWilson272 has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "KillHealing by KWilson272 has been disabled.");
    }
}
