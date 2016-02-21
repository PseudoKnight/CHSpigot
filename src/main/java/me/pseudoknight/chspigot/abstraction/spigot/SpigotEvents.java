package me.pseudoknight.chspigot.abstraction.spigot;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.abstraction;
import me.pseudoknight.chspigot.abstraction.MCEntityDismountEvent;
import me.pseudoknight.chspigot.abstraction.MCEntityMountEvent;
import me.pseudoknight.chspigot.abstraction.MCPlayerItemDamageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class SpigotEvents {

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotPlayerItemDamageEvent implements MCPlayerItemDamageEvent {
		PlayerItemDamageEvent e;

		public SpigotPlayerItemDamageEvent(Event e) {
			this.e = (PlayerItemDamageEvent) e;
		}

		@Override
		public MCItemStack getItem() {
			return new BukkitMCItemStack(e.getItem());
		}

		@Override
		public int getDamage() {
			return e.getDamage();
		}

		@Override
		public void setDamage(int damage) {
			e.setDamage(damage);
		}

		@Override
		public MCPlayer getPlayer() {
			return new BukkitMCPlayer(e.getPlayer());
		}

		@Override
		public Object _GetObject() {
			return e;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotEntityMountEvent implements MCEntityMountEvent {
		EntityMountEvent e;

		public SpigotEntityMountEvent(Event e) {
			this.e = (EntityMountEvent) e;
		}

		@Override
		public MCEntity getEntity() {
			return new BukkitMCEntity(e.getEntity());
		}

		@Override
		public MCEntity getMount() {
			return new BukkitMCEntity(e.getMount());
		}

		@Override
		public Object _GetObject() {
			return e;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class SpigotEntityDismountEvent implements MCEntityDismountEvent {
		EntityDismountEvent e;

		public SpigotEntityDismountEvent(Event e) {
			this.e = (EntityDismountEvent) e;
		}

		@Override
		public MCEntity getEntity() {
			return new BukkitMCEntity(e.getEntity());
		}

		@Override
		public MCEntity getDismounted() {
			return new BukkitMCEntity(e.getDismounted());
		}

		@Override
		public Object _GetObject() {
			return e;
		}

	}

}
