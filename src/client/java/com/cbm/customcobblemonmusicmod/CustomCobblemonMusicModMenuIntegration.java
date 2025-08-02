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
                    .setTitle(Text.translatable("config.customcobblemonmusicmod.title"))
                    .setSavingRunnable(() -> {
                        config.save();
                        CustomCobblemonMusicMod.LOGGER.info("Configuration saved");
                    });
            
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            
            // Volume Settings Category
            ConfigCategory volumeCategory = builder.getOrCreateCategory(
                    Text.translatable("config.customcobblemonmusicmod.category.volume"));
            
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.battle_volume"), config.battleMusicVolume)
                    .setDefaultValue(0.8f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.battle_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.battleMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.strong_battle_volume"), config.strongBattleMusicVolume)
                    .setDefaultValue(0.85f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.strong_battle_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.strongBattleMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.panic_volume"), config.panicMusicVolume)
                    .setDefaultValue(0.9f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.panic_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.panicMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.victory_volume"), config.victoryMusicVolume)
                    .setDefaultValue(1.0f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.victory_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.victoryMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.evolution_volume"), config.evolutionMusicVolume)
                    .setDefaultValue(0.7f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.evolution_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.evolutionMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.evolution_congrat_volume"), config.evolutionCongratMusicVolume)
                    .setDefaultValue(0.8f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.evolution_congrat_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.evolutionCongratMusicVolume = newValue)
                    .build());
                    
            volumeCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.catch_congrat_volume"), config.catchCongratMusicVolume)
                    .setDefaultValue(0.9f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.catch_congrat_volume.tooltip"))
                    .setSaveConsumer(newValue -> config.catchCongratMusicVolume = newValue)
                    .build());
            
            // Music Features Category
            ConfigCategory featuresCategory = builder.getOrCreateCategory(
                    Text.translatable("config.customcobblemonmusicmod.category.features"));
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_battle_music"), config.enableBattleMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_battle_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableBattleMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_strong_battle_music"), config.enableStrongBattleMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_strong_battle_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableStrongBattleMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_panic_music"), config.enablePanicMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_panic_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enablePanicMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_victory_music"), config.enableVictoryMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_victory_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableVictoryMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_evolution_music"), config.enableEvolutionMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_evolution_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableEvolutionMusic = newValue)
                    .build());
                    
            featuresCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.enable_catch_music"), config.enableCatchMusic)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.enable_catch_music.tooltip"))
                    .setSaveConsumer(newValue -> config.enableCatchMusic = newValue)
                    .build());
            
            // Advanced Settings Category
            ConfigCategory advancedCategory = builder.getOrCreateCategory(
                    Text.translatable("config.customcobblemonmusicmod.category.advanced"));
                    
            advancedCategory.addEntry(entryBuilder
                    .startFloatField(Text.translatable("config.customcobblemonmusicmod.panic_health_threshold"), config.panicHealthThreshold)
                    .setDefaultValue(0.2f)
                    .setMin(0.0f)
                    .setMax(1.0f)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.panic_health_threshold.tooltip"))
                    .setSaveConsumer(newValue -> config.panicHealthThreshold = newValue)
                    .build());
                    
            advancedCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config.customcobblemonmusicmod.strong_battle_level_difference"), config.strongBattleLevelDifference)
                    .setDefaultValue(15)
                    .setMin(1)
                    .setMax(100)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.strong_battle_level_difference.tooltip"))
                    .setSaveConsumer(newValue -> config.strongBattleLevelDifference = newValue)
                    .build());
                    
            advancedCategory.addEntry(entryBuilder
                    .startIntField(Text.translatable("config.customcobblemonmusicmod.victory_music_duration"), config.victoryMusicDuration)
                    .setDefaultValue(7000)
                    .setMin(1000)
                    .setMax(30000)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.victory_music_duration.tooltip"))
                    .setSaveConsumer(newValue -> config.victoryMusicDuration = newValue)
                    .build());
                    
            advancedCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.immediate_stop_on_battle_end"), config.immediateStopOnBattleEnd)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.immediate_stop_on_battle_end.tooltip"))
                    .setSaveConsumer(newValue -> config.immediateStopOnBattleEnd = newValue)
                    .build());
                    
            advancedCategory.addEntry(entryBuilder
                    .startBooleanToggle(Text.translatable("config.customcobblemonmusicmod.debug_logging"), config.debugLogging)
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("config.customcobblemonmusicmod.debug_logging.tooltip"))
                    .setSaveConsumer(newValue -> config.debugLogging = newValue)
                    .build());
            
            return builder.build();
        };
    }
}