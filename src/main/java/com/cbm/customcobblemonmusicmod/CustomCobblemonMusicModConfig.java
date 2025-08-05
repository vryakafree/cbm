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
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("tdsound.json");
    private static CustomCobblemonMusicModConfig INSTANCE;
    
    // Music volume settings (only for custom congrat sounds)
    public float victoryMusicVolume = 0.3f; // 30% volume
    public float evolutionCongratMusicVolume = 0.3f; // 30% volume
    public float catchCongratMusicVolume = 0.3f; // 30% volume
    public float fleeMusicVolume = 0.3f; // 30% volume
    
    // Music pitch settings (separate from volume)
    public float victoryMusicPitch = 1.0f; // Normal pitch
    public float evolutionCongratMusicPitch = 1.0f; // Normal pitch
    public float catchCongratMusicPitch = 1.0f; // Normal pitch
    public float fleeMusicPitch = 1.0f; // Normal pitch
    
    // Cobblemon sound control settings
    public boolean enableCobblemonSoundControl = true; // Enabled by default to control Cobblemon sounds
    public float cobblemonSoundsVolume = 1.0f; // Global volume for Cobblemon sounds
    public float cobblemonSoundsPitch = 1.0f; // Global pitch for Cobblemon sounds
    public float cobblemonPokemonCriesVolume = 1.0f; // Volume for Pokemon cries specifically
    public float cobblemonPokemonCriesPitch = 1.0f; // Pitch for Pokemon cries specifically
    public float cobblemonPokeballSoundsVolume = 1.0f; // Volume for Pokeball sounds
    public float cobblemonPokeballSoundsPitch = 1.0f; // Pitch for Pokeball sounds
    public float cobblemonBattleSoundsVolume = 1.0f; // Volume for battle sounds
    public float cobblemonBattleSoundsPitch = 1.0f; // Pitch for battle sounds
    
    // Music behavior settings
    public boolean enableVictoryMusic = true;
    public boolean enableEvolutionMusic = true;
    public boolean enableCatchMusic = true;
    public boolean enableFleeMusic = true;
    
    // Advanced settings
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
        // Ensure volume values are within valid range (0.0 to 1.0)
        victoryMusicVolume = Math.max(0.0f, Math.min(1.0f, victoryMusicVolume));
        evolutionCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, evolutionCongratMusicVolume));
        catchCongratMusicVolume = Math.max(0.0f, Math.min(1.0f, catchCongratMusicVolume));
        fleeMusicVolume = Math.max(0.0f, Math.min(1.0f, fleeMusicVolume));
        
        // Ensure pitch values are within valid range (0.5 to 2.0)
        victoryMusicPitch = Math.max(0.5f, Math.min(2.0f, victoryMusicPitch));
        evolutionCongratMusicPitch = Math.max(0.5f, Math.min(2.0f, evolutionCongratMusicPitch));
        catchCongratMusicPitch = Math.max(0.5f, Math.min(2.0f, catchCongratMusicPitch));
        fleeMusicPitch = Math.max(0.5f, Math.min(2.0f, fleeMusicPitch));
        
        // Ensure Cobblemon sound values are within valid range
        cobblemonSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonSoundsVolume));
        cobblemonSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonSoundsPitch));
        cobblemonPokemonCriesVolume = Math.max(0.0f, Math.min(1.0f, cobblemonPokemonCriesVolume));
        cobblemonPokemonCriesPitch = Math.max(0.5f, Math.min(2.0f, cobblemonPokemonCriesPitch));
        cobblemonPokeballSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonPokeballSoundsVolume));
        cobblemonPokeballSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonPokeballSoundsPitch));
        cobblemonBattleSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonBattleSoundsVolume));
        cobblemonBattleSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonBattleSoundsPitch));
    }
}