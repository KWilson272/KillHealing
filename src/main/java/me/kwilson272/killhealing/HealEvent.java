package me.kwilson272.killhealing;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HealEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player killer;
    private final Player victim;
    private boolean isCancelled;

    public HealEvent(Player killer, Player victim) {
        this.killer = killer;
        this.victim = victim;
        this.isCancelled = false;
    }

    /**
     * Returns the Player that killed another Player. This is the player that
     * will be healed.
     *
     * @return the Player that killed another Player
     */
    public Player getKiller() {
        return killer;
    }

    /**
     * Returns the Player that was killed. This player will not be healed. To get
     * the player that will be healed, see {@link #getKiller()}
     *
     * @return the Player that was killed
     */
    public Player getVictim() {
        return victim;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return  HANDLER_LIST;
    }
}
