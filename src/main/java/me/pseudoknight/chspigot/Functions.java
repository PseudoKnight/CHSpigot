package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCArrow;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CDouble;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

/**
 *
 * @author PseudoKnight
 */
public class Functions {
    public static String docs() {
        return "These functions provide a methodscript interface for Spigot specific methods.";
    }
 
    @api
    public static class set_collides_with_entities extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.PlayerOfflineException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            Player p = (Player) Static.GetPlayer(args[0], t).getHandle();                
            p.spigot().setCollidesWithEntities(Static.getBoolean(args[1]));
            return new CVoid(t);
        }

        public String getName() {
            return "set_collides_with_entities"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{2};
        }

        public String docs() {
            return "void {playerName, isCollideable} Sets whether the player can collide with other entities.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
    
    @api
    public static class get_collides_with_entities extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.PlayerOfflineException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            Player p = (Player) Static.GetPlayer(args[0], t).getHandle();         
            return new CBoolean(p.spigot().getCollidesWithEntities(), t);
        }

        public String getName() {
            return "get_collides_with_entities"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{1};
        }

        public String docs() {
            return "boolean {playerName} Gets whether the player can collide with other entities.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
    
    @api
    public static class get_arrow_damage extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.BadEntityException, ExceptionType.CastException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            MCEntity ent = Static.getEntity(Static.getInt32(args[0], t), t);
            if(ent instanceof MCArrow) {
                return new CDouble(((Arrow) ent).spigot().getDamage(), t);
            }
            
            throw new ConfigRuntimeException("The specified entity ID must be an arrow", ExceptionType.BadEntityException, t);
        }

        public String getName() {
            return "get_arrow_damage"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{1};
        }

        public String docs() {
            return "double {entityID} Gets the damage for the specified arrow entityID.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
    
    @api
    public static class set_arrow_damage extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.BadEntityException, ExceptionType.CastException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            MCEntity ent = Static.getEntity(Static.getInt32(args[0], t), t);
            if(ent instanceof MCArrow) {
                ((Arrow) ent).spigot().setDamage(Static.getDouble(args[1], t));
                return new CVoid(t);
            }
            
            throw new ConfigRuntimeException("The specified entity ID must be an arrow", ExceptionType.BadEntityException, t);
        }

        public String getName() {
            return "set_arrow_damage"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{2};
        }

        public String docs() {
            return "void {entityID, double} Sets the damage the specified arrow entity will do.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
 
    @api
    public static class play_effect extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.FormatException, ExceptionType.CastException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct  exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            MCLocation l = ObjectGenerator.GetGenerator().location(args[0], null, t);
            World w = (World) l.getWorld().getHandle();
            Effect e = Effect.valueOf(args[1].val().toUpperCase());
            Location loc;
            loc = new Location(w, l.getX(), l.getY(), l.getZ());
            if(args.length > 2) {
                if(!(args[2] instanceof CArray)) {
                    throw new ConfigRuntimeException("The third parameter must be an array", ExceptionType.CastException, t);
                }
                
                CArray options = Static.getArray(args[2], t);
                int id = 0;
                int data = 0;
                float offsetX = 0;
                float offsetY = 0;
                float offsetZ = 0;
                float speed = 1;
                int particleCount = 1;
                int radius = 16;
                if(options.containsKey("id")){
                    id = Static.getInt32(options.get("id"), t);
                }
                if(options.containsKey("data")){
                    data = Static.getInt32(options.get("data"), t);
                }
                if(options.containsKey("offsetX")){
                    offsetX = Static.getDouble32(options.get("offsetX"), t);
                }
                if(options.containsKey("offsetY")){
                    offsetY = Static.getDouble32(options.get("offsetY"), t);
                }
                if(options.containsKey("offsetZ")){
                    offsetZ = Static.getDouble32(options.get("offsetZ"), t);
                }
                if(options.containsKey("speed")){
                    speed = Static.getDouble32(options.get("speed"), t);
                }
                if(options.containsKey("particleCount")){
                    particleCount = Static.getInt32(options.get("particleCount"), t);
                }
                if(options.containsKey("radius")){
                    radius = Static.getInt32(options.get("radius"), t);
                }
                
                w.spigot().playEffect(loc, e, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
                
                return new CVoid(t);
            }
            
            w.spigot().playEffect(loc, e);
            return new CVoid(t);
        }

        public String getName() {
            return "play_effect"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{2,3};
        }

        public String docs() {
            return "void {locationArray, effect, [effectArray]} Plays the specified particle effect to any nearby players. "
                    + "Effect array may contain one or more of the following indexes: "
                    + "int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
    
    @api
    public static class respawn extends AbstractFunction {

        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[]{ExceptionType.PlayerOfflineException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false; 
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            Player p = (Player) Static.GetPlayer(args[0], t).getHandle();
            p.spigot().respawn();
            return new CVoid(t);
        }

        public String getName() {
            return "respawn"; 
        }

        public Integer[] numArgs() {
            return new Integer[]{1};
        }

        public String docs() {
            return "void {player} Respawns the player immediately.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }

}
