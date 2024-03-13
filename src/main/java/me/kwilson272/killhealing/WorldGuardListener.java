package me.kwilson272.killhealing;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldGuardListener implements Listener {

    private final StateFlag healFlag;

    public WorldGuardListener(StateFlag healFlag) {
        this.healFlag = healFlag;
    }

    @EventHandler
    private void onHeal(HealEvent healEvent) {
        LocalPlayer localKiller = WorldGuardPlugin.inst().wrapPlayer(healEvent.getKiller());
        Location healLoc = localKiller.getLocation();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        if (!query.testState(healLoc, localKiller, healFlag)) {
            healEvent.setCancelled(true);
        }
    }
}
