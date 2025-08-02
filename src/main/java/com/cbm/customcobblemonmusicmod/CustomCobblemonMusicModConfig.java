package com.cbm.customcobblemonmusicmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class CustomCobblemonMusicModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("customcobblemonmusicmod.json");
    private static CustomCobblemonMusicModConfig INSTANCE;
    
    // Music volume settings
    public float battleMusicVolume = 0.8f;
    public float strongBattleMusicVolume = 0.85f;
    public float panicMusicVolume = 0.9f;
    public float victoryMusicVolume = 1.0f;
    public float evolutionMusicVolume = 0.7f;
    public float evolutionCongratMusicVolume = 0.8f;
    public float catchCongratMusicVolume = 0.9f;
    
    // Music behavior settings
    public boolean enableBattleMusic = true;
    public boolean enableStrongBattleMusic = true;
    public boolean enablePanicMusic = true;
    public boolean enableVictoryMusic = true;
    public boolean enableEvolutionMusic = true;
    public boolean enableCatchMusic = true;
    
    // Advanced settings
    public float panicHealthThreshold = 0.2f; // 20% health
    public int strongBattleLevelDifference = 15; // 15 level difference
    public int victoryMusicDuration = 7000; // 7 seconds in milliseconds
    public boolean immediateStopOnBattleEnd = true;
    public boolean debugLogging = false;
    
    public static CustomCobblemonMusicModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }
    
    public static CustomCobblemonMusicModConfig load() {
        try {
            if (CONFIG_PATH.toFile().exists()) {
                try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
                    CustomCobblemonMusicModConfig config = GSON.fromJson(reader, CustomCobblemonMusicModConfig.class);
                    if (config != null) {
                        config.validate();
                        return config;
                    }
                }
            }
        } catch (IOException e) {
            CustomCobblemonMusicMod.LOGGER.error("Failed to load config: " + e.getMessage());
        }
        
        // Return default config if loading fails
        CustomCobblemonMusicModConfig defaultConfig = new CustomCobblemonMusicModConfig();
        defaultConfig.save();
        return defaultConfig;
    }
    
    public void save() {
        try {
            CONFIG_PATH.getParent().toFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            CustomCobblemonMusicMod.LOGGER.error("Failed to save config: " + e.getMessage());
        }
    }
    
    private void validate() {
        // Ensure volume values are within valid range
        battleMusicVolume = Math.max(0.0f, Math.min(1.0f, battleMusicVolume));
        strongBattleMusicVolume = Math.max(0.0f, Math.min(1.0f, strongBattleMusicVolume));
        panicMusicVolume = Math.max(0.0f, Math.min(1.0f, panicMusicVolume));
        victoryMusicVolume = Math.max(0.0f, Math.min(1.0f, victoryMusicVolume));
        evolutionMusicVolume = Math.max(0.0f, Math.min(1.0f, evolutionMusicVolume));
        evolutionCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, evolutionCongratMusicVolume));
        catchCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, catchCongratMusicVolume));
        
        // Ensure threshold values are within valid range
        panicHealthThreshold = Math.max(0.0f, Math.min(1.0f, panicHealthThreshold));
        strongBattleLevelDifference = Math.max(1, Math.min(100, strongBattleLevelDifference));
        victoryMusicDuration = Math.max(1000, Math.min(30000, victoryMusicDuration));
    }
}