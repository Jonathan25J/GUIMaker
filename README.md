# GUIMaker
A Minecraft plugin that's make it possible to make GUI's
Use the example file to use as example for you work for your own GUI's

Short tutorial:
Placeholders:
<player>
<world>
<online>
<level>
<exp>
<ip>
<max_players>
<uuid>
<x>
<y>
<z>

Items:
name: displayname
lore: lore (list)
material: item/block
x: in gui than x parameter
y: in gui than y parameter
Permission: sets permission to use that item
Access-denied: sets message when player doesn't have the permission
Enchantments: possible to add enchantments (list)
Hide-enchantments: hide the enchantments from the item (true of false)
Keep-open: when you click on the item close the menu or keep it open (true or false)
Durability: sets the damage of the item (int)
Required-items: items that needs to be required to use this item (list)
Commands: execute commands with the item (list)
Head: when material = player_head than me(player) or username (head: me || head: Notch)

Example:
Example:
  Name: '&bHello <player>'
  Material: diamond_block || player_head
  Head: me
  X: 1
  Y: 3
  Lore:
  - 'user: <player>'
  - 'world: <world>'
  - 'level: <level>'
  - 'coords: x: <x>, y: <y>, z: <z>'
  Commands:
  - op, op <player>
  - console, stop
  - open, file.yml
  - give @s pufferfish
  Keep-open: true
  Permission: jonaqhan.example
  Access-denied: '&cYou dont have the right permission!'
  Durrability: 10
  Enchantments:
  - unbreaking, 3
  Hide-enchantments: true
 


# Inspired of
ChestCommands plugin from FiloGhost
https://dev.bukkit.org/projects/chest-commands
