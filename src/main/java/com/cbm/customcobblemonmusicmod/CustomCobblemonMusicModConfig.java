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
    
    // Additional granular Cobblemon sound controls
    public float cobblemonMoveSoundsVolume = 1.0f; // Volume for move sounds
    public float cobblemonMoveSoundsPitch = 1.0f; // Pitch for move sounds
    public float cobblemonImpactSoundsVolume = 1.0f; // Volume for impact sounds
    public float cobblemonImpactSoundsPitch = 1.0f; // Pitch for impact sounds
    public float cobblemonEvolutionSoundsVolume = 1.0f; // Volume for evolution sounds
    public float cobblemonEvolutionSoundsPitch = 1.0f; // Pitch for evolution sounds
    public float cobblemonPCSoundsVolume = 1.0f; // Volume for PC sounds
    public float cobblemonPCSoundsPitch = 1.0f; // Pitch for PC sounds
    public float cobblemonUISoundsVolume = 1.0f; // Volume for UI sounds
    public float cobblemonUISoundsPitch = 1.0f; // Pitch for UI sounds
    public float cobblemonItemSoundsVolume = 1.0f; // Volume for item sounds
    public float cobblemonItemSoundsPitch = 1.0f; // Pitch for item sounds
    public float cobblemonBlockSoundsVolume = 1.0f; // Volume for block sounds
    public float cobblemonBlockSoundsPitch = 1.0f; // Pitch for block sounds
    
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
        
        // Validate additional granular sound controls
        cobblemonMoveSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonMoveSoundsVolume));
        cobblemonMoveSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonMoveSoundsPitch));
        cobblemonImpactSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonImpactSoundsVolume));
        cobblemonImpactSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonImpactSoundsPitch));
        cobblemonEvolutionSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonEvolutionSoundsVolume));
        cobblemonEvolutionSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonEvolutionSoundsPitch));
        cobblemonPCSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonPCSoundsVolume));
        cobblemonPCSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonPCSoundsPitch));
        cobblemonUISoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonUISoundsVolume));
        cobblemonUISoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonUISoundsPitch));
        cobblemonItemSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonItemSoundsVolume));
        cobblemonItemSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonItemSoundsPitch));
        cobblemonBlockSoundsVolume = Math.max(0.0f, Math.min(1.0f, cobblemonBlockSoundsVolume));
        cobblemonBlockSoundsPitch = Math.max(0.5f, Math.min(2.0f, cobblemonBlockSoundsPitch));
    }
}