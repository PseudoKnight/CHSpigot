package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.*;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.natives.interfaces.Mixed;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 *
 * @author PseudoKnight
 */
public class Functions {
	public static String docs() {
		return "These functions provide a methodscript interface for Spigot specific methods.";
	}

	@api
	public static class respawn extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREPlayerOfflineException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			Player.Spigot p;
			if(args.length == 0) {
				p = ((Player) environment.getEnv(CommandHelperEnvironment.class).GetPlayer().getHandle()).spigot();
			} else {
				p = ((Player) Static.GetPlayer(args[0], t).getHandle()).spigot();
			}
			p.respawn();
			return CVoid.VOID;
		}

		public String getName() {
			return "respawn";
		}

		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		public String docs() {
			return "void {[player]} Respawns the player immediately.";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

	}

	@api
	public static class player_locale extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREPlayerOfflineException.class};
		}

		public boolean isRestricted() {
			return false;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			Player p;
			if(args.length == 0) {
				p = (Player) environment.getEnv(CommandHelperEnvironment.class).GetPlayer().getHandle();
			} else {
				p = (Player) Static.GetPlayer(args[0], t).getHandle();
			}
			return new CString(p.getLocale(), t);
		}

		public String getName() {
			return "player_locale";
		}

		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		public String docs() {
			return "string {[player]} Gets the player's locale language.";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

	}

	@api
	public static class get_hidden_players extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREPlayerOfflineException.class};
		}

		public boolean isRestricted() {
			return false;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			Player.Spigot p;
			if(args.length == 0) {
				p = ((Player) environment.getEnv(CommandHelperEnvironment.class).GetPlayer().getHandle()).spigot();
			} else {
				p = ((Player) Static.GetPlayer(args[0], t).getHandle()).spigot();
			}
			Set<Player> players = p.getHiddenPlayers();
			CArray hidden = new CArray(t);
			for(Player pl : players) {
				hidden.push((CString) pl, t);
			}
			return hidden;
		}

		public String getName() {
			return "get_hidden_players";
		}

		public Integer[] numArgs() {
			return new Integer[]{0, 1};
		}

		public String docs() {
			return "array {[player]} Returns an array of players that the player cannot see.";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

	}

}
