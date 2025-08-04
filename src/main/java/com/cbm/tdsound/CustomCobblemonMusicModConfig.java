package com.cbm.tdsound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class CustomCobblemonMusicModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("tdsound.json");
    private static CustomCobblemonMusicModConfig INSTANCE;
    
    // Music volume settings (only for custom congrat sounds)
    public float victoryMusicVolume = 1.0f;
    public float evolutionCongratMusicVolume = 0.8f;
    public float catchCongratMusicVolume = 0.9f;
    
    // Music behavior settings
    public boolean enableVictoryMusic = true;
    public boolean enableEvolutionMusic = true;
    public boolean enableCatchMusic = true;
    
    // Advanced settings
    public int victoryMusicDuration = 7000; // 7 seconds in milliseconds
    public int victoryMusicFadeOutDuration = 2000; // 2 seconds fade out
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
            TDsoundMod.LOGGER.error("Failed to load config: " + e.getMessage());
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
            TDsoundMod.LOGGER.error("Failed to save config: " + e.getMessage());
        }
    }
    
    private void validate() {
        // Ensure volume values are within valid range
        victoryMusicVolume = Math.max(0.0f, Math.min(1.0f, victoryMusicVolume));
        evolutionCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, evolutionCongratMusicVolume));
        catchCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, catchCongratMusicVolume));
        
        // Ensure duration is within valid range
        victoryMusicDuration = Math.max(1000, Math.min(30000, victoryMusicDuration));
        victoryMusicFadeOutDuration = Math.max(500, Math.min(5000, victoryMusicFadeOutDuration));
    }
}