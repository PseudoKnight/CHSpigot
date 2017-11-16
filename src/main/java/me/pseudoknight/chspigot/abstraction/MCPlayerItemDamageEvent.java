package me.pseudoknight.chspigot.abstraction;

import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.core.events.BindableEvent;

public interface MCPlayerItemDamageEvent extends BindableEvent {

	MCItemStack getItem();
	int getDamage();
	void setDamage(int damage);
	MCPlayer getPlayer();

}
