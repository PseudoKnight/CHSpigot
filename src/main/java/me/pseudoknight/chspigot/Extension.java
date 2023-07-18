package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotListeners;

import java.util.logging.Level;

@MSExtension("CHSpigot")
public class Extension extends AbstractExtension {

	private final SimpleVersion VERSION = new SimpleVersion(2,0,6);

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		SpigotListeners.register();
		Static.getLogger().log(Level.INFO, "CHSpigot " + VERSION + " loaded.");
	}

	@Override
	public void onShutdown() {
		SpigotListeners.unregister();
		Static.getLogger().log(Level.INFO, "CHSpigot " + VERSION + " unloaded.");
	}
}
