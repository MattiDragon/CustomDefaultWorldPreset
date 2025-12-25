package dev.mattidragon.customdefaultworldpreset.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.mattidragon.customdefaultworldpreset.CustomDefaultWorldPreset;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(WorldCreationUiState.class)
public class WorldCreationUiStateMixin {
    @Inject(method = "updatePresetLists",
            at = @At("TAIL"),
            // This is an additional debug feature that might break at some point
            // We don't want to break the rest of the mod for this
            require = 0)
    private void dumpAvailablePresets(CallbackInfo ci, @Local Registry<WorldPreset> registry) {
        if (!Boolean.getBoolean("customDefaultWorldPresets.dumpPresets")) return;

        var message = new StringBuilder("Available world presets:");

        registry.listElements()
                .map(WorldCreationUiState.WorldTypeEntry::new)
                .forEach(entry -> Optional.ofNullable(entry.preset())
                        .flatMap(Holder::unwrapKey)
                        .map(ResourceKey::identifier)
                        .ifPresent(id -> message.append("\n - ")
                                .append(id)
                                .append(" (")
                                .append(entry.describePreset().getString())
                                .append(")")));

        CustomDefaultWorldPreset.LOGGER.info(message.toString());
    }
}
