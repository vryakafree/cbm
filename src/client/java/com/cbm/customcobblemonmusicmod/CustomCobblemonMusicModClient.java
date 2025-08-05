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
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomCobblemonMusicModClient implements ClientModInitializer {
    private static boolean isEvolutionMusicPlaying = false;
    private static String currentMusicType = "none";
    private static Timer musicTimer;
    private static SoundInstance currentSoundInstance;
    private static int tickCounter = 0;
    
    @Override
    public void onInitializeClient() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Custom Congrat Sound For Cobblemon client - Victory, Evolution Congrat & Catch Congrat music only");
        
        // Register event listeners
        registerCobblemonEvents();
        
        // Initialize Cobblemon sound control system
        initializeCobblemonSoundControl();
        
        // Register client tick event for evolution sound monitoring
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            if (tickCounter >= 20) { // Every second
                tickCounter = 0;
                monitorEvolutionSounds();
            }
        });
        
        CustomCobblemonMusicMod.LOGGER.info("Custom Congrat Sound For Cobblemon client initialized!");
        
        // Register test command for debugging
        registerTestCommands();
    }
    
    private void initializeCobblemonSoundControl() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        if (config.enableCobblemonSoundControl) {
            CustomCobblemonMusicMod.LOGGER.info("Cobblemon sound control system initialized and enabled");
            CustomCobblemonMusicMod.LOGGER.info("All Cobblemon sounds will be processed through the sound interceptor");
        } else {
            CustomCobblemonMusicMod.LOGGER.info("Cobblemon sound control system initialized but disabled");
            CustomCobblemonMusicMod.LOGGER.info("Use '/tdsound cobblemon toggle' to enable or configure via ModMenu");
        }
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
            
            // Battle flee - for flee sound
            CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
                onBattleFled();
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
                stopCurrentMusic();
            }
        }, 15000); // 15 seconds fixed duration
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Victory! Playing victory music immediately for 15 seconds");
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
    
    private void onBattleFled() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableFleeMusic) return;
        
        playFleeMusic();
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Player fled from battle! Playing flee music");
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
        playMusic(CustomCobblemonMusicMod.VICTORY_MUSIC, config.victoryMusicVolume, config.victoryMusicPitch);
        currentMusicType = "victory";
    }
    
    public static void playEvolutionCongratMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC, config.evolutionCongratMusicVolume, config.evolutionCongratMusicPitch);
        currentMusicType = "evo_congrat";
    }
    
    public static void playCatchMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC, config.catchCongratMusicVolume, config.catchCongratMusicPitch);
        currentMusicType = "catch_congrat";
    }
    
    public static void playFleeMusic() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        playMusic(CustomCobblemonMusicMod.FLEE_MUSIC, config.fleeMusicVolume, config.fleeMusicPitch);
        currentMusicType = "flee";
    }
    
    private static void playMusic(SoundEvent sound, float volume, float pitch) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Stop any current music first
            stopCurrentMusic();
            
            // Play new music with separate volume and pitch
            currentSoundInstance = PositionedSoundInstance.master(sound, pitch, volume);
            client.getSoundManager().play(currentSoundInstance);
            
            // Debug logging
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Playing sound: " + sound.getId() + " with volume: " + volume + ", pitch: " + pitch);
            }
        } else {
            CustomCobblemonMusicMod.LOGGER.error("Sound manager is null, cannot play music");
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
    
    private static SoundEvent getCurrentSoundEvent() {
        switch (currentMusicType) {
            case "victory":
                return CustomCobblemonMusicMod.VICTORY_MUSIC;
            case "evo_congrat":
                return CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC;
            case "catch_congrat":
                return CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC;
            case "flee":
                return CustomCobblemonMusicMod.FLEE_MUSIC;
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
    }
    
    // Public getter methods for external access
    public static String getCurrentMusicType() {
        return currentMusicType;
    }
    
    public static boolean isMusicPlaying() {
        return currentSoundInstance != null;
    }
    
    private void registerTestCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("tdsound")
                .then(ClientCommandManager.literal("test")
                    .executes(context -> {
                        testCobblemonSoundInterception();
                        context.getSource().sendFeedback(Text.literal("Testing Cobblemon sound interception... Check logs for details."));
                        return 1;
                    }))
                .then(ClientCommandManager.literal("debug")
                    .executes(context -> {
                        toggleDebugLogging();
                        context.getSource().sendFeedback(Text.literal("Debug logging toggled. Check config for current state."));
                        return 1;
                    }))
                .then(ClientCommandManager.literal("config")
                    .executes(context -> {
                        showCurrentConfig();
                        context.getSource().sendFeedback(Text.literal("Current config displayed in chat. Check logs for full details."));
                        return 1;
                    }))
            );
        });
    }
    
    private void testCobblemonSoundInterception() {
        CustomCobblemonMusicMod.LOGGER.info("=== TESTING COBBLEMON SOUND INTERCEPTION ===");
        
        // Test various Cobblemon sound IDs
        String[] testSounds = {
            "cobblemon:pokemon.bulbasaur.cry",
            "cobblemon:pokemon.pikachu.cry", 
            "cobblemon:move.tackle",
            "cobblemon:impact.tackle",
            "cobblemon:evolution.full",
            "cobblemon:poke_ball.capture_succeeded",
            "cobblemon:pc.on",
            "cobblemon:ui.menu"
        };
        
        for (String soundIdStr : testSounds) {
            Identifier soundId = Identifier.of(soundIdStr);
            boolean isCobblemon = CobblemonSoundInterceptor.isCobblemonSound(soundId);
            String category = CobblemonSoundInterceptor.getCobblemonSoundCategory(soundId);
            
            CustomCobblemonMusicMod.LOGGER.info("Testing sound: " + soundIdStr);
            CustomCobblemonMusicMod.LOGGER.info("  - Is Cobblemon sound: " + isCobblemon);
            CustomCobblemonMusicMod.LOGGER.info("  - Category: " + category);
            
            if (isCobblemon) {
                try {
                    SoundInstance testSound = CobblemonSoundInterceptor.createModifiedCobblemonSound(soundId, 1.0f, 1.0f);
                    CustomCobblemonMusicMod.LOGGER.info("  - Successfully created modified sound instance");
                } catch (Exception e) {
                    CustomCobblemonMusicMod.LOGGER.error("  - Error creating sound instance: " + e.getMessage());
                }
            }
        }
        
        CustomCobblemonMusicMod.LOGGER.info("=== END TEST ===");
    }
    
    private void toggleDebugLogging() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        config.debugLogging = !config.debugLogging;
        config.save();
        
        CustomCobblemonMusicMod.LOGGER.info("Debug logging " + (config.debugLogging ? "ENABLED" : "DISABLED"));
    }
    
    private void showCurrentConfig() {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        CustomCobblemonMusicMod.LOGGER.info("=== CURRENT CONFIG ===");
        CustomCobblemonMusicMod.LOGGER.info("Cobblemon sound control enabled: " + config.enableCobblemonSoundControl);
        CustomCobblemonMusicMod.LOGGER.info("Debug logging: " + config.debugLogging);
        CustomCobblemonMusicMod.LOGGER.info("Global Cobblemon volume: " + config.cobblemonSoundsVolume);
        CustomCobblemonMusicMod.LOGGER.info("Global Cobblemon pitch: " + config.cobblemonSoundsPitch);
        CustomCobblemonMusicMod.LOGGER.info("Pokemon cries volume: " + config.cobblemonPokemonCriesVolume);
        CustomCobblemonMusicMod.LOGGER.info("Pokemon cries pitch: " + config.cobblemonPokemonCriesPitch);
        CustomCobblemonMusicMod.LOGGER.info("Pokeball sounds volume: " + config.cobblemonPokeballSoundsVolume);
        CustomCobblemonMusicMod.LOGGER.info("Pokeball sounds pitch: " + config.cobblemonPokeballSoundsPitch);
        CustomCobblemonMusicMod.LOGGER.info("Move sounds volume: " + config.cobblemonMoveSoundsVolume);
        CustomCobblemonMusicMod.LOGGER.info("Move sounds pitch: " + config.cobblemonMoveSoundsPitch);
        CustomCobblemonMusicMod.LOGGER.info("Evolution sounds volume: " + config.cobblemonEvolutionSoundsVolume);
        CustomCobblemonMusicMod.LOGGER.info("Evolution sounds pitch: " + config.cobblemonEvolutionSoundsPitch);
        CustomCobblemonMusicMod.LOGGER.info("=====================");
    }
}