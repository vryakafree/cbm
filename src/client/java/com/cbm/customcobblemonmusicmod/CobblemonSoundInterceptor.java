package com.cbm.customcobblemonmusicmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

public class CobblemonSoundInterceptor {
    
    /**
     * Creates a modified Cobblemon sound with custom volume/pitch when playing manually
     */
    public static SoundInstance createModifiedCobblemonSound(Identifier soundId, float volume, float pitch) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            return createOriginalSoundInstance(soundId, volume, pitch);
        }
        
        // Check if this is a Cobblemon sound
        if (!isCobblemonSound(soundId)) {
            return createOriginalSoundInstance(soundId, volume, pitch);
        }
        
        // Determine sound category and apply appropriate volume/pitch
        float newVolume = volume;
        float newPitch = pitch;
        
        String soundPath = soundId.getPath();
        
        // Comprehensive mapping based on Cobblemon sounds.json structure
        if (soundPath.contains("pokemon") && soundPath.contains("cry")) {
            // Pokemon cry sounds (e.g., "pokemon.bulbasaur.cry")
            newVolume *= config.cobblemonPokemonCriesVolume;
            newPitch *= config.cobblemonPokemonCriesPitch;
        } else if (soundPath.contains("poke_ball") || soundPath.contains("pokeball")) {
            // Pokeball sounds (capture, shake, bounce, etc.)
            newVolume *= config.cobblemonPokeballSoundsVolume;
            newPitch *= config.cobblemonPokeballSoundsPitch;
        } else if (soundPath.contains("move.")) {
            // Move sounds (e.g., "move.tackle", "move.thunder_shock")
            newVolume *= config.cobblemonMoveSoundsVolume;
            newPitch *= config.cobblemonMoveSoundsPitch;
        } else if (soundPath.contains("impact.")) {
            // Impact sounds (e.g., "impact.tackle", "impact.thunder_shock")
            newVolume *= config.cobblemonImpactSoundsVolume;
            newPitch *= config.cobblemonImpactSoundsPitch;
        } else if (soundPath.contains("evolution.")) {
            // Evolution sounds (e.g., "evolution.full", "evolution.start")
            newVolume *= config.cobblemonEvolutionSoundsVolume;
            newPitch *= config.cobblemonEvolutionSoundsPitch;
        } else if (soundPath.contains("battle.")) {
            // Battle-specific sounds
            newVolume *= config.cobblemonBattleSoundsVolume;
            newPitch *= config.cobblemonBattleSoundsPitch;
        } else if (soundPath.contains("pc.")) {
            // PC sounds (on, off, grab, drop, release, click)
            newVolume *= config.cobblemonPCSoundsVolume;
            newPitch *= config.cobblemonPCSoundsPitch;
        } else if (soundPath.contains("entity/villager/work_nurse")) {
            // Nurse Joy sounds
            newVolume *= config.cobblemonSoundsVolume;
            newPitch *= config.cobblemonSoundsPitch;
        } else if (soundPath.contains("ui.")) {
            // UI sounds (menu, button clicks, etc.)
            newVolume *= config.cobblemonUISoundsVolume;
            newPitch *= config.cobblemonUISoundsPitch;
        } else if (soundPath.contains("item.")) {
            // Item sounds (potions, berries, etc.)
            newVolume *= config.cobblemonItemSoundsVolume;
            newPitch *= config.cobblemonItemSoundsPitch;
        } else if (soundPath.contains("block.")) {
            // Block sounds (healing machine, etc.)
            newVolume *= config.cobblemonBlockSoundsVolume;
            newPitch *= config.cobblemonBlockSoundsPitch;
        } else {
            // General Cobblemon sounds (fallback)
            newVolume *= config.cobblemonSoundsVolume;
            newPitch *= config.cobblemonSoundsPitch;
        }
        
        // Ensure values are within valid ranges
        newVolume = Math.max(0.0f, Math.min(1.0f, newVolume));
        newPitch = Math.max(0.5f, Math.min(2.0f, newPitch));
        
        // Debug logging
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Creating modified Cobblemon sound: " + soundId + 
                " | Original vol: " + volume + ", pitch: " + pitch + 
                " | Modified vol: " + newVolume + ", pitch: " + newPitch +
                " | Category: " + getCobblemonSoundCategory(soundId));
        }
        
        try {
            // Get the existing SoundEvent from the registry instead of creating a new one
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);
            if (soundEvent == null) {
                CustomCobblemonMusicMod.LOGGER.warn("SoundEvent not found in registry for: " + soundId);
                return createOriginalSoundInstance(soundId, newVolume, newPitch);
            }
            
            // Create modified sound instance with proper category
            return createAppropriateSoundInstance(soundEvent, newVolume, newPitch, soundPath);
        } catch (Exception e) {
            CustomCobblemonMusicMod.LOGGER.error("Error creating modified sound for " + soundId + ": " + e.getMessage());
            // Fallback to original sound
            return createOriginalSoundInstance(soundId, newVolume, newPitch);
        }
    }
    
    /**
     * Create the appropriate type of sound instance based on the sound path
     */
    private static SoundInstance createAppropriateSoundInstance(SoundEvent soundEvent, float volume, float pitch, String soundPath) {
        // Use master category for all sounds since positioning isn't critical for volume/pitch control
        return PositionedSoundInstance.master(soundEvent, pitch, volume);
    }
    
    /**
     * Create the original sound instance without modifications
     */
    private static SoundInstance createOriginalSoundInstance(Identifier soundId, float volume, float pitch) {
        try {
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);
            if (soundEvent == null) {
                // Fallback to creating a new SoundEvent if not in registry
                soundEvent = SoundEvent.of(soundId);
            }
            return PositionedSoundInstance.master(soundEvent, pitch, volume);
        } catch (Exception e) {
            CustomCobblemonMusicMod.LOGGER.error("Error creating original sound instance for " + soundId + ": " + e.getMessage());
            return PositionedSoundInstance.master(SoundEvent.of(soundId), pitch, volume);
        }
    }
    
    /**
     * Check if a sound ID belongs to Cobblemon
     */
    public static boolean isCobblemonSound(Identifier soundId) {
        String namespace = soundId.getNamespace();
        String path = soundId.getPath();
        
        // Primary check: namespace must be "cobblemon"
        if (!namespace.equals("cobblemon")) {
            return false;
        }
        
        // Secondary check: path should contain Cobblemon-specific patterns
        return path.contains("pokemon") || 
               path.contains("poke_ball") || 
               path.contains("pokeball") ||
               path.contains("move.") ||
               path.contains("impact.") ||
               path.contains("evolution.") ||
               path.contains("battle.") ||
               path.contains("pc.") ||
               path.contains("ui.") ||
               path.contains("item.") ||
               path.contains("block.") ||
               path.contains("entity/villager/work_nurse");
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
        } else if (path.contains("move.")) {
            return "move_sounds";
        } else if (path.contains("impact.")) {
            return "impact_sounds";
        } else if (path.contains("evolution.")) {
            return "evolution_sounds";
        } else if (path.contains("battle.")) {
            return "battle_sounds";
        } else if (path.contains("pc.")) {
            return "pc_sounds";
        } else if (path.contains("ui.")) {
            return "ui_sounds";
        } else if (path.contains("item.")) {
            return "item_sounds";
        } else if (path.contains("block.")) {
            return "block_sounds";
        } else if (path.contains("entity/villager/work_nurse")) {
            return "nurse_sounds";
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