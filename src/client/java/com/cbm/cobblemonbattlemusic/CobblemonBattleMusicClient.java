package com.cbm.cobblemonbattlemusic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.api.Priority;
import kotlin.Unit;

public class CobblemonBattleMusicClient implements ClientModInitializer {
    private static boolean isInBattle = false;
    private static boolean isBattleMusicPlaying = false;
    private static boolean isLowHealthMusicPlaying = false;
    private static int battleMusicTicks = 0;
    private static final int BATTLE_MUSIC_DURATION_TICKS = 20 * 60; // 60 seconds at 20 TPS
    
    @Override
    public void onInitializeClient() {
        CobblemonBattleMusic.LOGGER.info("Initializing Cobblemon Battle Music client");
        
        // Register event listeners
        registerBattleEvents();
        
        // Register tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            checkBattleState();
        });
    }
    
    private void registerBattleEvents() {
        // Listen for battle start events
        CobblemonEvents.BATTLE_STARTED_PRE.subscribe(Priority.NORMAL, battleStartEvent -> {
            onBattleStart();
            return Unit.INSTANCE;
        });
        
        // Listen for battle victory events (only when player wins)
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
            // Check if the player is the winner
            if (battleVictoryEvent.getBattle().getPlayers().contains(MinecraftClient.getInstance().player)) {
                onBattleVictory();
            } else {
                onBattleEnd(); // Player lost
            }
            return Unit.INSTANCE;
        });
        
        // Listen for battle end events (defeat/flee)
        CobblemonEvents.BATTLE_FLED.subscribe(Priority.NORMAL, battleFledEvent -> {
            onBattleEnd();
            return Unit.INSTANCE;
        });
        
        CobblemonEvents.BATTLE_FAINTED.subscribe(Priority.NORMAL, battleFaintedEvent -> {
            onBattleEnd();
            return Unit.INSTANCE;
        });
    }
    
    private void checkBattleState() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        // Check if player is in battle using Cobblemon's battle registry
        // Note: BattleRegistry expects ServerPlayerEntity, but we're on client side
        // For now, we'll use a placeholder until we find the correct client-side API
        boolean currentlyInBattle = false; // Placeholder - need to find client-side battle detection
        
        if (currentlyInBattle != isInBattle) {
            isInBattle = currentlyInBattle;
            if (isInBattle) {
                onBattleStart();
            } else {
                onBattleEnd();
            }
        }
        
        // Check for low health condition (20% of max health = 4.0f)
        if (isInBattle && client.player.getHealth() <= 4.0f && !isLowHealthMusicPlaying) {
            playLowHealthMusic();
        } else if ((!isInBattle || client.player.getHealth() > 4.0f) && isLowHealthMusicPlaying) {
            stopLowHealthMusic();
        }
        
        // Handle battle music looping
        if (isInBattle && isBattleMusicPlaying) {
            battleMusicTicks++;
            if (battleMusicTicks >= BATTLE_MUSIC_DURATION_TICKS) {
                // Restart battle music for looping effect
                playBattleMusic();
                battleMusicTicks = 0;
            }
        }
    }
    
    private void onBattleStart() {
        if (!isBattleMusicPlaying) {
            playBattleMusic();
            battleMusicTicks = 0;
        }
    }
    
    private void onBattleEnd() {
        stopBattleMusic();
        stopLowHealthMusic();
        battleMusicTicks = 0;
    }
    
    private void onBattleVictory() {
        stopBattleMusic();
        stopLowHealthMusic();
        playVictoryMusic();
        battleMusicTicks = 0;
    }
    
    public static void playBattleMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Use master for music with correct pitch and volume
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.BATTLE_MUSIC, 1.0F));
            isBattleMusicPlaying = true;
            CobblemonBattleMusic.LOGGER.info("Playing battle music");
        }
    }
    
    public static void playLowHealthMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null && !isLowHealthMusicPlaying) {
            // Use master for music with correct pitch and volume
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.LOW_HEALTH_MUSIC, 1.0F));
            isLowHealthMusicPlaying = true;
            CobblemonBattleMusic.LOGGER.info("Playing low health music");
        }
    }
    
    public static void playVictoryMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            // Victory music doesn't need to loop, use master with correct pitch
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.VICTORY_MUSIC, 1.0F));
            CobblemonBattleMusic.LOGGER.info("Playing victory music (one-time)");
        }
    }
    
    public static void stopBattleMusic() {
        if (isBattleMusicPlaying) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                // Stop all music sounds
                client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
            }
            isBattleMusicPlaying = false;
            CobblemonBattleMusic.LOGGER.info("Stopped battle music");
        }
    }
    
    public static void stopLowHealthMusic() {
        if (isLowHealthMusicPlaying) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                // Stop all music sounds
                client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
            }
            isLowHealthMusicPlaying = false;
            CobblemonBattleMusic.LOGGER.info("Stopped low health music");
        }
    }
}
