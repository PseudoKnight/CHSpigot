package me.pseudoknight.chspigot;

import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class SpigotEventListener implements Listener {
    public SpigotEventListener(CommandHelperPlugin chp) {
        chp.registerEvents(this);
    }

    public void unregister() {
        PlayerItemDamageEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Events.ItemDamageEvent pide = new Events.ItemDamageEvent(event);
        EventUtils.TriggerListener(Driver.EXTENSION, "item_damage", pide);
    }
}
