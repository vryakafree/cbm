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

    // Sound Events - Complete Pokemon music system
    public static final SoundEvent BATTLE_MUSIC = registerSoundEvent("battle_song");
    public static final SoundEvent STRONG_BATTLE_MUSIC = registerSoundEvent("strong_battle_song");
    public static final SoundEvent PANIC_MUSIC = registerSoundEvent("panic_song");
    public static final SoundEvent VICTORY_MUSIC = registerSoundEvent("victory");
    public static final SoundEvent EVO_MUSIC = registerSoundEvent("evo");
    public static final SoundEvent EVO_CONGRAT_MUSIC = registerSoundEvent("evo_congrat");
    public static final SoundEvent CATCH_CONGRAT_MUSIC = registerSoundEvent("catch_congrat");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Custom Cobblemon Music Mod");
        
        // Register debug commands
        CustomCobblemonMusicModCommands.register();
        
        LOGGER.info("Registered sound events:");
        LOGGER.info("- battle_song.ogg (loops during normal battles)");
        LOGGER.info("- strong_battle_song.ogg (when opponent 15+ levels higher)");
        LOGGER.info("- panic_song.ogg (when Pokemon health < 20%)");
        LOGGER.info("- victory.ogg (plays for 7 seconds after winning)");
        LOGGER.info("- evo.ogg (during Pokemon evolution)");
        LOGGER.info("- evo_congrat.ogg (after evolution completes)");
        LOGGER.info("- catch_congrat.ogg (when Pokemon is caught)");
        LOGGER.info("Ready for Cobblemon integration!");
        LOGGER.info("Use '/cobblemusic status' to check mod status");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}