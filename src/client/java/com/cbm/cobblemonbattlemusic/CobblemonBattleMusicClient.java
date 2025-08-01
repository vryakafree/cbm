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
        
        // Listen for battle end events  
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, battleVictoryEvent -> {
            onBattleVictory();
            return Unit.INSTANCE;
        });
        
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
        
        // Check for low health condition
        if (isInBattle && client.player.getHealth() <= 6.0f && !isLowHealthMusicPlaying) {
            playLowHealthMusic();
        } else if ((!isInBattle || client.player.getHealth() > 6.0f) && isLowHealthMusicPlaying) {
            stopLowHealthMusic();
        }
    }
    
    private void onBattleStart() {
        if (!isBattleMusicPlaying) {
            playBattleMusic();
        }
    }
    
    private void onBattleEnd() {
        stopBattleMusic();
        stopLowHealthMusic();
    }
    
    private void onBattleVictory() {
        stopBattleMusic();
        stopLowHealthMusic();
        playVictoryMusic();
    }
    
    public static void playBattleMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.BATTLE_MUSIC, 0.8F));
            isBattleMusicPlaying = true;
            CobblemonBattleMusic.LOGGER.info("Playing battle music");
        }
    }
    
    public static void playLowHealthMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null && !isLowHealthMusicPlaying) {
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.LOW_HEALTH_MUSIC, 0.6F));
            isLowHealthMusicPlaying = true;
            CobblemonBattleMusic.LOGGER.info("Playing low health music");
        }
    }
    
    public static void playVictoryMusic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getSoundManager() != null) {
            client.getSoundManager().play(PositionedSoundInstance.master(
                CobblemonBattleMusic.VICTORY_MUSIC, 1.0F));
            CobblemonBattleMusic.LOGGER.info("Playing victory music");
        }
    }
    
    public static void stopBattleMusic() {
        if (isBattleMusicPlaying) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() != null) {
                client.getSoundManager().stopSounds(null, SoundCategory.MUSIC);
            }
            isBattleMusicPlaying = false;
            CobblemonBattleMusic.LOGGER.info("Stopped battle music");
        }
    }
    
    public static void stopLowHealthMusic() {
        if (isLowHealthMusicPlaying) {
            isLowHealthMusicPlaying = false;
            CobblemonBattleMusic.LOGGER.info("Stopped low health music");
        }
    }
}