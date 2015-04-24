package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHSpigot")
public class CHSpigot extends AbstractExtension {

    public static CommandHelperPlugin chp;
    public static SpigotEventListener listener;
    
    public Version getVersion() {
        return new SimpleVersion(1,3,0);
    }

    @Override
    public void onStartup() {
        chp = CommandHelperPlugin.self;
        listener = new SpigotEventListener(chp);
        System.out.println("CHSpigot " + getVersion() + " loaded.");
    }
    
    @Override
    public void onShutdown() {
        listener.unregister();
        System.out.println("CHSpigot " + getVersion() + " unloaded.");
    }
}
