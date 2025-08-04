package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.Priority;
import kotlin.Unit;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CustomCobblemonMusicModClient implements ClientModInitializer {
    private static boolean isEvolutionMusicPlaying = false;
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    private static Timer fadeOutTimer;
    private static SoundInstance currentSoundInstance;
    private static int tickCounter = 0;
    private static float originalVolume = 1.0f;
    
    @Override
    public void onInitializeClient() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Custom Congrat Sound For Cobblemon client - Victory, Evolution Congrat & Catch Congrat music only");
        
        // Register event listeners
        registerCobblemonEvents();
        
        // Register client tick event for evolution sound monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            if (tickCounter >= 20) { // Every second
                tickCounter = 0;
                monitorEvolutionSounds();
            }
        });
        
        CustomCobblemonMusicMod.LOGGER.info("Custom Congrat Sound For Cobblemon client initialized!");
    }
    
    private void registerCobblemonEvents() {
        CustomCobblemonMusicMod.LOGGER.info("Registering Cobblemon event listeners for victory, evolution congrat, and catch congrat music...");
        
        try {
            // Battle victory - immediate response
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                onBattleVictoryImmediate();
                return Unit.INSTANCE;
            });
            
            // Pokemon capture
            CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, captureEvent -> {
                onPokemonCaptured();
                return Unit.INSTANCE;
            });
            
            // Evolution complete - for evo_congrat
            CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.NORMAL, evolutionEvent -> {
                onEvolutionComplete();
                return Unit.INSTANCE;
            });
            
            CustomCobblemonMusicMod.LOGGER.info("Cobblemon event listeners registered successfully!");
            
        } catch (Exception e) {
            CustomCobblemonMusicMod.LOGGER.error("Failed to register Cobblemon events: " + e.getMessage());
            CustomCobblemonMusicMod.LOGGER.error("Make sure Cobblemon 1.6.1+ is installed!");
        }
    }
    
    private void monitorEvolutionSounds() {
        // Monitor for Cobblemon's native evolution sounds
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Check if evolution_full sound is playing
            // This is a simplified detection method
            // Real implementation would require hooking into Cobblemon's sound events
            
            // For now, we'll use the evolution complete event to trigger our music
        }
    }
    
    private void onBattleVictoryImmediate() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (!config.enableVictoryMusic) {
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Victory music disabled in config");
            }
            return;
        }
        
        // Play victory music IMMEDIATELY
        playVictoryMusic();
        
        // Schedule automatic stop after configured duration
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fadeOutMusic();
            }
        }, config.victoryMusicDuration);
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music immediately for " + 
                (config.victoryMusicDuration / 1000) + " seconds");
        }
    }
    
    private void onPokemonCaptured() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableCatchMusic) return;
        
        playCatchMusic();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Pokemon captured! Playing congratulations music");
        }
    }
    
    private void onEvolutionComplete() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (!config.enableEvolutionMusic) {
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Evolution congrat music disabled in config");
            }
            return;
        }
        
        // Stop any playing evolution music first
        if (isEvolutionMusicPlaying) {
            stopCurrentMusic();
            isEvolutionMusicPlaying = false;
        }
        
        // Play evolution congratulations music
        playEvolutionCongratMusic();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Evolution completed! Playing congratulations music");
        }
    }
    
    // Music playing methods
    public static void playVictoryMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.VICTORY_MUSIC, config.victoryMusicVolume);
        currentMusicType = "victory";
    }
    
    public static void playEvolutionCongratMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC, config.evolutionCongratMusicVolume);
        currentMusicType = "evo_congrat";
    }
    
    public static void playCatchMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC, config.catchCongratMusicVolume);
        currentMusicType = "catch_congrat";
    }
    
    private static void playMusic(SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Stop any current music first
            stopCurrentMusic();
            
            // Store original volume for fade out
            originalVolume = volume;
            
            // Play new music
            currentSoundInstance = PositionedSoundInstance.master(sound, volume);
            client.getSoundManager().play(currentSoundInstance);
        }
    }
    
    public static void stopCurrentMusic() {
        if (currentSoundInstance != null) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                client.getSoundManager().stop(currentSoundInstance);
            }
            currentSoundInstance = null;
        }
        
        resetMusicState();
    }
    
    public static void fadeOutMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (currentSoundInstance == null || fadeOutTimer != null) {
            return; // Already fading out or no music playing
        }
        
        // Cancel the regular stop timer
        if (musicTimer != null) {
            musicTimer.cancel();
            musicTimer = null;
        }
        
        // Start fade out timer
        fadeOutTimer = new Timer();
        final int fadeOutDuration = config.victoryMusicFadeOutDuration;
        final int steps = 20; // 20 steps for smooth fade
        final int stepDuration = fadeOutDuration / steps;
        final float volumeStep = originalVolume / steps;
        
        fadeOutTimer.scheduleAtFixedRate(new TimerTask() {
            private int currentStep = 0;
            
            @Override
            public void run() {
                if (currentStep >= steps || currentSoundInstance == null) {
                    // Fade out complete, stop the music
                    stopCurrentMusic();
                    if (fadeOutTimer != null) {
                        fadeOutTimer.cancel();
                        fadeOutTimer = null;
                    }
                    return;
                }
                
                // Reduce volume gradually
                float newVolume = originalVolume - (volumeStep * currentStep);
                if (newVolume < 0) newVolume = 0;
                
                // Update volume if possible
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.getSoundManager() != null && currentSoundInstance != null) {
                    // Create new sound instance with reduced volume
                    SoundEvent currentSound = getCurrentSoundEvent();
                    if (currentSound != null) {
                        SoundInstance newInstance = PositionedSoundInstance.master(currentSound, newVolume);
                        client.getSoundManager().stop(currentSoundInstance);
                        currentSoundInstance = newInstance;
                        client.getSoundManager().play(currentSoundInstance);
                    }
                }
                
                currentStep++;
            }
        }, 0, stepDuration);
    }
    
    private static SoundEvent getCurrentSoundEvent() {
        switch (currentMusicType) {
            case "victory":
                return CustomCobblemonMusicMod.VICTORY_MUSIC;
            case "evo_congrat":
                return CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC;
            case "catch_congrat":
                return CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC;
            default:
                return null;
        }
    }
    
    public static void stopAllMusic() {
        stopCurrentMusic();
        
        // Also stop all music category sounds
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
        }
    }
    
    private static void resetMusicState() {
        isEvolutionMusicPlaying = false;
        currentMusicType = "none";
        
        if (musicTimer != null) {
            musicTimer.cancel();
            musicTimer = null;
        }
        
        if (fadeOutTimer != null) {
            fadeOutTimer.cancel();
            fadeOutTimer = null;
        }
    }
    
    // Public getter methods for external access
    public static String getCurrentMusicType() {
        return currentMusicType;
    }
    
    public static boolean isMusicPlaying() {
        return currentSoundInstance != null;
    }
}