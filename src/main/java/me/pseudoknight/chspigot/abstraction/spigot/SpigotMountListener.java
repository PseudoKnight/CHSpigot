package me.pseudoknight.chspigot.abstraction.spigot;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.pseudoknight.chspigot.abstraction.MCEntityDismountEvent;
import me.pseudoknight.chspigot.abstraction.MCEntityMountEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class SpigotMountListener implements Listener {

	private static SpigotItemDamageListener listener;

	public static void register() {
		if(listener == null){
			listener = new SpigotItemDamageListener();
		}
		CommandHelperPlugin.self.registerEvents(listener);
	}

	public static void unregister() {
		EntityMountEvent.getHandlerList().unregister(listener);
		EntityDismountEvent.getHandlerList().unregister(listener);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityDismount(EntityDismountEvent event) {
		SpigotEntityDismountEvent sede = new SpigotEntityDismountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_dismount", sede);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityMount(EntityMountEvent event) {
		SpigotEntityMountEvent seme = new SpigotEntityMountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_mount", seme);
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotEntityMountEvent implements MCEntityMountEvent {
		EntityMountEvent e;

		public SpigotEntityMountEvent(Event e) {
			this.e = (EntityMountEvent) e;
		}

		@Override
		public MCEntity getEntity() {
			return new BukkitMCEntity(e.getEntity());
		}

		@Override
		public MCEntity getMount() {
			return new BukkitMCEntity(e.getMount());
		}

		@Override
		public Object _GetObject() {
			return e;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotEntityDismountEvent implements MCEntityDismountEvent {
		EntityDismountEvent e;

		public SpigotEntityDismountEvent(Event e) {
			this.e = (EntityDismountEvent) e;
		}

		@Override
		public MCEntity getEntity() {
			return new BukkitMCEntity(e.getEntity());
		}

		@Override
		public MCEntity getDismounted() {
			return new BukkitMCEntity(e.getDismounted());
		}

		@Override
		public Object _GetObject() {
			return e;
		}

	}
}
