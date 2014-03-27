package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHSpigot")
public class CHSpigot extends AbstractExtension {
    
    public Version getVersion() {
        return new SimpleVersion(1,2,0);
    }

    @Override
    public void onStartup() {
        System.out.println("CHSpigot " + getVersion() + " loaded.");
    }
    
    @Override
    public void onShutdown() {
        System.out.println("CHSpigot " + getVersion() + " unloaded.");
    }

}
