# CHSpigot

Gives access to methods available in Spigot but not Bukkit. 
These functions are more subject to changes and deprecation than typical CH functions,
so you'll need to keep the scripts that use this updated.

## Function Documentation

### Collision API

**get_collides_with_entities([player])**

Gets whether the player can collide with other entities. Returns boolean.

**set_collides_with_entities([player], isCollideable)**

Sets whether the player can collide with other entities.

### Arrow API

**get_arrow_damage(entityID)**

Gets the damage for the specified arrow entityID. Returns double.

**set_arrow_damage(entityID, double)**

Sets the damage the specified arrow entity will do.

### Particles API

**play_effect(locationArray, effect, [effectArray])**

Plays the specified particle effect to any nearby players.

Effect List:

SMOKE*, STEP_SOUND*, POTION_BREAK*, ENDER_SIGNAL*, MOBSPAWNER_FLAMES*, FIREWORKS_SPARK, CRIT, MAGIC_CRIT, POTION_SWIRL, POTION_SWIRL_TRANSPARENT, SPELL, INSTANT_SPELL, WITCH_MAGIC, NOTE, PORTAL, FLYING_GLYPH, FLAME, LAVA_POP, FOOTSTEP, SPLASH, PARTICLE_SMOKE, EXPLOSION_HUGE, EXPLOSION_LARGE, EXPLOSION, VOID_FOG, SMALL_SMOKE, CLOUD, COLOURED_DUST, SNOWBALL_BREAK, WATERDRIP, LAVADRIP, SNOW_SHOVEL, SLIME, HEART, VILLAGER_THUNDERCLOUD, HAPPY_VILLAGER, LARGE_SMOKE, ITEM_BREAK, TILE_BREAK, TILE_DUST

\* Also available in make_effect()

The optional effectArray may contain one or more of the following indexes:<br/>
(Note: not all options will modify all the above effects)

**int 'id':** the item/block/data id for the effect<br/>
**int 'data':** the data value of the block/item for the effect<br/>
**float 'offsetX':** the starting random offset in the X axis<br/>
**float 'offsetY':** the starting random offset in the Y axis<br/>
**float 'offsetZ':** the starting random offset in the Z axis<br/>
**float 'speed':** the speed of the particles<br/>
**int 'particleCount':** the number of particles<br/>
**int 'radius':** the radius in which the particle(s) can be seen

Example:
```play_effect(pcursor(), PARTICLE_SMOKE, array(particleCount: 10))```

### Respawn API

**respawn([player])**

If the player is dead, it will respawn them.
