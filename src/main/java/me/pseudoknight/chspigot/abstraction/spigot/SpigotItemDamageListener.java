package me.pseudoknight.chspigot.abstraction.spigot;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.pseudoknight.chspigot.abstraction.MCPlayerItemDamageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class SpigotItemDamageListener implements Listener {

	private static SpigotItemDamageListener listener;

	public static void register() {
		if (listener == null) {
			listener = new SpigotItemDamageListener();
		}
		CommandHelperPlugin.self.registerEvents(listener);
	}

	public static void unregister() {
		PlayerItemDamageEvent.getHandlerList().unregister(listener);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		SpigotPlayerItemDamageEvent spide = new SpigotPlayerItemDamageEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "item_damage", spide);
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotPlayerItemDamageEvent implements MCPlayerItemDamageEvent {
		PlayerItemDamageEvent e;

		public SpigotPlayerItemDamageEvent(Event e) {
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
		public MCPlayer getPlayer() {
			return new BukkitMCPlayer(e.getPlayer());
		}

		@Override
		public Object _GetObject() {
			return e;
		}
	}
}