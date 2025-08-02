package com.cbm.customcobblemonmusicmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.pokemon.Pokemon;
import kotlin.Unit;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CustomCobblemonMusicModClient implements ClientModInitializer {
    private static boolean isInBattle = false;
    private static boolean isBattleMusicPlaying = false;
    private static boolean isLowHealthMusicPlaying = false;
    private static boolean isEvolutionMusicPlaying = false;
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    private static SoundInstance currentSoundInstance;
    private static int tickCounter = 0;
    
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
        
        // Register client tick event for health monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            // Check every 20 ticks (1 second)
            if (tickCounter >= 20) {
                tickCounter = 0;
                checkPokemonHealth();
            }
        });
        
        CustomCobblemonMusicMod.LOGGER.info("Custom Cobblemon Music Mod client initialized successfully!");
    }
    
    private void registerBattleEvents() {
        CustomCobblemonMusicMod.LOGGER.info("Registering Cobblemon battle event listeners...");
        
        try {
            // Listen for battle start events
            CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, battleStartEvent -> {
                onBattleStart(battleStartEvent);
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
            
            // Listen for evolution start events (when evolve button is pressed)
            CobblemonEvents.EVOLUTION_STARTED.subscribe(Priority.NORMAL, evolutionEvent -> {
                onEvolutionStarted();
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
    
    private void checkPokemonHealth() {
        if (!isInBattle || isLowHealthMusicPlaying) return;
        
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            
            UUID playerId = client.player.getUuid();
            var battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(playerId);
            
            if (battle != null) {
                var playerActor = battle.getActorByUUID(playerId);
                if (playerActor != null) {
                    var activePokemon = playerActor.getActivePokemon();
                    if (!activePokemon.isEmpty()) {
                        Pokemon pokemon = activePokemon.get(0).getBattlePokemon().getOriginalPokemon();
                        float healthPercentage = (float) pokemon.getCurrentHealth() / pokemon.getMaxHealth();
                        
                        if (healthPercentage <= 0.2f && !isLowHealthMusicPlaying) {
                            // Switch to panic music
                            stopCurrentMusic();
                            playPanicMusic();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Silently handle errors - health monitoring is optional
        }
    }
    
    private void onBattleStart(Object battleStartEvent) {
        isInBattle = true;
        isLowHealthMusicPlaying = false;
        
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            
            UUID playerId = client.player.getUuid();
            var battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(playerId);
            
            String musicType = "battle_song";
            
            if (battle != null) {
                var playerActor = battle.getActorByUUID(playerId);
                if (playerActor != null) {
                    var activePokemon = playerActor.getActivePokemon();
                    if (!activePokemon.isEmpty()) {
                        Pokemon playerPokemon = activePokemon.get(0).getBattlePokemon().getOriginalPokemon();
                        
                        // Find opponent Pokemon
                        var opponents = battle.getActors();
                        for (var actor : opponents) {
                            if (!actor.getUuid().equals(playerId)) {
                                var opponentPokemon = actor.getActivePokemon();
                                if (!opponentPokemon.isEmpty()) {
                                    Pokemon wildPokemon = opponentPokemon.get(0).getBattlePokemon().getOriginalPokemon();
                                    
                                    // Check if opponent is 15+ levels higher
                                    if (wildPokemon.getLevel() >= playerPokemon.getLevel() + 15) {
                                        musicType = "strong_battle_song";
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            
            playBattleMusic(musicType);
            CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: " + musicType);
            
        } catch (Exception e) {
            // Fallback to normal battle music
            playBattleMusic("battle_song");
            CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: battle_song (fallback)");
        }
    }
    
    private void onBattleEnd() {
        isInBattle = false;
        isLowHealthMusicPlaying = false;
        stopAllMusic();
        CustomCobblemonMusicMod.LOGGER.info("Battle ended, stopping all music immediately");
    }
    
    private void onBattleVictory() {
        stopCurrentMusic();
        playVictoryMusic();
        
        // Schedule victory music to fade out after 7 seconds
        if (musicTimer != null) {
            musicTimer.cancel();
        }
        musicTimer = new Timer();
        musicTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopAllMusic();
            }
        }, 7000);
        
        CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music for 7 seconds");
        onBattleEnd();
    }
    
    private void onPokemonCaptured() {
        stopCurrentMusic();
        playCatchMusic();
        CustomCobblemonMusicMod.LOGGER.info("Pokemon captured! Playing congratulations music");
    }
    
    private void onEvolutionStarted() {
        stopCurrentMusic();
        playEvolutionMusic();
        isEvolutionMusicPlaying = true;
        CustomCobblemonMusicMod.LOGGER.info("Evolution started! Playing evolution music");
    }
    
    private void onPokemonEvolution() {
        if (isEvolutionMusicPlaying) {
            stopCurrentMusic();
            isEvolutionMusicPlaying = false;
        }
        
        playEvolutionCongratMusic();
        CustomCobblemonMusicMod.LOGGER.info("Pokemon evolution completed! Playing congratulations music");
    }
    
    // Music playing methods
    public static void playBattleMusic(String musicType) {
        float volume = musicType.equals("strong_battle_song") ? STRONG_BATTLE_VOLUME : BATTLE_VOLUME;
        var sound = musicType.equals("strong_battle_song") ? 
            CustomCobblemonMusicMod.STRONG_BATTLE_MUSIC : CustomCobblemonMusicMod.BATTLE_MUSIC;
        playMusicWithLoop(sound, volume);
        isBattleMusicPlaying = true;
        currentMusicType = musicType;
    }
    
    public static void playPanicMusic() {
        playMusicWithLoop(CustomCobblemonMusicMod.PANIC_MUSIC, PANIC_VOLUME);
        isLowHealthMusicPlaying = true;
        currentMusicType = "panic_song";
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
            // Use proper pitch value of 1.0f to ensure normal playback speed
            currentSoundInstance = PositionedSoundInstance.master(sound, SoundCategory.MUSIC, volume, 1.0f);
            client.getSoundManager().play(currentSoundInstance);
        }
    }
    
    private static void playMusicWithVolume(net.minecraft.sound.SoundEvent sound, float volume) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Use proper pitch value of 1.0f to ensure normal playback speed
            currentSoundInstance = PositionedSoundInstance.master(sound, SoundCategory.MUSIC, volume, 1.0f);
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
        currentMusicType = "none";
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
        currentMusicType = "none";
        
        if (musicTimer != null) {
            musicTimer.cancel();
            musicTimer = null;
        }
    }
    
    public static void fadeOutMusic() {
        // Immediate stop for better responsiveness
        stopAllMusic();
    }
}