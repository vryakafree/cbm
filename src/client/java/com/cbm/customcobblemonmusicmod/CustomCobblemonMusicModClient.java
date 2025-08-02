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
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class CustomCobblemonMusicModClient implements ClientModInitializer {
    private static boolean isInBattle = false;
    private static boolean isBattleMusicPlaying = false;
    private static boolean isLowHealthMusicPlaying = false;
    private static boolean isEvolutionMusicPlaying = false;
    private static boolean isFadingOut = false;
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    private static Timer fadeTimer;
    private static Timer healthCheckTimer;
    private static Timer battleStateTimer;
    private static SoundInstance currentSoundInstance;
    private static int tickCounter = 0;
    private static float currentVolume = 1.0f;
    private static float targetVolume = 1.0f;
    private static final float FADE_STEP = 0.1f;
    private static final int FADE_INTERVAL = 100;
    
    // Battle tracking
    private static UUID currentBattleId = null;
    private static Map<String, Object> battleInfoMap = new ConcurrentHashMap<>();
    
    // Sound event tracking for evolution
    private static boolean evolutionSoundDetected = false;
    private static long lastBattleCheck = 0;
    private static final long BATTLE_CHECK_INTERVAL = 1000; // 1 second
    
    @Override
    public void onInitializeClient() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Custom Cobblemon Music Mod client with enhanced battle tracking");
        
        // Register event listeners
        registerBattleEvents();
        registerSoundEventHooks();
        
        // Initialize evolution sound handler
        EvolutionSoundHandler.initialize();
        
        // Register client tick event for comprehensive monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            if (tickCounter >= 20) { // Every second
                tickCounter = 0;
                monitorBattleState();
                checkEvolutionSounds();
            }
        });
        
        CustomCobblemonMusicMod.LOGGER.info("Custom Cobblemon Music Mod client initialized with battle tracking!");
    }
    
    private void registerBattleEvents() {
        CustomCobblemonMusicMod.LOGGER.info("Registering comprehensive Cobblemon battle event listeners...");
        
        try {
            // Battle start - with battle info extraction
            CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, battleStartEvent -> {
                onBattleStartWithInfo(battleStartEvent);
                return Unit.INSTANCE;
            });
            
            // Battle end events - immediate music stop
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                onBattleVictory(battleVictoryEvent);
                return Unit.INSTANCE;
            });
            
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
                onBattleEndImmediate("fled");
                return Unit.INSTANCE;
            });
            
            CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, battleFaintedEvent -> {
                onBattleEndImmediate("fainted");
                return Unit.INSTANCE;
            });
            
            // Other events
            CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, captureEvent -> {
                onPokemonCaptured();
                return Unit.INSTANCE;
            });
            
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
    
    private void registerSoundEventHooks() {
        CustomCobblemonMusicMod.LOGGER.info("Setting up sound event hooks for evolution detection...");
        
        // We'll monitor sound events through the client tick
        // This is a fallback method since direct sound event hooking is complex
    }
    
    private void onBattleStartWithInfo(Object battleStartEvent) {
        isInBattle = true;
        isLowHealthMusicPlaying = false;
        isFadingOut = false;
        evolutionSoundDetected = false;
        
        // Generate a simple battle ID for tracking
        currentBattleId = UUID.randomUUID();
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableBattleMusic) return;
        
        // Start with normal battle music - simplified approach
        String musicType = "battle_song";
        playBattleMusic(musicType);
        
        // Start monitoring systems
        startHealthMonitoring();
        startBattleStateMonitoring();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Battle started - ID: " + currentBattleId + ", Music: " + musicType);
        }
    }
    
    private void startHealthMonitoring() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enablePanicMusic || currentBattleId == null) return;
        
        if (healthCheckTimer != null) {
            healthCheckTimer.cancel();
        }
        
        healthCheckTimer = new Timer();
        healthCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkPlayerPokemonHealth();
            }
        }, 1000, 1000); // Check every second
    }
    
    private void startBattleStateMonitoring() {
        if (battleStateTimer != null) {
            battleStateTimer.cancel();
        }
        
        battleStateTimer = new Timer();
        battleStateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkBattleState();
            }
        }, 500, 500); // Check every 500ms for responsiveness
    }
    
    private void checkPlayerPokemonHealth() {
        if (!isInBattle || currentBattleId == null || isLowHealthMusicPlaying) return;
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            
            // Simplified health check - this would need proper Cobblemon API integration
            // For now, we'll use a heuristic approach
            
            // Placeholder: In a real implementation, this would check actual Pokemon health
            // through Cobblemon's API when it becomes available and stable
            
        } catch (Exception e) {
            // Silently handle errors
        }
    }
    
    private void checkBattleState() {
        if (!isInBattle) return;
        
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) {
                onBattleEndImmediate("player_null");
                return;
            }
            
            // Simplified battle state check
            // In a real implementation, this would check if the battle is still active
            // through Cobblemon's BattleRegistry when API is stable
            
        } catch (Exception e) {
            // If we can't check, might indicate battle ended
            onBattleEndImmediate("check_error");
        }
    }
    
    private void monitorBattleState() {
        // Additional monitoring through client tick
        if (isInBattle && currentBattleId != null) {
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - lastBattleCheck >= BATTLE_CHECK_INTERVAL) {
                lastBattleCheck = currentTime;
                
                // Check for external battle end conditions
                try {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player == null) {
                        onBattleEndImmediate("monitoring_player_null");
                    }
                } catch (Exception e) {
                    onBattleEndImmediate("monitoring_error");
                }
            }
        }
    }
    
    private void checkEvolutionSounds() {
        // Monitor for evolution_full sound event
        // This is a simplified detection method
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Check if any evolution sounds are currently playing
            // Note: This is a placeholder - actual implementation would require sound event hooking
            if (!evolutionSoundDetected && isEvolutionMusicPlaying) {
                // Evolution sound might have ended, check if we should play evo_congrat
                // This is handled in onEvolutionComplete
            }
        }
    }
    
    private void onBattleEndImmediate(String reason) {
        isInBattle = false;
        isLowHealthMusicPlaying = false;
        
        // Stop all monitoring
        if (healthCheckTimer != null) {
            healthCheckTimer.cancel();
            healthCheckTimer = null;
        }
        
        if (battleStateTimer != null) {
            battleStateTimer.cancel();
            battleStateTimer = null;
        }
        
        // Immediate music stop with quick fade
        stopCurrentMusicImmediately();
        
        // Clean up battle info
        if (currentBattleId != null) {
            battleInfoMap.remove(currentBattleId.toString());
            currentBattleId = null;
        }
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Battle ended immediately - Reason: " + reason);
        }
    }
    
    private void onBattleVictory(Object battleVictoryEvent) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Stop battle music immediately
        stopCurrentMusic();
        
        if (config.enableVictoryMusic) {
            playVictoryMusic();
            
            // Schedule victory music duration
            if (musicTimer != null) {
                musicTimer.cancel();
            }
            musicTimer = new Timer();
            musicTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopCurrentMusicImmediately();
                }
            }, config.victoryMusicDuration);
            
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music for " + 
                    (config.victoryMusicDuration / 1000) + " seconds");
            }
        }
        
        // Clean up battle state
        onBattleEndImmediate("victory");
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
        
        // Trigger evolution music sequence through the sound handler
        EvolutionSoundHandler.triggerEvolutionMusic();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Evolution completed! Triggering evolution music sequence");
        }
    }
    
    // Enhanced music control methods
    public static void playBattleMusic(String musicType) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        float volume = musicType.equals("strong_battle_song") ? config.strongBattleMusicVolume : config.battleMusicVolume;
        var sound = musicType.equals("strong_battle_song") ? 
            CustomCobblemonMusicMod.STRONG_BATTLE_MUSIC : CustomCobblemonMusicMod.BATTLE_MUSIC;
        
        playLoopingMusic(sound, volume);
        isBattleMusicPlaying = true;
        currentMusicType = musicType;
        currentVolume = volume;
        targetVolume = volume;
        isFadingOut = false;
    }
    
    public static void playPanicMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playLoopingMusic(CustomCobblemonMusicMod.PANIC_MUSIC, config.panicMusicVolume);
        isLowHealthMusicPlaying = true;
        isBattleMusicPlaying = false; // Override battle music
        currentMusicType = "panic_song";
        currentVolume = config.panicMusicVolume;
        targetVolume = config.panicMusicVolume;
        isFadingOut = false;
    }
    
    public static void playVictoryMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playOneShotMusic(CustomCobblemonMusicMod.VICTORY_MUSIC, config.victoryMusicVolume);
        currentMusicType = "victory";
        currentVolume = config.victoryMusicVolume;
        targetVolume = config.victoryMusicVolume;
        isFadingOut = false;
    }
    
    public static void playCatchMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playOneShotMusic(CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC, config.catchCongratMusicVolume);
        currentMusicType = "catch_congrat";
        currentVolume = config.catchCongratMusicVolume;
        targetVolume = config.catchCongratMusicVolume;
        isFadingOut = false;
    }
    
    public static void playEvolutionMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playOneShotMusic(CustomCobblemonMusicMod.EVO_MUSIC, config.evolutionMusicVolume);
        isEvolutionMusicPlaying = true;
        evolutionSoundDetected = true;
        currentMusicType = "evo";
        currentVolume = config.evolutionMusicVolume;
        targetVolume = config.evolutionMusicVolume;
        isFadingOut = false;
    }
    
    public static void playEvolutionCongratMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playOneShotMusic(CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC, config.evolutionCongratMusicVolume);
        currentMusicType = "evo_congrat";
        currentVolume = config.evolutionCongratMusicVolume;
        targetVolume = config.evolutionCongratMusicVolume;
        isFadingOut = false;
    }
    
    private static void playLoopingMusic(SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            currentSoundInstance = PositionedSoundInstance.master(sound, volume);
            client.getSoundManager().play(currentSoundInstance);
        }
    }
    
    private static void playOneShotMusic(SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
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
    
    public static void stopCurrentMusicImmediately() {
        // Cancel any fading
        if (fadeTimer != null) {
            fadeTimer.cancel();
            fadeTimer = null;
        }
        
        // Stop current sound
        stopCurrentMusic();
        
        // Stop all music sounds to be sure
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
        }
        
        resetMusicState();
    }
    
    private static void resetMusicState() {
        isBattleMusicPlaying = false;
        isLowHealthMusicPlaying = false;
        isEvolutionMusicPlaying = false;
        isFadingOut = false;
        evolutionSoundDetected = false;
        currentMusicType = "none";
        currentVolume = 1.0f;
        targetVolume = 1.0f;
        
        if (musicTimer != null) {
            musicTimer.cancel();
            musicTimer = null;
        }
        
        if (fadeTimer != null) {
            fadeTimer.cancel();
            fadeTimer = null;
        }
        
        // Clean up evolution handler
        EvolutionSoundHandler.forceStopEvolutionMusic();
    }
    
    public static void stopAllMusic() {
        stopCurrentMusicImmediately();
    }
    
    public static void fadeOutMusic() {
        if (currentSoundInstance == null) return;
        
        isFadingOut = true;
        targetVolume = 0.0f;
        
        if (fadeTimer != null) {
            fadeTimer.cancel();
        }
        
        fadeTimer = new Timer();
        fadeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentVolume -= FADE_STEP;
                if (currentVolume <= 0.0f) {
                    stopCurrentMusicImmediately();
                    return;
                }
                // Note: Minecraft doesn't support real-time volume changes
                // So we just fade by duration
            }
        }, 0, FADE_INTERVAL);
        
        // Force stop after fade duration
        Timer forceStopTimer = new Timer();
        forceStopTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopCurrentMusicImmediately();
            }
        }, (long)(1.0f / FADE_STEP * FADE_INTERVAL));
    }
    
    // Public getter methods for external access
    public static boolean isInBattle() {
        return isInBattle;
    }
    
    public static String getCurrentMusicType() {
        return currentMusicType;
    }
    
    public static boolean isMusicPlaying() {
        return isBattleMusicPlaying || isLowHealthMusicPlaying || isEvolutionMusicPlaying;
    }
    
    public static UUID getCurrentBattleId() {
        return currentBattleId;
    }
}