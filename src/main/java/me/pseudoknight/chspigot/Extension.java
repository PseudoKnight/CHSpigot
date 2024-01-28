package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import me.pseudoknight.chspigot.abstraction.bukkit.BukkitMountListener;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotItemDamageListener;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotMountListener;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotSpawnerSpawnListener;

import java.util.logging.Level;

@MSExtension("CHSpigot")
public class Extension extends AbstractExtension {

	private final SimpleVersion VERSION = new SimpleVersion(2,0,8);

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		SpigotItemDamageListener.register();
		SpigotSpawnerSpawnListener.register();
		try {
			Class.forName("org.bukkit.event.entity.EntityMountEvent");
			BukkitMountListener.register();
		} catch (ClassNotFoundException e) {
			// changed in 1.20.4
			SpigotMountListener.register();
		}
		Static.getLogger().log(Level.INFO, "CHSpigot " + VERSION + " loaded.");
	}

	@Override
	public void onShutdown() {
		SpigotItemDamageListener.unregister();
		SpigotSpawnerSpawnListener.unregister();
		try {
			Class.forName("org.bukkit.event.entity.EntityMountEvent");
			BukkitMountListener.unregister();
		} catch (ClassNotFoundException e) {
			SpigotMountListener.unregister();
		}
		Static.getLogger().log(Level.INFO, "CHSpigot " + VERSION + " unloaded.");
	}
}
