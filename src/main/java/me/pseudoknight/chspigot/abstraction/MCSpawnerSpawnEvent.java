package me.pseudoknight.chspigot.abstraction;

import com.laytonsmith.abstraction.MCCreatureSpawner;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.core.events.BindableEvent;

public interface MCSpawnerSpawnEvent extends BindableEvent {

	MCEntity getEntity();
	MCCreatureSpawner getSpawner();
	MCLocation getLocation();

}
