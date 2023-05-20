package io.github.mattidragon.customdefaultworldpreset.mixin;

import io.github.mattidragon.customdefaultworldpreset.CustomDefaultWorldTypes;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPropertiesHandler.class)
public class ServerPropertiesHandlerMixin {
    @Redirect(method = "<init>",
            at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/util/registry/RegistryKey;"))
    private RegistryKey<WorldPreset> defaultWorldTypes$replaceDefault() {
        return CustomDefaultWorldTypes.getConfig();
    }

    // Also override the fallback when invalid for consistency
    @SuppressWarnings("unused")
    @Mixin(targets = "net.minecraft.server.dedicated.ServerPropertiesHandler$WorldGenProperties")
    private static class WorldGenPropertiesMixin {
        @Redirect(method = "createGeneratorOptions",
                at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/util/registry/RegistryKey;"))
        private RegistryKey<WorldPreset> defaultWorldTypes$replaceDefault() {
            return CustomDefaultWorldTypes.getConfig();
        }
    }
}
