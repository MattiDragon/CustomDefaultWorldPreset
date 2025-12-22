package io.github.mattidragon.customdefaultworldpreset;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.IdentifierException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CustomDefaultWorldPreset implements ModInitializer {
    public static final String MOD_ID = "custom_default_world_preset";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("default_world_preset.txt");

    @Override
    public void onInitialize() {
        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.writeString(CONFIG_PATH, "minecraft:normal", StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write default config for custom default world types", e);
            }
        }
    }

    public static ResourceKey<WorldPreset> getConfig() {
        try {
            var key = Files.readString(CONFIG_PATH);
            return ResourceKey.create(Registries.WORLD_PRESET, Identifier.parse(key.trim()));
        } catch (IOException | IdentifierException e) {
            throw new RuntimeException("Failed to read config for custom default world types", e);
        }
    }
}