package me.pseudoknight.chspigot.abstraction.bukkit;

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
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;

public class BukkitMountListener implements Listener {

	private static BukkitMountListener listener;

	public static void register() {
		if(listener == null){
			listener = new BukkitMountListener();
		}
		CommandHelperPlugin.self.registerEvents(listener);
	}

	public static void unregister() {
		EntityMountEvent.getHandlerList().unregister(listener);
		EntityDismountEvent.getHandlerList().unregister(listener);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityDismount(EntityDismountEvent event) {
		BukkitEntityDismountEvent bede = new BukkitEntityDismountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_dismount", bede);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityMount(EntityMountEvent event) {
		BukkitEntityMountEvent beme = new BukkitEntityMountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_mount", beme);
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitEntityMountEvent implements MCEntityMountEvent {
		EntityMountEvent e;

		public BukkitEntityMountEvent(Event e) {
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
	public static class BukkitEntityDismountEvent implements MCEntityDismountEvent {
		EntityDismountEvent e;

		public BukkitEntityDismountEvent(Event e) {
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
