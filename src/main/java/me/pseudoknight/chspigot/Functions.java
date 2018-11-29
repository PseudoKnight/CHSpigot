package me.pseudoknight.chspigot;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.entities.MCArrow;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHLog;
import com.laytonsmith.core.MSVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Optimizable;
import com.laytonsmith.core.ParseTree;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.compiler.FileOptions;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CDouble;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREBadEntityException;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidWorldException;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigCompileException;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.natives.interfaces.Mixed;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
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
	public static class get_arrow_damage extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREBadEntityException.class, CRECastException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCEntity ent = Static.getEntity(args[0], t);
			if(ent instanceof MCArrow) {
				return new CDouble(((Arrow) ent.getHandle()).spigot().getDamage(), t);
			}

			throw new CREBadEntityException("The specified entity ID must be an arrow", t);
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
			return MSVersion.V3_3_1;
		}

	}

	@api
	public static class set_arrow_damage extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREBadEntityException.class, CRECastException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCEntity ent = Static.getEntity(args[0], t);
			if(ent instanceof MCArrow) {
				((Arrow) ent.getHandle()).spigot().setDamage(Static.getDouble(args[1], t));
				return CVoid.VOID;
			}

			throw new CREBadEntityException("The specified entity ID must be an arrow", t);
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
			return MSVersion.V3_3_1;
		}

	}
 
	@api
	public static class play_effect extends AbstractFunction implements Optimizable {

		// convert old mismatched particle effect names to particle types
		public enum OldEffect {
			MAGIC_CRIT(Particle.CRIT_MAGIC),
			POTION_SWIRL(Particle.SPELL_MOB),
			POTION_SWIRL_TRANSPARENT(Particle.SPELL_MOB_AMBIENT),
			INSTANT_SPELL(Particle.SPELL_INSTANT),
			WITCH_MAGIC(Particle.SPELL_WITCH),
			FLYING_GLYPH(Particle.ENCHANTMENT_TABLE),
			LAVA_POP(Particle.LAVA),
			SPLASH(Particle.WATER_SPLASH),
			PARTICLE_SMOKE(Particle.SMOKE_NORMAL),
			EXPLOSION(Particle.EXPLOSION_NORMAL),
			VOID_FOG(Particle.SUSPENDED_DEPTH),
			SMALL_SMOKE(Particle.SUSPENDED_DEPTH),
			COLOURED_DUST(Particle.FALLING_DUST),
			SNOWBALL_BREAK(Particle.SNOWBALL),
			WATERDRIP(Particle.DRIP_WATER),
			LAVADRIP(Particle.DRIP_LAVA),
			VILLAGER_THUNDERCLOUD(Particle.VILLAGER_ANGRY),
			HAPPY_VILLAGER(Particle.VILLAGER_HAPPY),
			LARGE_SMOKE(Particle.SMOKE_LARGE),
			TILE_BREAK(Particle.BLOCK_CRACK),
			TILE_DUST(Particle.BLOCK_DUST);

			private final Particle particle;

			OldEffect(Particle type) {
				particle = type;
			}

			Particle getParticle() {
				return particle;
			}
		}

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREFormatException.class, CRECastException.class, CREInvalidWorldException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed  exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCLocation l;
			Particle particle;
			CArray options = null;
			Player p = null;

			if(args[0] instanceof CArray) {
				l = ObjectGenerator.GetGenerator().location(args[0], null, t);
				try {
					particle = Particle.valueOf(args[1].val().toUpperCase());
				} catch(IllegalArgumentException ex) {
					try {
						particle = OldEffect.valueOf(args[1].val().toUpperCase()).getParticle();
					} catch(IllegalArgumentException ex2) {
						CHLog.GetLogger().e(CHLog.Tags.GENERAL, "Invalid particle: " + args[1].val(), t);
						return CVoid.VOID;
					}
				}
				if(args.length == 3) {
					options = Static.getArray(args[2], t);
				}
			} else {
				p = (Player) Static.GetPlayer(args[0], t).getHandle();
				l = ObjectGenerator.GetGenerator().location(args[1], null, t);
				try {
					particle = Particle.valueOf(args[2].val().toUpperCase());
				} catch(IllegalArgumentException ex) {
					try {
						particle = OldEffect.valueOf(args[2].val().toUpperCase()).getParticle();
					} catch(IllegalArgumentException ex2) {
						CHLog.GetLogger().e(CHLog.Tags.GENERAL, "Invalid particle: " + args[2].val(), t);
						return CVoid.VOID;
					}
				}
				if(args.length == 4) {
					options = Static.getArray(args[3], t);
				}
			}

			World w = (World) l.getWorld().getHandle();
			Location loc = new Location(w, l.getX(), l.getY(), l.getZ());

			if(options != null) {
				float offsetX = 0;
				float offsetY = 0;
				float offsetZ = 0;
				float speed = 1;
				int particleCount = 1;
				int radius = 0;
				if(options.containsKey("offsetX")){
					offsetX = Static.getDouble32(options.get("offsetX", t), t);
				}
				if(options.containsKey("offsetY")){
					offsetY = Static.getDouble32(options.get("offsetY", t), t);
				}
				if(options.containsKey("offsetZ")){
					offsetZ = Static.getDouble32(options.get("offsetZ", t), t);
				}
				if(options.containsKey("speed")){
					speed = Static.getDouble32(options.get("speed", t), t);
				}
				if(options.containsKey("particleCount")){
					particleCount = Static.getInt32(options.get("particleCount", t), t);
				}
				if(options.containsKey("radius")) {
					radius = Static.getInt32(options.get("radius", t), t);
				}
				if(options.containsKey("id") || options.containsKey("data")) {
					CHLog.GetLogger().w(CHLog.Tags.DEPRECATION, "Particle id and data is deprecated in play_effect()."
							+ " Consider using spawn_particle().", t);
				}

				if(p == null) {
					if(radius > 0) {
						World world = loc.getWorld();
						for(Player player : world.getPlayers()) {
							if(player.isOnline() && player.getLocation().distance(loc) < radius) {
								player.spawnParticle(particle, loc, particleCount, offsetX, offsetY, offsetZ, speed);
							}
						}
					} else {
						w.spawnParticle(particle, loc, particleCount, offsetX, offsetY, offsetZ, speed);
					}
				} else {
					p.spawnParticle(particle, loc, particleCount, offsetX, offsetY, offsetZ, speed);
				}

				return CVoid.VOID;
			}

			if(p == null) {
				w.spawnParticle(particle, loc, 1);
			} else {
				p.spawnParticle(particle, loc, 1);
			}
			return CVoid.VOID;
		}

		public String getName() {
			return "play_effect";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3, 4};
		}

		public String docs() {
			return "void {[player], locationArray, particle, [particleArray]} Plays the specified particle effect to any"
					+ " nearby players or specified player. Particle array may contain one or more of the following indexes: "
					+ "float offsetX, float offsetY, float offsetZ, float speed, int particleCount";
		}

		public Version since() {
			return MSVersion.V3_3_1;
		}

		@Override
		public ParseTree optimizeDynamic(Target t, List<ParseTree> children, FileOptions fileOptions) throws ConfigCompileException, ConfigRuntimeException {
			if(children.size() < 2) {
				return null;
			}
			Mixed c = null;
			if(children.get(1).getData() instanceof CString) {
				c = children.get(1).getData();
			} else if(children.get(2).getData() instanceof CString) {
				c = children.get(2).getData();
			}
			if(c != null) {
				try {
					OldEffect e = OldEffect.valueOf(c.val().toUpperCase());
					CHLog.GetLogger().w(CHLog.Tags.DEPRECATION, "The particle " + c.val() + " is deprecated for " + e.getParticle().name(), t);
				} catch(IllegalArgumentException ex) {
					// not deprecated
				}
			}
			return null;
		}

		@Override
		public Set<OptimizationOption> optimizationOptions() {
			return EnumSet.of(OptimizationOption.OPTIMIZE_DYNAMIC);
		}

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
