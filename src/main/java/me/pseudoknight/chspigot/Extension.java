package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import me.pseudoknight.chspigot.abstraction.spigot.SpigotListeners;

@MSExtension("CHSpigot")
public class Extension extends AbstractExtension {

	public Version getVersion() {
		return new SimpleVersion(1,5,1);
	}

	@Override
	public void onStartup() {
		SpigotListeners.register();
		System.out.println("CHSpigot " + getVersion() + " loaded.");
	}

	@Override
	public void onShutdown() {
		SpigotListeners.unregister();
		System.out.println("CHSpigot " + getVersion() + " unloaded.");
	}
}
