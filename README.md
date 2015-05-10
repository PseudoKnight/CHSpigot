# CHSpigot

Gives access to methods available in Spigot but not Bukkit. 
These functions are more subject to changes and deprecation than typical CH functions,
so you'll need to keep the scripts that use this updated.

## Function Documentation

### Player API

**get_collides_with_entities([player])**

Gets whether the player can collide with other entities. Returns boolean.

**set_collides_with_entities([player], isCollideable)**

Sets whether the player can collide with other entities.

**respawn([player])**

If the player is dead, it will respawn them.

**player_locale([player])**

Returns the language local for a player. This always returns the default (en_US) on player_join. After the player has joined this should reflect their client setting.

**get_hidden_players([player])**

Returns an array of players who cannot see the player.

### Arrow API

**get_arrow_damage(entityID)**

Gets the damage for the specified arrow entityID. Returns double.

**set_arrow_damage(entityID, double)**

Sets the damage the specified arrow entity will do.

### Particles API

**play_effect([player], locationArray, effect, [effectArray])**

Plays the specified particle effect to any nearby players or just the specified player, if provided. If you're on Spigot 1.8, make_effect() can do most of this now. I recommend that you use make_effect() where possible.

Effect List:

SMOKE, STEP_SOUND, POTION_BREAK, ENDER_SIGNAL, MOBSPAWNER_FLAMES, FIREWORKS_SPARK, CRIT, MAGIC_CRIT, POTION_SWIRL, POTION_SWIRL_TRANSPARENT, SPELL, INSTANT_SPELL, WITCH_MAGIC, NOTE, PORTAL, FLYING_GLYPH, FLAME, LAVA_POP, FOOTSTEP, SPLASH, PARTICLE_SMOKE, EXPLOSION_HUGE, EXPLOSION_LARGE, EXPLOSION, VOID_FOG, SMALL_SMOKE, CLOUD, COLOURED_DUST, SNOWBALL_BREAK, WATERDRIP, LAVADRIP, SNOW_SHOVEL, SLIME, HEART, VILLAGER_THUNDERCLOUD, HAPPY_VILLAGER, LARGE_SMOKE, ITEM_BREAK, TILE_BREAK, TILE_DUST

The optional effectArray may contain one or more of the following indexes:<br/>
(Note: not all options will modify all the above effects)

**int 'id':** the item/block/data id for the effect<br/>
**int 'data':** the data value of the block/item for the effect (requires a valid data value for 1.8)<br/>
**float 'offsetX':** the starting random offset in the X axis<br/>
**float 'offsetY':** the starting random offset in the Y axis<br/>
**float 'offsetZ':** the starting random offset in the Z axis<br/>
**float 'speed':** the speed of the particles<br/>
**int 'particleCount':** the number of particles<br/>
**int 'radius':** the radius in which the particle(s) can be seen

Example:
```play_effect(pcursor(), PARTICLE_SMOKE, array(particleCount: 10))```

## Event Documentation

### item_damage

This event is called when a player's item (like a tool) will take damage. Cancelling this event will prevent damage from being taken on items.

#### Prefilters

* **player:** [String Match][1]
* **item:** [Item Match][2]

#### Event Data

* **player:** the player's name
* **item:** an item array of the item being damaged
* **damage:** the amount of durability damage the item will be taking

#### Mutable Fields

* **damage**


[1]: http://wiki.sk89q.com/wiki/CommandHelper/Events/Prefilters#String_Match
[2]: http://wiki.sk89q.com/wiki/CommandHelper/Events/Prefilters#Item_Match
