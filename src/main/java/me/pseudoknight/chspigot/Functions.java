package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
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
            return "array {playerName, isCollideable} Sets whether the player can collide with other entities.";
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
            return "array {playerName} Gets whether the player can collide with other entities.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
        
    }
 

}
