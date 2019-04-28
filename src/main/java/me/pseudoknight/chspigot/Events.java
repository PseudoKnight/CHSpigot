package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.MSLog;
import com.laytonsmith.core.MSVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.BoundEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import com.laytonsmith.core.natives.interfaces.Mixed;
import me.pseudoknight.chspigot.abstraction.MCEntityDismountEvent;
import me.pseudoknight.chspigot.abstraction.MCEntityMountEvent;
import me.pseudoknight.chspigot.abstraction.MCPlayerItemDamageEvent;
import me.pseudoknight.chspigot.abstraction.MCSpawnerSpawnEvent;

import java.util.HashMap;
import java.util.Map;

public class Events {
	public static String docs() {
		return "This augments CommandHelper with Spigot-specific events";
	}

	@api
	public static class item_damage extends AbstractEvent {

		@Override
		public String getName() {
			return "item_damage";
		}

		@Override
		public String docs() {
			return "{player: <string match> | itemname: <string match>} "
					+ "This event is called when a player's item (like a tool) will take damage. "
					+ "Cancelling this event will prevent damage from being taken on items."
					+ "{player: The player | item: An item array of the item being damaged |"
					+ " damage: the amount of durability damage the item will be taking} "
					+ "{damage: change the amount of damage taken} "
					+ "{}";
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			return null;
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_1;
		}

		@Override
		public void bind(BoundEvent event) {
			Map<String, Mixed> prefilters = event.getPrefilter();
			if(prefilters.containsKey("item")) {
				MSLog.GetLogger().w(MSLog.Tags.DEPRECATION, "The \"item\" prefilter here is no longer supported."
						+ " Use \"itemname\" instead.", event.getTarget());
			}
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent e) throws PrefilterNonMatchException {
			if (e instanceof MCPlayerItemDamageEvent) {
				MCPlayerItemDamageEvent event = (MCPlayerItemDamageEvent)e;

				Prefilters.match(prefilter, "itemname", event.getItem().getType().getName(), Prefilters.PrefilterType.STRING_MATCH);
				Prefilters.match(prefilter, "player", event.getPlayer().getName(), Prefilters.PrefilterType.MACRO);

				return true;
			}

			return false;
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent e) throws EventException {
			if (e instanceof MCPlayerItemDamageEvent) {
				MCPlayerItemDamageEvent event = (MCPlayerItemDamageEvent) e;
				Map<String, Mixed> map = evaluate_helper(e);

				map.put("player", new CString(event.getPlayer().getName(), Target.UNKNOWN));
				map.put("item", ObjectGenerator.GetGenerator().item(event.getItem(), Target.UNKNOWN));
				map.put("damage", new CInt(event.getDamage(), Target.UNKNOWN));

				return map;
			}
			throw new EventException("Cannot convert e to ItemDamageEvent");
		}

		@Override
		public boolean modifyEvent(String key, Mixed value, BindableEvent e) {
			MCPlayerItemDamageEvent event = (MCPlayerItemDamageEvent)e;

			if (key.equalsIgnoreCase("damage") && value instanceof CInt) {
				event.setDamage(Integer.parseInt(value.val()));
				event.getPlayer().updateInventory();
				return true;
			}

			return false;
		}

		@Override
		public void cancel(BindableEvent o, boolean state) {
			super.cancel(o, state);
			MCPlayerItemDamageEvent e = (MCPlayerItemDamageEvent)o;
			e.getPlayer().updateInventory();
		}

	}

	@api
	public static class entity_mount extends AbstractEvent {

		@Override
		public String getName() {
			return "entity_mount";
		}

		@Override
		public String docs() {
			return "{type: <macro match> | mounttype: <macro match>} "
					+ "This event is called when an entity mounts another entity."
					+ "{type: The type of entity that is mounting | id: The UUID of the mounting entity"
					+ " | mounttype: The type of entity that is mounted | mountid: The UUID of the mounted entity } "
					+ "{}"
					+ "{}";
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			return null;
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_2;
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent e) throws PrefilterNonMatchException {
			if (e instanceof MCEntityMountEvent) {
				MCEntityMountEvent event = (MCEntityMountEvent)e;

				Prefilters.match(prefilter, "type", event.getEntity().getType().name(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "mounttype", event.getMount().getType().name(), Prefilters.PrefilterType.MACRO);

				return true;
			}

			return false;
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent e) throws EventException {
			if (e instanceof MCEntityMountEvent) {
				MCEntityMountEvent event = (MCEntityMountEvent) e;
				Map<String, Mixed> map = new HashMap<>();

				map.put("type", new CString(event.getEntity().getType().name(), Target.UNKNOWN));
				map.put("id", new CString(event.getEntity().getUniqueId().toString(), Target.UNKNOWN));
				map.put("mounttype", new CString(event.getMount().getType().name(), Target.UNKNOWN));
				map.put("mountid", new CString(event.getMount().getUniqueId().toString(), Target.UNKNOWN));

				return map;
			}
			throw new EventException("Cannot convert e to MCEntityMountEvent");
		}

		@Override
		public boolean modifyEvent(String key, Mixed value, BindableEvent e) {
			return false;
		}
	}

