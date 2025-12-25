package dev.mattidragon.customdefaultworldpreset;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

@Mod(CustomDefaultWorldPreset.MOD_ID)
public class NeoForgeInit {
    public NeoForgeInit() {
        CustomDefaultWorldPreset.init(FMLPaths.CONFIGDIR.get().resolve("default_world_preset.txt"));
    }
}
