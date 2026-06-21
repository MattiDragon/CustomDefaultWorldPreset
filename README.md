# Custom Default World Preset
This simple mod allows you to change the default world preset. 
This mod is intended for modpack developers who want a custom world without replacing the default and without making users change the setting.

To configure the default world preset you edit the `default_world_preset.txt` file in your config directory.
It should contain the id of the desired default preset. The `minecraft:` prefix may be omitted.
The configuration is read each time the key is used, so changes should show up be reopening the world creation screen.

To find the id of a world preset, you can add `-DcustomDefaultWorldPresets.dumpPresets=true` to your JVM options.
With this option enabled, whenever you open the Create World screen in singleplayer, all available presets will be listed in the log.

This mod also changes the default on dedicated servers. You need to ensure that the preset is loaded in the initial datapacks for this to work.
Most global datapack mods should work for this.

<details>
<summary>
Adding a custom preset / custom superflat options
</summary>

Here's a quick guide to adding custom world presets into the game.
1. Go to [Misode's generators' world preset page](https://misode.github.io/worldgen/world-preset).
2. Select your desired Minecraft version and choose a base from the Presets button.
3. Make any desired edits to the preset. You can even add dimensions and freely tweak their generation settings
4. Open the `...` menu and select `Save As`: Choose a fitting id for your preset, for example `my_modpack:default`
5. Once saved, got to the [world preset tag generator](https://misode.github.io/tags/world-preset)
6. Here, select `normal` from `Presets`. Add your preset to the tag. You can optionally reorder or remove others.
7. Now choose `... > Save As` again, this time the id has to be `minecraft:normal`.
8. Open up the project panel from the bottom left of the screen.
9. In the panel that opens, choose `... > Download data pack`. This downloads a datapack to your computer.
10. Next, you'll need some sort of global datapack loader, such as Paxi. 
    Follow their instructions on putting your datapack in the correct location.
11. Once done, you can set the id you chose in step 4 as the default preset. 

</details>