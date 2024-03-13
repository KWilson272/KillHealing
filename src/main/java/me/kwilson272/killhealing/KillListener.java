package me.kwilson272.killhealing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    private final String killerMessage;
    private final String victimMessage;

    protected KillListener(KillHealing killHealing) {
        killerMessage = killHealing.getConfig().getString("Translation.KillerMessage");
        victimMessage = killHealing.getConfig().getString("Translation.VictimMessage");
    }

    @EventHandler
    private void onKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null || killer.equals(victim)) {
            return;
        }

        HealEvent healEvent = new HealEvent(killer, victim);
        Bukkit.getPluginManager().callEvent(healEvent);
        if (!healEvent.isCancelled()) {
            double maxHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double curHealth = killer.getHealth();
            String sMaxHealth = String.format("%.1f", maxHealth);
            String sCurHealth = String.format("%.1f", curHealth);

            String killerFormat = ChatColor.translateAlternateColorCodes('&',
                    killerMessage.replace("{killer}", killer.getName())
                            .replace("{victim}", victim.getName())
                            .replace("{max_health}", sMaxHealth)
                            .replace("{cur_health}", sCurHealth));
            killer.sendMessage(killerFormat);

            String victimFormat = ChatColor.translateAlternateColorCodes('&',
                    victimMessage.replace("{killer}", killer.getName())
                            .replace("{victim}", victim.getName())
                            .replace("{max_health}", sMaxHealth)
                            .replace("{cur_health}", sCurHealth));
            victim.sendMessage(victimFormat);

            killer.setHealth(maxHealth);
        }
    }
}
