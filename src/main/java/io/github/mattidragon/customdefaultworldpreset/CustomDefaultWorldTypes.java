package io.github.mattidragon.customdefaultworldpreset;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.world.gen.WorldPreset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CustomDefaultWorldTypes implements ModInitializer {
	public static final String MOD_ID = "custom_default_world_types";
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

	public static RegistryKey<WorldPreset> getConfig() {
		try {
			var key = Files.readString(CONFIG_PATH);
			return RegistryKey.of(RegistryKeys.WORLD_PRESET, new Identifier(key.trim()));
		} catch (IOException | InvalidIdentifierException e) {
			throw new RuntimeException("Failed to read config for custom default world types", e);
		}
	}
}