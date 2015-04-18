package me.pseudoknight.chspigot.abstraction.bukkit.events.drivers;

import com.laytonsmith.annotations.EventIdentifier;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.pseudoknight.chspigot.abstraction.bukkit.events.BukkitItemEvents;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 *
 * @author jacobwgillespie
 */
public class BukkitItemListener implements Listener {

    @EventIdentifier(event = Driver.EXTENSION, className = "org.bukkit.event.player.PlayerItemDamageEvent")
    public void onItemPickup(Event event) {
        BukkitItemEvents.BukkitMCItemDamageEvent pide = new BukkitItemEvents.BukkitMCItemDamageEvent(event);
        EventUtils.TriggerExternal(pide);
        EventUtils.TriggerListener(Driver.EXTENSION, "item_damage", pide);
    }

}
