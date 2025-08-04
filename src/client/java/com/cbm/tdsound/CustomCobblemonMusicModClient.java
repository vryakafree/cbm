package com.cbm.tdsound;

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
    private static boolean isFadingOut = false;
    
    @Override
    public void onInitializeClient() {
        TDsoundMod.LOGGER.info("Initializing TDsound client - Victory, Evolution Congrat & Catch Congrat music only");
        
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
        
        TDsoundMod.LOGGER.info("TDsound client initialized!");
    }
    
    private void registerCobblemonEvents() {
        TDsoundMod.LOGGER.info("Registering Cobblemon event listeners for victory, evolution congrat, and catch congrat music...");
        
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
            
            TDsoundMod.LOGGER.info("Cobblemon event listeners registered successfully!");
            
        } catch (Exception e) {
            TDsoundMod.LOGGER.error("Failed to register Cobblemon events: " + e.getMessage());
            TDsoundMod.LOGGER.error("Make sure Cobblemon 1.6.1+ is installed!");
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
                TDsoundMod.LOGGER.info("Victory music disabled in config");
            }
            return;
        }
        
        // Play victory music IMMEDIATELY
        playVictoryMusic();
        
        // Schedule automatic stop after configured duration
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        if (fadeOutTimer != null) {
            fadeOutTimer.cancel();
        }
        
        // Schedule fade out after the main duration
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startFadeOut();
            }
        }, config.victoryMusicDuration);
        
        if (config.debugLogging) {
            TDsoundMod.LOGGER.info("Victory! Playing victory music immediately for " + 
                (config.victoryMusicDuration / 1000) + " seconds");
        }
    }
    
    private void onPokemonCaptured() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableCatchMusic) return;
        
        playCatchMusic();
        
        if (config.debugLogging) {
            TDsoundMod.LOGGER.info("Pokemon captured! Playing congratulations music");
        }
    }
    
    private void onEvolutionComplete() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (!config.enableEvolutionMusic) {
            if (config.debugLogging) {
                TDsoundMod.LOGGER.info("Evolution congrat music disabled in config");
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
            TDsoundMod.LOGGER.info("Evolution completed! Playing congratulations music");
        }
    }
    
    // Music playing methods
    public static void playVictoryMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(TDsoundMod.VICTORY_MUSIC, config.victoryMusicVolume);
        currentMusicType = "victory";
    }
    
    public static void playEvolutionCongratMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(TDsoundMod.EVO_CONGRAT_MUSIC, config.evolutionCongratMusicVolume);
        currentMusicType = "evo_congrat";
    }
    
    public static void playCatchMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(TDsoundMod.CATCH_CONGRAT_MUSIC, config.catchCongratMusicVolume);
        currentMusicType = "catch_congrat";
    }
    
    private static void playMusic(SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Stop any current music first
            stopCurrentMusic();
            
            // Play new music
            currentSoundInstance = PositionedSoundInstance.master(sound, volume);
            client.getSoundManager().play(currentSoundInstance);
        }
    }
    
    public static void stopCurrentMusic() {
        if (fadeOutTimer != null) {
            fadeOutTimer.cancel();
            fadeOutTimer = null;
        }
        
        if (currentSoundInstance != null) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                client.getSoundManager().stop(currentSoundInstance);
            }
            currentSoundInstance = null;
        }
        
        isFadingOut = false;
        resetMusicState();
    }
    
    private static void startFadeOut() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (currentSoundInstance == null || isFadingOut) {
            return;
        }
        
        isFadingOut = true;
        originalVolume = config.victoryMusicVolume;
        
        if (config.debugLogging) {
            TDsoundMod.LOGGER.info("Starting fade out for victory music over " + 
                (config.victoryMusicFadeOutDuration / 1000) + " seconds");
        }
        
        // Calculate fade steps (20 ticks per second)
        int fadeSteps = (config.victoryMusicFadeOutDuration / 50); // 50ms per step
        float volumeStep = originalVolume / fadeSteps;
        
        fadeOutTimer = new Timer();
        fadeOutTimer.scheduleAtFixedRate(new TimerTask() {
            private int currentStep = 0;
            
            @Override
            public void run() {
                currentStep++;
                float newVolume = Math.max(0.0f, originalVolume - (volumeStep * currentStep));
                
                // Update volume of current sound
                if (currentSoundInstance != null && newVolume > 0.0f) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.getSoundManager() != null) {
                        // Stop current sound and play with new volume
                        client.getSoundManager().stop(currentSoundInstance);
                        currentSoundInstance = PositionedSoundInstance.master(TDsoundMod.VICTORY_MUSIC, newVolume);
                        client.getSoundManager().play(currentSoundInstance);
                    }
                }
                
                // Stop fade out when volume reaches 0
                if (newVolume <= 0.0f || currentStep >= fadeSteps) {
                    fadeOutTimer.cancel();
                    stopCurrentMusic();
                    isFadingOut = false;
                }
            }
        }, 0, 50); // 50ms intervals
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
        isFadingOut = false;
        
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