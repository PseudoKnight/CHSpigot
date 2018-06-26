package me.pseudoknight.chspigot.abstraction.spigot;

import com.laytonsmith.abstraction.MCCreatureSpawner;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class SpigotListeners implements Listener {

	private static SpigotListeners listener;

	public static void register() {
		if(listener == null){
			listener = new SpigotListeners();
		}
		CommandHelperPlugin.self.registerEvents(listener);
	}

	public static void unregister() {
		PlayerItemDamageEvent.getHandlerList().unregister(listener);
		EntityMountEvent.getHandlerList().unregister(listener);
		EntityDismountEvent.getHandlerList().unregister(listener);
		SpawnerSpawnEvent.getHandlerList().unregister(listener);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		SpigotEvents.SpigotPlayerItemDamageEvent spide = new SpigotEvents.SpigotPlayerItemDamageEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "item_damage", spide);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityDismount(EntityDismountEvent event) {
		SpigotEvents.SpigotEntityDismountEvent sede = new SpigotEvents.SpigotEntityDismountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_dismount", sede);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onEntityMount(EntityMountEvent event) {
		SpigotEvents.SpigotEntityMountEvent seme = new SpigotEvents.SpigotEntityMountEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "entity_mount", seme);
	}

	@EventHandler(priority= EventPriority.LOWEST)
	public void onSpawnerSpawn(SpawnerSpawnEvent event) {
		SpigotEvents.SpigotSpawnerSpawnEvent sse = new SpigotEvents.SpigotSpawnerSpawnEvent(event);
		EventUtils.TriggerListener(Driver.EXTENSION, "spawner_spawn", sse);
	}
}
