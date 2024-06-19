# CHSpigot

Gives access to API available in Spigot but not Bukkit. As CommandHelper now depends on Spigot,
these functions and events are subject to be moved into CommandHelper at some point.

## Recommended Builds

* [**CHSpigot 2.0.8 for Spigot 1.16.5+**](https://github.com/PseudoKnight/CHSpigot/releases/tag/v2.0.8) (CommandHelper 3.3.5 #392 or later) [Documentation](https://github.com/PseudoKnight/CHSpigot/blob/master/README.md)
* [**CHSpigot 2.0.4 for Spigot 1.13.2 - 1.16.5**](https://github.com/PseudoKnight/CHSpigot/releases/tag/v2.0.4) (CommandHelper 3.3.4) [Documentation](https://github.com/PseudoKnight/CHSpigot/blob/v2.0.4/README.md)
* [**CHSpigot 1.6.0a for Spigot 1.12.2**](https://github.com/PseudoKnight/CHSpigot/releases/tag/v1.6.0a) (CommandHelper 3.3.2) [Documentation](https://github.com/PseudoKnight/CHSpigot/blob/v1.6.0a/README.md)

## Function Documentation

### Player API

**get_hidden_players([player])**

Returns an array of players who cannot see the player.

## Event Documentation

### item_damage

This event is called when a player's item (like a tool) will take damage. Cancelling this event will prevent damage from being taken on items.

#### Prefilters

* **player:** String Match
* **itemname:** String Match

#### Event Data

* **player:** the player's name
* **item:** an item array of the item being damaged
* **damage:** the amount of durability damage the item will be taking

#### Mutable Fields

* **damage**

### entity_mount

This event is called when an entity mounts another entity.

#### Prefilters

* **type:** Macro Match
* **mounttype:** Macro Match

#### Event Data

* **type:** The type of entity that is mounting
* **id:** The UUID of the mounting entity
* **mounttype:** The type of entity that is mounted
* **mountid:** The UUID of the mounted entity

### entity_dismount

This event is called when an entity dismounts another entity. This event cannot be cancelled.

#### Prefilters

* **type:** Macro Match
* **mounttype:** Macro Match

#### Event Data

* **type:** The type of entity that is dismounting
* **id:** The UUID of the dismounting entity
* **mounttype:** The type of entity that is mounted
* **mountid:** The UUID of the mounted entity

### spawner_spawn

This event is called when a spawner spawns an entity.

#### Prefilters

* **type:** String Match

#### Event Data

* **type:** The type of entity that is spawning
* **id:** The UUID of the spawning entity
* **location:** The location the entity spawned
* **spawner:** The location of the spawner that spawned the entity
