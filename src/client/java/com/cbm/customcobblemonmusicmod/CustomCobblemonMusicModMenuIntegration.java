package com.cbm.customcobblemonmusicmod;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class CustomCobblemonMusicModMenuIntegration implements ModMenuApi {
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("config.tdsound.title"))
                    .setSavingRunnable(() -> {
                        config.save();
                        CustomCobblemonMusicMod.LOGGER.info("Configuration saved");
                    });
            
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            
            // Volume Settings Category
            ConfigCategory volumeCategory = builder.getOrCreateCategory(
                    Text.translatable("config.tdsound.category.volume"));
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.victory_volume"), config.victoryMusicVolume)
                    .setDefaultValue(0.3f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.tdsound.victory_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.victoryMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.evolution_congrat_volume"), config.evolutionCongratMusicVolume)
                    .setDefaultValue(0.3f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.tdsound.evolution_congrat_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.evolutionCongratMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.catch_congrat_volume"), config.catchCongratMusicVolume)
                    .setDefaultValue(0.3f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.tdsound.catch_congrat_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.catchCongratMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.flee_volume"), config.fleeMusicVolume)
                    .setDefaultValue(0.3f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.tdsound.flee_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.fleeMusicVolume = newValue)
                    .build());
            
            // Pitch Settings Category
            ConfigCategory pitchCategory = builder.getOrCreateCategory(
                    Text.translatable("config.tdsound.category.pitch"));
                    
            pitchCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.victory_pitch"), config.victoryMusicPitch)
                    .setDefaultValue(1.0f)
                    .setMin(0.5f)
                    .setMax(2.0f)
                    .setTooltip(Text.translatable("config.tdsound.victory_pitch.tooltip"))
                    .setSaveConsumer(newValue -> config.victoryMusicPitch = newValue)
                    .build());
                    
            pitchCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.evolution_congrat_pitch"), config.evolutionCongratMusicPitch)
                    .setDefaultValue(1.0f)
                    .setMin(0.5f)
                    .setMax(2.0f)
                    .setTooltip(Text.translatable("config.tdsound.evolution_congrat_pitch.tooltip"))
                    .setSaveConsumer(newValue -> config.evolutionCongratMusicPitch = newValue)
                    .build());
                    
            pitchCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.catch_congrat_pitch"), config.catchCongratMusicPitch)
                    .setDefaultValue(1.0f)
                    .setMin(0.5f)
                    .setMax(2.0f)
                    .setTooltip(Text.translatable("config.tdsound.catch_congrat_pitch.tooltip"))
                    .setSaveConsumer(newValue -> config.catchCongratMusicPitch = newValue)
                    .build());
                    
            pitchCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.tdsound.flee_pitch"), config.fleeMusicPitch)
                    .setDefaultValue(1.0f)
                    .setMin(0.5f)
                    .setMax(2.0f)
                    .setTooltip(Text.translatable("config.tdsound.flee_pitch.tooltip"))
                    .setSaveConsumer(newValue -> config.fleeMusicPitch = newValue)
                    .build());
            
            // Music Features Category
            ConfigCategory featuresCategory = builder.getOrCreateCategory(
                    Text.translatable("config.tdsound.category.features"));
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.tdsound.enable_victory_music"), config.enableVictoryMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.tdsound.enable_victory_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableVictoryMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.tdsound.enable_evolution_music"), config.enableEvolutionMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.tdsound.enable_evolution_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableEvolutionMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.tdsound.enable_catch_music"), config.enableCatchMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.tdsound.enable_catch_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableCatchMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.tdsound.enable_flee_music"), config.enableFleeMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.tdsound.enable_flee_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableFleeMusic = newValue)
                    .build());
            
            // Advanced Settings Category
            ConfigCategory advancedCategory = builder.getOrCreateCategory(
                    Text.translatable("config.tdsound.category.advanced"));
                    

                    
            advancedCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.tdsound.debug_logging"), config.debugLogging)
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("config.tdsound.debug_logging.tooltip"))
                    .setSaveConsumer(newValue -> config.debugLogging = newValue)
                    .build());
            
            return builder.build();
        };
    }
}