package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCobblemonMusicMod implements ModInitializer {
    public static final String MOD_ID = "tdsound";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Sound Events - Only victory, evolution congratulations, catch congratulations, and flee
    public static final SoundEvent VICTORY_MUSIC = registerSoundEvent("victory");
    public static final SoundEvent EVO_CONGRAT_MUSIC = registerSoundEvent("evo_congrat");
    public static final SoundEvent CATCH_CONGRAT_MUSIC = registerSoundEvent("catch_congrat");
    public static final SoundEvent FLEE_MUSIC = registerSoundEvent("flee");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Custom Congrat Sound For Cobblemon");
        
        // Initialize configuration
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        LOGGER.info("Configuration loaded successfully");
        
        // Register debug commands
        CustomCobblemonMusicModCommands.register();
        
        LOGGER.info("Registered sound events:");
        LOGGER.info("- victory.ogg (plays for " + (config.victoryMusicDuration / 1000) + " seconds after winning)");
        LOGGER.info("- evo_congrat.ogg (after evolution completes)");
        LOGGER.info("- catch_congrat.ogg (when Pokemon is caught)");
        LOGGER.info("- flee.ogg (when player flees from battle)");
        LOGGER.info("Ready for Cobblemon integration!");
        LOGGER.info("Use '/cobblecongrat status' to check mod status");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        SoundEvent soundEvent = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, soundEvent);
        LOGGER.info("Registered sound event: " + id);
        return soundEvent;
    }
}