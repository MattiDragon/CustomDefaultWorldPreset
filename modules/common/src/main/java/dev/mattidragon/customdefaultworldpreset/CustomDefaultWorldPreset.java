package dev.mattidragon.customdefaultworldpreset;

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

public class CustomDefaultWorldPreset {
    public static final String MOD_ID = "custom_default_world_preset";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static Path configPath = null;

    static void init(Path configPath) {
        if (CustomDefaultWorldPreset.configPath != null) {
            throw new IllegalStateException("Already initialized");
        }
        CustomDefaultWorldPreset.configPath = configPath;

        if (!Files.exists(configPath)) {
            try {
                Files.writeString(configPath, "minecraft:normal", StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write default config for custom default world types", e);
            }
        }
    }

    public static ResourceKey<WorldPreset> getConfig() {
        try {
            var key = Files.readString(configPath);
            return ResourceKey.create(Registries.WORLD_PRESET, Identifier.parse(key.trim()));
        } catch (IOException | IdentifierException e) {
            throw new RuntimeException("Failed to read config for custom default world types", e);
        }
    }
}
