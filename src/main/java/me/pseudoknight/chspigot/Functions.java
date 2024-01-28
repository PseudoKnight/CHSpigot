package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.*;
import com.laytonsmith.core.compiler.CompilerEnvironment;
import com.laytonsmith.core.compiler.CompilerWarning;
import com.laytonsmith.core.compiler.FileOptions;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigCompileException;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.natives.interfaces.Mixed;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.List;
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
	public static class respawn extends AbstractFunction implements Optimizable {

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
			return "void {[player]} Respawns the player immediately. Deprecated for pforce_respawn() in CommandHelper.";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

		@Override
		public Set<OptimizationOption> optimizationOptions() {
			return EnumSet.of(OptimizationOption.OPTIMIZE_DYNAMIC);
		}

		@Override
		public ParseTree optimizeDynamic(Target t, Environment env, Set<Class<? extends Environment.EnvironmentImpl>> envs,
				List<ParseTree> children, FileOptions fileOptions)
				throws ConfigCompileException, ConfigRuntimeException {
			env.getEnv(CompilerEnvironment.class).addCompilerWarning(fileOptions, new CompilerWarning(
					"respawn() is deprecated for pforce_respawn() in CommandHelper.", t, null));
			return null;
		}
	}

	@api
	public static class player_locale extends AbstractFunction implements Optimizable {

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
			return "string {[player]} Gets the player's locale language. Deprecated for plocale() in CommandHelper.";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

		@Override
		public Set<OptimizationOption> optimizationOptions() {
			return EnumSet.of(OptimizationOption.OPTIMIZE_DYNAMIC);
		}

		@Override
		public ParseTree optimizeDynamic(Target t, Environment env, Set<Class<? extends Environment.EnvironmentImpl>> envs,
				List<ParseTree> children, FileOptions fileOptions)
				throws ConfigCompileException, ConfigRuntimeException {
			env.getEnv(CompilerEnvironment.class).addCompilerWarning(fileOptions, new CompilerWarning(
					"player_locale() is deprecated for plocale() in CommandHelper", t, null));
			return null;
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
