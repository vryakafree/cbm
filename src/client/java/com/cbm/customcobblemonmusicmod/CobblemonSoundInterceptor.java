package com.cbm.customcobblemonmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CobblemonSoundInterceptor {
    
    /**
     * Intercepts and modifies sound instances from Cobblemon with custom volume/pitch
     */
    public static SoundInstance interceptCobblemonSound(SoundInstance original) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            return original;
        }
        
        // Check if this is a Cobblemon sound
        Identifier soundId = original.getId();
        if (!soundId.getNamespace().equals("cobblemon")) {
            return original;
        }
        
        // Determine sound category and apply appropriate volume/pitch
        float newVolume = original.getVolume();
        float newPitch = original.getPitch();
        
        String soundPath = soundId.getPath();
        
        if (soundPath.contains("pokemon") && soundPath.contains("cry")) {
            // Pokemon cry sounds
            newVolume *= config.cobblemonPokemonCriesVolume;
            newPitch *= config.cobblemonPokemonCriesPitch;
        } else if (soundPath.contains("poke_ball") || soundPath.contains("pokeball")) {
            // Pokeball sounds
            newVolume *= config.cobblemonPokeballSoundsVolume;
            newPitch *= config.cobblemonPokeballSoundsPitch;
        } else if (soundPath.contains("battle") || soundPath.contains("fight")) {
            // Battle sounds
            newVolume *= config.cobblemonBattleSoundsVolume;
            newPitch *= config.cobblemonBattleSoundsPitch;
        } else {
            // General Cobblemon sounds
            newVolume *= config.cobblemonSoundsVolume;
            newPitch *= config.cobblemonSoundsPitch;
        }
        
        // Debug logging
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Intercepted Cobblemon sound: " + soundId + 
                " | Original vol: " + original.getVolume() + ", pitch: " + original.getPitch() + 
                " | Modified vol: " + newVolume + ", pitch: " + newPitch);
        }
        
        // Create modified sound instance
        return createModifiedSoundInstance(original, newVolume, newPitch);
    }
    
    /**
     * Creates a new sound instance with modified volume and pitch
     */
    private static SoundInstance createModifiedSoundInstance(SoundInstance original, float volume, float pitch) {
        // Create a new positioned sound instance with modified values
        return PositionedSoundInstance.master(
            SoundEvent.of(original.getId()),
            pitch,
            volume
        );
    }
    
    /**
     * Check if a sound ID belongs to Cobblemon
     */
    public static boolean isCobblemonSound(Identifier soundId) {
        return soundId.getNamespace().equals("cobblemon");
    }
    
    /**
     * Get the category of a Cobblemon sound for more specific control
     */
    public static String getCobblemonSoundCategory(Identifier soundId) {
        String path = soundId.getPath();
        
        if (path.contains("pokemon") && path.contains("cry")) {
            return "pokemon_cries";
        } else if (path.contains("poke_ball") || path.contains("pokeball")) {
            return "pokeball_sounds";
        } else if (path.contains("battle") || path.contains("fight")) {
            return "battle_sounds";
        } else {
            return "general_sounds";
        }
    }
}