	@api
	public static class entity_dismount extends AbstractEvent {

		@Override
		public String getName() {
			return "entity_dismount";
		}

		@Override
		public String docs() {
			return "{type: <macro match> | mounttype: <macro match>} "
					+ "This event is called when an entity dismounts another entity. This event cannot be cancelled."
					+ "{type: The type of entity that is dismounting | id: The UUID of the dismounting entity"
					+ " | mounttype: The type of entity that is mounted | mountid: The UUID of the mounted entity } "
					+ "{}"
					+ "{}";
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			return null;
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_2;
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent e) throws PrefilterNonMatchException {
			if (e instanceof MCEntityDismountEvent) {
				MCEntityDismountEvent event = (MCEntityDismountEvent)e;

				Prefilters.match(prefilter, "type", event.getEntity().getType().name(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "mounttype", event.getDismounted().getType().name(), Prefilters.PrefilterType.MACRO);

				return true;
			}

			return false;
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent e) throws EventException {
			if (e instanceof MCEntityDismountEvent) {
				MCEntityDismountEvent event = (MCEntityDismountEvent) e;
				Map<String, Mixed> map = new HashMap<>();

				map.put("type", new CString(event.getEntity().getType().name(), Target.UNKNOWN));
				map.put("id", new CString(event.getEntity().getUniqueId().toString(), Target.UNKNOWN));
				map.put("mounttype", new CString(event.getDismounted().getType().name(), Target.UNKNOWN));
				map.put("mountid", new CString(event.getDismounted().getUniqueId().toString(), Target.UNKNOWN));

				return map;
			}
			throw new EventException("Cannot convert e to MCEntityDismountEvent");
		}

		@Override
		public boolean modifyEvent(String key, Mixed value, BindableEvent e) {
			return false;
		}
	}

	@api
	public static class spawner_spawn extends AbstractEvent {

		@Override
		public String getName() {
			return "spawner_spawn";
		}

		@Override
		public String docs() {
			return "{type: <string match>} "
					+ "This event is called when a spawner spawns an entity."
					+ "{type: The type of entity that is spawning | id: The UUID of the spawning entity"
					+ " | spawner: The location of the spawner that spawned the entity"
					+ " | location: The location the entity spawned} "
					+ "{}"
					+ "{}";
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent e) throws PrefilterNonMatchException {
			if (e instanceof MCSpawnerSpawnEvent) {
				MCSpawnerSpawnEvent event = (MCSpawnerSpawnEvent)e;
				if(prefilter.containsKey("type")) {
					if(!prefilter.get("type").val().equals(event.getEntity().getType().name())) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent e) throws EventException {
			if (e instanceof MCSpawnerSpawnEvent) {
				MCSpawnerSpawnEvent event = (MCSpawnerSpawnEvent) e;
				Map<String, Mixed> map = new HashMap<>();
				Target t = Target.UNKNOWN;

				map.put("type", new CString(event.getEntity().getType().name(),t));
				map.put("id", new CString(event.getEntity().getUniqueId().toString(), t));
				map.put("location", ObjectGenerator.GetGenerator().location(event.getLocation(), false));
				map.put("spawner", ObjectGenerator.GetGenerator().location(event.getSpawner().getLocation(), false));

				return map;
			}
			throw new EventException("Cannot convert e to MCSpawnerSpawnEvent");
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			return null;
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_2;
		}

		@Override
		public boolean modifyEvent(String key, Mixed value, BindableEvent e) {
			return false;
		}

		@Override
		public void preExecution(Environment env, BoundEvent.ActiveEvent activeEvent) {
			if(activeEvent.getUnderlyingEvent() instanceof MCSpawnerSpawnEvent) {
				// Entity object isn't in the world yet, so we need to inject it to make it accessible to functions
				MCEntity entity = ((MCSpawnerSpawnEvent) activeEvent.getUnderlyingEvent()).getEntity();
				Static.InjectEntity(entity);
			}
		}

		@Override
		public void postExecution(Environment env, BoundEvent.ActiveEvent activeEvent) {
			if(activeEvent.getUnderlyingEvent() instanceof MCSpawnerSpawnEvent) {
				MCEntity entity = ((MCSpawnerSpawnEvent) activeEvent.getUnderlyingEvent()).getEntity();
				Static.UninjectEntity(entity);
			}
		}
	}

}
