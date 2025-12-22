package io.github.mattidragon.customdefaultworldpreset.mixin;

import io.github.mattidragon.customdefaultworldpreset.CustomDefaultWorldPreset;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Redirect(method = "show(Lnet/minecraft/client/MinecraftClient;Ljava/lang/Runnable;Lnet/minecraft/client/gui/screen/world/CreateWorldCallback;)V",
        at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/registry/RegistryKey;"))
    private static RegistryKey<WorldPreset> defaultWorldTypes$replaceDefault() {
        return CustomDefaultWorldPreset.getConfig();
    }
}
