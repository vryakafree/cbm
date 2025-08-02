package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.Priority;
import kotlin.Unit;

import java.util.Timer;
import java.util.TimerTask;

public class CustomCobblemonMusicModClient implements ClientModInitializer {
    private static boolean isInBattle = false;
    private static boolean isBattleMusicPlaying = false;
    private static boolean isLowHealthMusicPlaying = false;
    private static boolean isEvolutionMusicPlaying = false;
    private static boolean isFadingOut = false;
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    private static Timer fadeTimer;
    private static SoundInstance currentSoundInstance;
    private static int tickCounter = 0;
    private static float currentVolume = 1.0f;
    private static float targetVolume = 1.0f;
    private static final float FADE_STEP = 0.05f; // Volume decrease per fade step
    private static final int FADE_INTERVAL = 50; // Fade step interval in milliseconds
    
    @Override
    public void onInitializeClient() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Custom Cobblemon Music Mod client");
        
        // Register event listeners
        registerBattleEvents();
        
        // Register client tick event for health monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            // Check every 20 ticks (1 second)
            if (tickCounter >= 20) {
                tickCounter = 0;
                // Note: Health monitoring temporarily disabled due to API complexity
                // Will be re-enabled when stable API is available
            }
        });
        
        CustomCobblemonMusicMod.LOGGER.info("Custom Cobblemon Music Mod client initialized successfully!");
    }
    
    private void registerBattleEvents() {
        CustomCobblemonMusicMod.LOGGER.info("Registering Cobblemon battle event listeners...");
        
        try {
            // Listen for battle start events
            CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, battleStartEvent -> {
                onBattleStart();
                return Unit.INSTANCE;
            });
            
            // Listen for battle victory events  
            CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
                onBattleVictory();
                return Unit.INSTANCE;
            });
            
            // Listen for battle fled events
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
                onBattleEnd();
                return Unit.INSTANCE;
            });
            
            // Listen for battle fainted events
            CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, battleFaintedEvent -> {
                onBattleEnd();
                return Unit.INSTANCE;
            });
            
            // Listen for Pokemon capture events
            CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, captureEvent -> {
                onPokemonCaptured();
                return Unit.INSTANCE;
            });
            
            // Listen for Pokemon evolution completion events
            CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.NORMAL, evolutionEvent -> {
                onPokemonEvolution();
                return Unit.INSTANCE;
            });
            
            CustomCobblemonMusicMod.LOGGER.info("Cobblemon event listeners registered successfully!");
            
        } catch (Exception e) {
            CustomCobblemonMusicMod.LOGGER.error("Failed to register Cobblemon events: " + e.getMessage());
            CustomCobblemonMusicMod.LOGGER.error("Make sure Cobblemon 1.6.1+ is installed!");
        }
    }
    
    private void onBattleStart() {
        isInBattle = true;
        isLowHealthMusicPlaying = false;
        isFadingOut = false;
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableBattleMusic) return;
        
        // For now, always play normal battle music
        // Level detection will be re-enabled when API is stable
        String musicType = "battle_song";
        playBattleMusic(musicType);
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: " + musicType);
        }
    }
    
    private void onBattleEnd() {
        isInBattle = false;
        isLowHealthMusicPlaying = false;
        
        // Start fade out immediately when battle ends
        startFadeOut();
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Battle ended, starting fade out");
        }
    }
    
    private void onBattleVictory() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableVictoryMusic) {
            onBattleEnd();
            return;
        }
        
        stopCurrentMusic();
        playVictoryMusic();
        
        // Schedule victory music to fade out after configured duration
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startFadeOut();
            }
        }, config.victoryMusicDuration);
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music for " + (config.victoryMusicDuration / 1000) + " seconds");
        }
        
        onBattleEnd();
    }
    
    private void onPokemonCaptured() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableCatchMusic) return;
        
        stopCurrentMusic();
        playCatchMusic();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Pokemon captured! Playing congratulations music");
        }
    }
    
    private void onPokemonEvolution() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (isEvolutionMusicPlaying) {
            stopCurrentMusic();
            isEvolutionMusicPlaying = false;
        }
        
        if (config.enableEvolutionMusic) {
            playEvolutionCongratMusic();
        }
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Pokemon evolution completed! Playing congratulations music");
        }
    }
    
    // Fade out system
    private static void startFadeOut() {
        if (currentSoundInstance == null || isFadingOut) return;
        
        isFadingOut = true;
        targetVolume = 0.0f;
        
        if (fadeTimer != null) {
            fadeTimer.cancel();
        }
        
        fadeTimer = new Timer();
        fadeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                performFadeStep();
            }
        }, 0, FADE_INTERVAL);
    }
    
    private static void performFadeStep() {
        if (!isFadingOut || currentSoundInstance == null) {
            if (fadeTimer != null) {
                fadeTimer.cancel();
                fadeTimer = null;
            }
            return;
        }
        
        currentVolume -= FADE_STEP;
        
        if (currentVolume <= targetVolume) {
            // Fade complete, stop music
            currentVolume = 0.0f;
            stopAllMusic();
            return;
        }
        
        // Note: Minecraft doesn't support real-time volume changes,
        // so we'll just fade by stopping after the fade duration
    }
    
    // Music playing methods
    public static void playBattleMusic(String musicType) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        float volume = musicType.equals("strong_battle_song") ? config.strongBattleMusicVolume : config.battleMusicVolume;
        var sound = musicType.equals("strong_battle_song") ? 
            CustomCobblemonMusicMod.STRONG_BATTLE_MUSIC : CustomCobblemonMusicMod.BATTLE_MUSIC;
        playMusicWithLoop(sound, volume);
        isBattleMusicPlaying = true;
        currentMusicType = musicType;
        currentVolume = volume;
        targetVolume = volume;
        isFadingOut = false;
    }
    
    public static void playPanicMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusicWithLoop(CustomCobblemonMusicMod.PANIC_MUSIC, config.panicMusicVolume);
        isLowHealthMusicPlaying = true;
        currentMusicType = "panic_song";
        currentVolume = config.panicMusicVolume;
        targetVolume = config.panicMusicVolume;
        isFadingOut = false;
    }
    
    public static void playVictoryMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusicWithVolume(CustomCobblemonMusicMod.VICTORY_MUSIC, config.victoryMusicVolume);
        currentMusicType = "victory";
        currentVolume = config.victoryMusicVolume;
        targetVolume = config.victoryMusicVolume;
        isFadingOut = false;
    }
    
    public static void playCatchMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusicWithVolume(CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC, config.catchCongratMusicVolume);
        currentMusicType = "catch_congrat";
        currentVolume = config.catchCongratMusicVolume;
        targetVolume = config.catchCongratMusicVolume;
        isFadingOut = false;
    }
    
    public static void playEvolutionMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusicWithVolume(CustomCobblemonMusicMod.EVO_MUSIC, config.evolutionMusicVolume);
        currentMusicType = "evo";
        currentVolume = config.evolutionMusicVolume;
        targetVolume = config.evolutionMusicVolume;
        isFadingOut = false;
    }
    
    public static void playEvolutionCongratMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusicWithVolume(CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC, config.evolutionCongratMusicVolume);
        currentMusicType = "evo_congrat";
        currentVolume = config.evolutionCongratMusicVolume;
        targetVolume = config.evolutionCongratMusicVolume;
        isFadingOut = false;
    }
    
    private static void playMusicWithLoop(net.minecraft.sound.SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Use standard PositionedSoundInstance.master method with correct parameters
            currentSoundInstance = PositionedSoundInstance.master(sound, volume);
            client.getSoundManager().play(currentSoundInstance);
        }
    }
    
    private static void playMusicWithVolume(net.minecraft.sound.SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Use standard PositionedSoundInstance.master method with correct parameters
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
        isBattleMusicPlaying = false;
        isLowHealthMusicPlaying = false;
        isFadingOut = false;
        currentMusicType = "none";
        currentVolume = 1.0f;
        targetVolume = 1.0f;
        
        if (fadeTimer != null) {
            fadeTimer.cancel();
            fadeTimer = null;
        }
    }
    
    public static void stopAllMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
        }
        if (currentSoundInstance != null) {
            currentSoundInstance = null;
        }
        isBattleMusicPlaying = false;
        isLowHealthMusicPlaying = false;
        isEvolutionMusicPlaying = false;
        isFadingOut = false;
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
    }
    
    public static void fadeOutMusic() {
        startFadeOut();
    }
}