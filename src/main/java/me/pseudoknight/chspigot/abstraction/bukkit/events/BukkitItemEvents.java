package me.pseudoknight.chspigot.abstraction.bukkit.events;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.abstraction;
import me.pseudoknight.chspigot.abstraction.events.MCItemDamageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemDamageEvent;

/**
 *
 * @author jacobwgillespie
 */
public class BukkitItemEvents {

    @abstraction(type = Implementation.Type.BUKKIT)
    public static class BukkitMCItemDamageEvent implements MCItemDamageEvent {
        PlayerItemDamageEvent e;

        public BukkitMCItemDamageEvent(Event e) {
            this.e = (PlayerItemDamageEvent) e;
        }

        @Override
        public MCItemStack getItem() {
            return new BukkitMCItemStack(e.getItem());
        }

        @Override
        public int getDamage() {
            return e.getDamage();
        }

        @Override
        public void setDamage(int damage) {
            e.setDamage(damage);
        }

        @Override
        public boolean isCancelled() {
            return e.isCancelled();
        }

        @Override
        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
        }

        @Override
        public MCPlayer getPlayer() {
            return new BukkitMCPlayer(e.getPlayer());
        }

        @Override
        public Object _GetObject() {
            return e;
        }
    }

}
