# Custom Default World Preset
This simple fabric mod allows you to change the default world preset. 
This mod is intended for modpack developers who want a custom world without replacing the default and without making users change the setting.

To configure the default world preset you edit the `default_world_preset.txt` file in your config directory.
It should contain the id of the desired default preset. The `minecraft:` prefix may be omitted.
The configuration is read each time the key is used, so changes should show up be reopening the world creation screen.

This mod also changes the default on dedicated servers. You need to ensure that the preset is loaded in the initial datapacks for this to work.
Most global datapack mods should work for this.