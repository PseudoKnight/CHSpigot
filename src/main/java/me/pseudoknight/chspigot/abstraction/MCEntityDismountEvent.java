package me.pseudoknight.chspigot.abstraction;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.core.events.BindableEvent;

public interface MCEntityDismountEvent extends BindableEvent {

	public MCEntity getEntity();
	public MCEntity getDismounted();

}
