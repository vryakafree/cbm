package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCobblemonMusicMod implements ModInitializer {
    public static final String MOD_ID = "customcobblemonmusicmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Sound Events - Only victory, evolution, and catch music (battle music handled by resource pack)
    public static final SoundEvent VICTORY_MUSIC = registerSoundEvent("victory");
    public static final SoundEvent EVO_MUSIC = registerSoundEvent("evo");
    public static final SoundEvent EVO_CONGRAT_MUSIC = registerSoundEvent("evo_congrat");
    public static final SoundEvent CATCH_CONGRAT_MUSIC = registerSoundEvent("catch_congrat");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Custom Cobblemon Music Mod");
        
        // Initialize configuration
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        LOGGER.info("Configuration loaded successfully");
        
        // Register debug commands
        CustomCobblemonMusicModCommands.register();
        
        LOGGER.info("Registered sound events:");
        LOGGER.info("- victory.ogg (plays for " + (config.victoryMusicDuration / 1000) + " seconds after winning with fade out)");
        LOGGER.info("- evo.ogg (during Pokemon evolution)");
        LOGGER.info("- evo_congrat.ogg (after evolution completes)");
        LOGGER.info("- catch_congrat.ogg (when Pokemon is caught)");
        LOGGER.info("Battle music is handled by Cobblemon's native system or resource packs");
        LOGGER.info("Ready for Cobblemon integration!");
        LOGGER.info("Use '/cobblemusic status' to check mod status");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}