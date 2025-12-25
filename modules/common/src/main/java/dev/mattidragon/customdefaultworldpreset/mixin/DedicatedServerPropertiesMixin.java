package dev.mattidragon.customdefaultworldpreset.mixin;

import dev.mattidragon.customdefaultworldpreset.CustomDefaultWorldPreset;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DedicatedServerProperties.class)
public class DedicatedServerPropertiesMixin {
    @Redirect(method = "<init>",
            at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/level/levelgen/presets/WorldPresets;NORMAL:Lnet/minecraft/resources/ResourceKey;"))
    private ResourceKey<WorldPreset> defaultWorldTypes$replaceDefault() {
        return CustomDefaultWorldPreset.getConfig();
    }

    // Also override the fallback when invalid for consistency
    @SuppressWarnings("unused")
    @Mixin(targets = "net.minecraft.server.dedicated.DedicatedServerProperties$WorldDimensionData")
    private static class WorldDimensionDataMixin {
        @Redirect(method = "create",
                at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/level/levelgen/presets/WorldPresets;NORMAL:Lnet/minecraft/resources/ResourceKey;"))
        private ResourceKey<WorldPreset> defaultWorldTypes$replaceDefault() {
            return CustomDefaultWorldPreset.getConfig();
        }
    }
}
