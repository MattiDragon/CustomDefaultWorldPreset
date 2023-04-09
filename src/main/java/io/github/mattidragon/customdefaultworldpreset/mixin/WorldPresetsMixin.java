package io.github.mattidragon.customdefaultworldpreset.mixin;

import io.github.mattidragon.customdefaultworldpreset.CustomDefaultWorldTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldPresets.class)
public class WorldPresetsMixin {
    @Redirect(method = {"createDemoOptions", "getDefaultOverworldOptions"},
            require = 2,
            at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/registry/RegistryKey;"))
    private static RegistryKey<WorldPreset> defaultWorldTypes$replaceDefault() {
        return CustomDefaultWorldTypes.getConfig();
    }
}
