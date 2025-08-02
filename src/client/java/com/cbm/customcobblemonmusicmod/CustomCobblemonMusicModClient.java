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
            CobblemonEvents.EVOLUTION_PRE.subscribe(Priority.NORMAL, evolutionEvent -> {
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
        if (!isInBattle || isLowHealthMusicPlaying || isFadingOut) return;
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enablePanicMusic) return;
        
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
                        
                        if (healthPercentage <= config.panicHealthThreshold && !isLowHealthMusicPlaying) {
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
        isFadingOut = false;
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableBattleMusic) return;
        
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            
            UUID playerId = client.player.getUuid();
            var battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(playerId);
            
            String musicType = "battle_song";
            
            if (battle != null && config.enableStrongBattleMusic) {
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
                                    
                                    // Check if opponent is configured level difference higher
                                    if (wildPokemon.getLevel() >= playerPokemon.getLevel() + config.strongBattleLevelDifference) {
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
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: " + musicType);
            }
            
        } catch (Exception e) {
            // Fallback to normal battle music
            playBattleMusic("battle_song");
            if (CustomCobblemonMusicModConfig.getInstance().debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Battle started, playing: battle_song (fallback)");
            }
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
    
    private void onEvolutionStarted() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableEvolutionMusic) return;
        
        stopCurrentMusic();
        playEvolutionMusic();
        isEvolutionMusicPlaying = true;
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Evolution started! Playing evolution music");
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
        
        // Update the sound volume (note: Minecraft doesn't support real-time volume changes,
        // so we'll simulate by stopping and restarting with lower volume)
        // This is a limitation of Minecraft's sound system
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