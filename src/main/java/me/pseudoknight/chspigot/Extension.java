package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotListeners;

@MSExtension("CHSpigot")
public class Extension extends AbstractExtension {

	private final SimpleVersion VERSION = new SimpleVersion(2,0,4);

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		SpigotListeners.register();
		System.out.println("CHSpigot " + VERSION + " loaded.");
	}

	@Override
	public void onShutdown() {
		SpigotListeners.unregister();
		System.out.println("CHSpigot " + VERSION + " unloaded.");
	}
}
