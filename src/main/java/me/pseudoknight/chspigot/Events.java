package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.Prefilters.PrefilterType;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import java.util.Map;
import org.bukkit.event.player.PlayerItemDamageEvent;

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
            return "{player: <string match> | item: <item match>} "
                    + "This event is called when a player's item (like a tool) will take damage."
                    + "{player: The player | item: An item array representing "
                    + "the item being picked up | damage: the amount of durability damage "
                    + "this item will be taking} "
                    + "{damage: change the amount of damage taken} "
                    + "{player|item|damage}";
        }

        @Override
        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            if (e instanceof PlayerItemDamageEvent) {
                PlayerItemDamageEvent event = (PlayerItemDamageEvent)e;

                Prefilters.match(prefilter, "item", Static.ParseItemNotation((MCItemStack)event.getItem()), Prefilters.PrefilterType.ITEM_MATCH);
                Prefilters.match(prefilter, "player", event.getPlayer().getName(), Prefilters.PrefilterType.MACRO);

                return true;
            }

            return false;
        }

        @Override
        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        @Override
        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if (e instanceof PlayerItemDamageEvent) {
                PlayerItemDamageEvent event = (PlayerItemDamageEvent) e;
                Map<String, Construct> map = evaluate_helper(e);

                //Fill in the event parameters
                map.put("player", new CString(event.getPlayer().getName(), Target.UNKNOWN));
                map.put("item", ObjectGenerator.GetGenerator().item((MCItemStack)event.getItem(), Target.UNKNOWN));
                map.put("damage", new CInt(event.getDamage(), Target.UNKNOWN));

                return map;
            } else {
                throw new EventException("Cannot convert e to PlayerItemDamageEvent");
            }
        }

        @Override
        public boolean isCancellable(BindableEvent e) {
            if (e instanceof PlayerItemDamageEvent) {
                return true;
            }
            return false;
        }

        @Override
        public void cancel(BindableEvent e, boolean state) { // throws EventException {
            if (e instanceof PlayerItemDamageEvent) {
                PlayerItemDamageEvent event = (PlayerItemDamageEvent) e;
                event.setCancelled(state);
                return;
            }
            //throw new EventException("Cannot convert e to PlayerItemDamageEvent");
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent e) {
            PlayerItemDamageEvent event = (PlayerItemDamageEvent)e;

            if (key.equalsIgnoreCase("damage")) {
                if (value instanceof CInt) {
                    event.setDamage(Integer.parseInt(value.val()));
                    return true;
                }

                return true;
            }

            return false;
        }

        @Override
        public Version since() {
            return CHVersion.V3_3_1;
        }

    }
}
