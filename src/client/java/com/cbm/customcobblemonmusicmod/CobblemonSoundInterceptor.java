package com.cbm.customcobblemonmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CobblemonSoundInterceptor {
    
    /**
     * Creates a modified Cobblemon sound with custom volume/pitch when playing manually
     */
    public static SoundInstance createModifiedCobblemonSound(Identifier soundId, float volume, float pitch) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            return PositionedSoundInstance.master(SoundEvent.of(soundId), pitch, volume);
        }
        
        // Check if this is a Cobblemon sound
        if (!soundId.getNamespace().equals("cobblemon")) {
            return PositionedSoundInstance.master(SoundEvent.of(soundId), pitch, volume);
        }
        
        // Determine sound category and apply appropriate volume/pitch
        float newVolume = volume;
        float newPitch = pitch;
        
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
            CustomCobblemonMusicMod.LOGGER.info("Creating modified Cobblemon sound: " + soundId + 
                " | Original vol: " + volume + ", pitch: " + pitch + 
                " | Modified vol: " + newVolume + ", pitch: " + newPitch);
        }
        
        // Create modified sound instance
        return PositionedSoundInstance.master(SoundEvent.of(soundId), newPitch, newVolume);
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
    
    /**
     * Play a Cobblemon sound with custom volume/pitch control
     */
    public static void playCobblemonSound(Identifier soundId, float volume, float pitch) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            SoundInstance sound = createModifiedCobblemonSound(soundId, volume, pitch);
            client.getSoundManager().play(sound);
        }
    }
    
    /**
     * Play a Cobblemon sound with default volume/pitch (1.0, 1.0)
     */
    public static void playCobblemonSound(Identifier soundId) {
        playCobblemonSound(soundId, 1.0f, 1.0f);
    }
}