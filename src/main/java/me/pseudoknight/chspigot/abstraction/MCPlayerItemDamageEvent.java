package me.pseudoknight.chspigot.abstraction;

import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.core.events.BindableEvent;

public interface MCPlayerItemDamageEvent extends BindableEvent {

	public MCItemStack getItem();
	public int getDamage();
	public void setDamage(int damage);
	public MCPlayer getPlayer();

}
