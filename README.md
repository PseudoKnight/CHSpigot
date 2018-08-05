# CHSpigot

Gives access to API available in Spigot but not Bukkit.
These functions and events are more subject to changes and deprecation than typical CH functions,
so you'll need to keep the scripts that use this updated.

## Function Documentation

### Player API

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

## Event Documentation

### item_damage

This event is called when a player's item (like a tool) will take damage. Cancelling this event will prevent damage from being taken on items.

#### Prefilters

* **player:** [String Match][1]
* **itemname:** [String Match][2]

#### Event Data

* **player:** the player's name
* **item:** an item array of the item being damaged
* **damage:** the amount of durability damage the item will be taking

#### Mutable Fields

* **damage**

### entity_mount

This event is called when an entity mounts another entity.

#### Prefilters

* **type:** [Macro Match][3]
* **mounttype:** [Macro Match][3]

#### Event Data

* **type:** The type of entity that is mounting
* **id:** The UUID of the mounting entity
* **mounttype:** The type of entity that is mounted
* **mountid:** The UUID of the mounted entity

### entity_dismount

This event is called when an entity dismounts another entity. This event cannot be cancelled.

#### Prefilters

* **type:** [Macro Match][3]
* **mounttype:** [Macro Match][3]

#### Event Data

* **type:** The type of entity that is dismounting
* **id:** The UUID of the dismounting entity
* **mounttype:** The type of entity that is mounted
* **mountid:** The UUID of the mounted entity

### spawner_spawn

This event is called when a spawner spawns an entity.

#### Prefilters

* **type:** [String Match][1]

#### Event Data

* **type:** The type of entity that is spawning
* **id:** The UUID of the spawning entity
* **location:** The location the entity spawned
* **spawner:** The location of the spawner that spawned the entity


[1]: http://wiki.sk89q.com/wiki/CommandHelper/Events/Prefilters#String_Match
[2]: http://wiki.sk89q.com/wiki/CommandHelper/Events/Prefilters#Item_Match
[3]: http://wiki.sk89q.com/wiki/CommandHelper/Events/Prefilters#Macro_Match
