package dev.mattidragon.customdefaultworldpreset;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricInit implements ModInitializer {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("default_world_preset.txt");

    @Override
    public void onInitialize() {
        CustomDefaultWorldPreset.init(CONFIG_PATH);
    }
}