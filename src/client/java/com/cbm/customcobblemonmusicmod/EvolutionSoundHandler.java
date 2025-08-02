package com.cbm.customcobblemonmusicmod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class EvolutionSoundHandler {
    private static final AtomicBoolean isMonitoring = new AtomicBoolean(false);
    private static final AtomicBoolean evolutionSoundPlaying = new AtomicBoolean(false);
    private static Timer evolutionTimer;
    private static long lastEvolutionCheck = 0;
    private static final long EVOLUTION_CHECK_COOLDOWN = 5000; // 5 seconds cooldown between evolution checks
    
    public static void initialize() {
        CustomCobblemonMusicMod.LOGGER.info("Initializing Evolution Sound Handler");
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            monitorEvolutionSounds();
        });
    }
    
    private static void monitorEvolutionSounds() {
        long currentTime = System.currentTimeMillis();
        
        // Don't check too frequently
        if (currentTime - lastEvolutionCheck < 500) { // Check every 500ms
            return;
        }
        lastEvolutionCheck = currentTime;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.getSoundManager() == null) return;
        
        try {
            // Check if we should monitor for evolution sounds
            boolean shouldMonitor = shouldMonitorEvolution();
            
            if (shouldMonitor && !isMonitoring.get()) {
                startEvolutionMonitoring();
            } else if (!shouldMonitor && isMonitoring.get()) {
                stopEvolutionMonitoring();
            }
            
            // Check for evolution sounds being played
            if (isMonitoring.get()) {
                checkForEvolutionSounds();
            }
            
        } catch (Exception e) {
            // Silently handle errors
        }
    }
    
    private static boolean shouldMonitorEvolution() {
        // Monitor when we're not in battle and no other music is playing
        return !CustomCobblemonMusicModClient.isInBattle() && 
               "none".equals(CustomCobblemonMusicModClient.getCurrentMusicType());
    }
    
    private static void startEvolutionMonitoring() {
        if (isMonitoring.compareAndSet(false, true)) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Started monitoring for evolution sounds");
            }
        }
    }
    
    private static void stopEvolutionMonitoring() {
        if (isMonitoring.compareAndSet(true, false)) {
            evolutionSoundPlaying.set(false);
            
            if (evolutionTimer != null) {
                evolutionTimer.cancel();
                evolutionTimer = null;
            }
            
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Stopped monitoring for evolution sounds");
            }
        }
    }
    
    private static void checkForEvolutionSounds() {
        // This is a simplified detection method
        // In a real implementation, we would hook into Minecraft's sound system
        
        long currentTime = System.currentTimeMillis();
        
        // Check if enough time has passed since last evolution check
        if (currentTime - lastEvolutionCheck < EVOLUTION_CHECK_COOLDOWN) {
            return;
        }
        
        // Heuristic: check if we should trigger evolution music
        // This could be enhanced by monitoring actual sound events
        
        if (!evolutionSoundPlaying.get()) {
            // Detect potential evolution scenario
            boolean shouldTriggerEvolution = detectEvolutionScenario();
            
            if (shouldTriggerEvolution) {
                onEvolutionSoundDetected();
            }
        }
    }
    
    private static boolean detectEvolutionScenario() {
        // Simplified detection - this could be enhanced
        // For now, we'll rely on the evolution complete event
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return false;
        
        // Check if player is likely in an evolution scenario
        // This is a placeholder - real implementation would need more sophisticated detection
        
        return false; // Will be triggered by evolution complete event instead
    }
    
    public static void onEvolutionSoundDetected() {
        if (!evolutionSoundPlaying.compareAndSet(false, true)) {
            return; // Already handling evolution
        }
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        if (!config.enableEvolutionMusic) {
            evolutionSoundPlaying.set(false);
            return;
        }
        
        // Play evolution music
        CustomCobblemonMusicModClient.playEvolutionMusic();
        
        // Schedule transition to congratulations music
        if (evolutionTimer != null) {
            evolutionTimer.cancel();
        }
        
        evolutionTimer = new Timer();
        evolutionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onEvolutionSoundEnded();
            }
        }, 3000); // 3 second delay - adjustable
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Evolution sound detected - playing evolution music");
        }
    }
    
    public static void onEvolutionSoundEnded() {
        if (!evolutionSoundPlaying.compareAndSet(true, false)) {
            return; // Not currently playing evolution
        }
        
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Stop evolution music and play congratulations
        CustomCobblemonMusicModClient.stopCurrentMusic();
        
        if (config.enableEvolutionMusic) {
            CustomCobblemonMusicModClient.playEvolutionCongratMusic();
        }
        
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Evolution sound ended - playing congratulations music");
        }
        
        // Clean up timer
        if (evolutionTimer != null) {
            evolutionTimer.cancel();
            evolutionTimer = null;
        }
    }
    
    public static void forceStopEvolutionMusic() {
        evolutionSoundPlaying.set(false);
        
        if (evolutionTimer != null) {
            evolutionTimer.cancel();
            evolutionTimer = null;
        }
    }
    
    // Helper method to trigger evolution music manually (for testing or external triggers)
    public static void triggerEvolutionMusic() {
        long currentTime = System.currentTimeMillis();
        
        // Prevent rapid triggering
        if (currentTime - lastEvolutionCheck < EVOLUTION_CHECK_COOLDOWN) {
            return;
        }
        
        lastEvolutionCheck = currentTime;
        onEvolutionSoundDetected();
    }
}