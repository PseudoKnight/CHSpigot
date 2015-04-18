package me.pseudoknight.chspigot.abstraction.events;

import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.events.MCPlayerEvent;

/**
 *
 * @author jacobwgillespie
 */
public interface MCItemDamageEvent extends MCPlayerEvent {
    public MCItemStack getItem();
    public int getDamage();
    public void setDamage(int damage);
    public boolean isCancelled();
    public void setCancelled(boolean cancelled);
}
