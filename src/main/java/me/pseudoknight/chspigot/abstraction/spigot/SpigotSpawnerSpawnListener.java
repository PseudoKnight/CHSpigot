package me.pseudoknight.chspigot.abstraction.spigot;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCCreatureSpawner;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCCreatureSpawner;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.pseudoknight.chspigot.abstraction.MCSpawnerSpawnEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpigotSpawnerSpawnListener implements Listener {

	private static SpigotSpawnerSpawnListener listener;

	public static void register() {
		if(listener == null){
			listener = new SpigotSpawnerSpawnListener();
		}
		CommandHelperPlugin.self.registerEvents(listener);
	}

	public static void unregister() {
		SpawnerSpawnEvent.getHandlerList().unregister(listener);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onSpawnerSpawn(SpawnerSpawnEvent event) {
		SpigotSpawnerSpawnEvent sse = new SpigotSpawnerSpawnEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "spawner_spawn", sse);
	}


	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotSpawnerSpawnEvent implements MCSpawnerSpawnEvent {
		SpawnerSpawnEvent e;

		public SpigotSpawnerSpawnEvent(Event e) {
			this.e = (SpawnerSpawnEvent) e;
		}

		@Override
		public MCEntity getEntity() {
			return new BukkitMCEntity(e.getEntity());
		}

		@Override
		public MCCreatureSpawner getSpawner() {
			return new BukkitMCCreatureSpawner(e.getSpawner());
		}

		@Override
		public MCLocation getLocation() {
			return new BukkitMCLocation(e.getLocation());
		}

		@Override
		public Object _GetObject() {
			return e;
		}

	}
}
