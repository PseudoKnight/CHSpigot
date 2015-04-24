package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.abstraction.events.MCPlayerEvent;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.Map;

public class Events {
    public static String docs() {
        return "This augments CommandHelper with Spigot-specific events";
    }

    protected static class ItemDamageEvent implements MCPlayerEvent {
        PlayerItemDamageEvent e;

        public ItemDamageEvent(Event e) {
            this.e = (PlayerItemDamageEvent) e;
        }

        public MCItemStack getItem() {
            return new BukkitMCItemStack(e.getItem());
        }

        public int getDamage() {
            return e.getDamage();
        }

        public void setDamage(int damage) {
            e.setDamage(damage);
        }

        public boolean isCancelled() {
            return e.isCancelled();
        }

        public void setCancelled(boolean cancelled) {
            e.setCancelled(cancelled);
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

    @api
    public static class item_damage extends AbstractEvent {

        @Override
        public String getName() {
            return "item_damage";
        }

        @Override
        public String docs() {
            return "{player: <string match> | item: <item match>} "
                    + "This event is called when a player's item (like a tool) will take damage. "
                    + "Cancelling this event will prevent damage from being taken on items."
                    + "{player: The player | item: An item array of the item being damaged |"
                    + " damage: the amount of durability damage the item will be taking} "
                    + "{damage: change the amount of damage taken} "
                    + "{player|item|damage}";
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
            return CHVersion.V3_3_1;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent e) {
            ItemDamageEvent event = (ItemDamageEvent)e;

            if (key.equalsIgnoreCase("damage") && value instanceof CInt) {
                event.setDamage(Integer.parseInt(value.val()));
                event.getPlayer().updateInventory();
                return true;
            }

            return false;
        }

        @Override
        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            if (e instanceof ItemDamageEvent) {
                ItemDamageEvent event = (ItemDamageEvent)e;

                Prefilters.match(prefilter, "item", Static.ParseItemNotation(event.getItem()), Prefilters.PrefilterType.ITEM_MATCH);
                Prefilters.match(prefilter, "player", event.getPlayer().getName(), Prefilters.PrefilterType.MACRO);

                return true;
            }

            return false;
        }

        @Override
        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if (e instanceof ItemDamageEvent) {
                ItemDamageEvent event = (ItemDamageEvent) e;
                Map<String, Construct> map = evaluate_helper(e);

                map.put("player", new CString(event.getPlayer().getName(), Target.UNKNOWN));
                map.put("item", ObjectGenerator.GetGenerator().item(event.getItem(), Target.UNKNOWN));
                map.put("damage", new CInt(event.getDamage(), Target.UNKNOWN));

                return map;
            }
            throw new EventException("Cannot convert e to ItemDamageEvent");
        }

        @Override
        public void cancel(BindableEvent o, boolean state) {
            super.cancel(o, state);
            ItemDamageEvent e = (ItemDamageEvent)o;
            e.getPlayer().updateInventory();
        }

    }

}
