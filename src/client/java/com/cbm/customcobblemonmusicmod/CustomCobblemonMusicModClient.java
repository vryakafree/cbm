package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
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
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    
    // Music configuration
    private static final float BATTLE_VOLUME = 0.8f;
    private static final float PANIC_VOLUME = 0.9f;
    private static final float STRONG_BATTLE_VOLUME = 0.85f;
    private static final float VICTORY_VOLUME = 1.0f;
    private static final float EVO_VOLUME = 0.7f;
    private static final float EVO_CONGRAT_VOLUME = 0.8f;
    private static final float CATCH_CONGRAT_VOLUME = 0.9f;
    
    @Override
    public void onInitializeClient() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Custom Cobblemon Music Mod client");
        
        // Register event listeners
        registerBattleEvents();
        
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
            
            // Listen for Pokemon evolution events
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
        // For simplicity, always start with normal battle music
        // Level detection can be added later when API is more stable
        String musicType = "battle_song";
        playBattleMusic(musicType);
        CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: " + musicType);
    }
    
    private void onBattleEnd() {
        isInBattle = false;
        fadeOutMusic();
        CustomCobblemonMusicMod.LOGGER.info("Battle ended, fading out music");
    }
    
    private void onBattleVictory() {
        stopBattleMusic();
        playVictoryMusic();
        
        // Schedule victory music to fade out after 7 seconds
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fadeOutMusic();
            }
        }, 7000);
        
        CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music for 7 seconds");
        onBattleEnd();
    }
    
    private void onPokemonCaptured() {
        playCatchMusic();
        CustomCobblemonMusicMod.LOGGER.info("Pokemon captured! Playing congratulations music");
    }
    
    private void onPokemonEvolution() {
        playEvolutionMusic();
        
        // Schedule evolution congratulations music after evolution sound
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                playEvolutionCongratMusic();
            }
        }, 3000); // 3 seconds delay
        
        CustomCobblemonMusicMod.LOGGER.info("Pokemon evolution! Playing evolution music sequence");
    }
    
    // Music playing methods
    public static void playBattleMusic(String musicType) {
        float volume = musicType.equals("strong_battle_song") ? STRONG_BATTLE_VOLUME : BATTLE_VOLUME;
        playMusicWithLoop(musicType.equals("strong_battle_song") ? 
            CustomCobblemonMusicMod.STRONG_BATTLE_MUSIC : CustomCobblemonMusicMod.BATTLE_MUSIC, volume);
        isBattleMusicPlaying = true;
        currentMusicType = musicType;
    }
    
    public static void playVictoryMusic() {
        playMusicWithVolume(CustomCobblemonMusicMod.VICTORY_MUSIC, VICTORY_VOLUME);
        currentMusicType = "victory";
    }
    
    public static void playCatchMusic() {
        playMusicWithVolume(CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC, CATCH_CONGRAT_VOLUME);
        currentMusicType = "catch_congrat";
    }
    
    public static void playEvolutionMusic() {
        playMusicWithVolume(CustomCobblemonMusicMod.EVO_MUSIC, EVO_VOLUME);
        currentMusicType = "evo";
    }
    
    public static void playEvolutionCongratMusic() {
        playMusicWithVolume(CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC, EVO_CONGRAT_VOLUME);
        currentMusicType = "evo_congrat";
    }
    
    private static void playMusicWithLoop(net.minecraft.sound.SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            var soundInstance = PositionedSoundInstance.master(sound, volume);
            client.getSoundManager().play(soundInstance);
        }
    }
    
    private static void playMusicWithVolume(net.minecraft.sound.SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().play(PositionedSoundInstance.master(sound, volume));
        }
    }
    
    public static void stopBattleMusic() {
        if (isBattleMusicPlaying) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
            }
            isBattleMusicPlaying = false;
            isLowHealthMusicPlaying = false;
            currentMusicType = "none";
        }
    }
    
    public static void fadeOutMusic() {
        // Implement gradual fade out
        if (isBattleMusicPlaying) {
            Timer fadeTimer = new Timer();
            fadeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stopBattleMusic();
                }
            }, 2500); // 2.5 second fade out
        }
    }
}