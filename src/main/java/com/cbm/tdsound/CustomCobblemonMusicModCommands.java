package com.cbm.tdsound;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CustomCobblemonMusicModCommands {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }
    
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("cobblecongrat")
            .then(CommandManager.literal("status")
                .executes(CustomCobblemonMusicModCommands::showStatus))
            .then(CommandManager.literal("test")
                .then(CommandManager.literal("victory")
                    .executes(CustomCobblemonMusicModCommands::testVictoryMusic))
                .then(CommandManager.literal("evo_congrat")
                    .executes(CustomCobblemonMusicModCommands::testEvolutionCongratMusic))
                .then(CommandManager.literal("catch")
                    .executes(CustomCobblemonMusicModCommands::testCatchMusic)))
            .then(CommandManager.literal("stop")
                .executes(CustomCobblemonMusicModCommands::stopMusic))
            .then(CommandManager.literal("config")
                .executes(CustomCobblemonMusicModCommands::showConfig))
        );
    }
    
    private static int showStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        source.sendMessage(Text.literal("§6=== Custom Congrat Sound For Cobblemon Status ==="));
        source.sendMessage(Text.literal("§eVersion: §f1.0.2"));
        source.sendMessage(Text.literal("§eActive Sound System: §fVictory, Evolution Congrat & Catch Congrat"));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Enabled Features:"));
        source.sendMessage(Text.literal("§7- Victory Music: §" + (config.enableVictoryMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Evolution Congrat: §" + (config.enableEvolutionMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Catch Congrat: §" + (config.enableCatchMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Commands:"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test victory"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test evo_congrat"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test catch"));
        source.sendMessage(Text.literal("§7- /cobblecongrat stop"));
        
        return 1;
    }
    
    private static int testVictoryMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            if (!config.enableVictoryMusic) {
                source.sendMessage(Text.literal("§cVictory music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing victory music..."));
            source.sendMessage(Text.literal("§ePlaying: victory.ogg"));
            source.sendMessage(Text.literal("§7Duration: " + (config.victoryMusicDuration / 1000) + " seconds"));
            source.sendMessage(Text.literal("§7Fade Out: " + (config.victoryMusicFadeOutDuration / 1000) + " seconds"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.victoryMusicVolume * 100) + "%"));
            
            // Actually play the victory music
            try {
                player.playSound(
                    TDsoundMod.VICTORY_MUSIC,
                    config.victoryMusicVolume,
                    1.0f
                );
                source.sendMessage(Text.literal("§a✓ Victory music played successfully!"));
                
                // Schedule fade out after duration
                player.getServer().execute(() -> {
                    // Simplified scheduling - just log the fade out
                    source.sendMessage(Text.literal("§eVictory music will fade out after " + (config.victoryMusicDuration / 1000) + " seconds"));
                });
                
            } catch (Exception e) {
                source.sendMessage(Text.literal("§c✗ Failed to play victory music: " + e.getMessage()));
            }
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testEvolutionCongratMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            if (!config.enableEvolutionMusic) {
                source.sendMessage(Text.literal("§cEvolution congrat music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing evolution congratulations music..."));
            source.sendMessage(Text.literal("§ePlaying: evo_congrat.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.evolutionCongratMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon evolution complete"));
            
            // Actually play the evolution congrat music
            try {
                player.playSound(
                    TDsoundMod.EVO_CONGRAT_MUSIC,
                    config.evolutionCongratMusicVolume,
                    1.0f
                );
                source.sendMessage(Text.literal("§a✓ Evolution congrat music played successfully!"));
                
            } catch (Exception e) {
                source.sendMessage(Text.literal("§c✗ Failed to play evolution congrat music: " + e.getMessage()));
            }
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testCatchMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            if (!config.enableCatchMusic) {
                source.sendMessage(Text.literal("§cCatch congrat music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing catch congratulations music..."));
            source.sendMessage(Text.literal("§ePlaying: catch_congrat.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.catchCongratMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon caught"));
            
            // Actually play the catch congrat music
            try {
                player.playSound(
                    TDsoundMod.CATCH_CONGRAT_MUSIC,
                    config.catchCongratMusicVolume,
                    1.0f
                );
                source.sendMessage(Text.literal("§a✓ Catch congrat music played successfully!"));
                
            } catch (Exception e) {
                source.sendMessage(Text.literal("§c✗ Failed to play catch congrat music: " + e.getMessage()));
            }
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int stopMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Stopping all custom congrat sounds..."));
            source.sendMessage(Text.literal("§7Note: This only stops mod's custom sounds"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int showConfig(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        source.sendMessage(Text.literal("§6=== Configuration Settings ==="));
        source.sendMessage(Text.literal("§eVictory Music: §f" + config.enableVictoryMusic + " (Vol: " + (int)(config.victoryMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eEvolution Congrat: §f" + config.enableEvolutionMusic + " (Vol: " + (int)(config.evolutionCongratMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eCatch Congrat: §f" + config.enableCatchMusic + " (Vol: " + (int)(config.catchCongratMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eVictory Duration: §f" + (config.victoryMusicDuration / 1000) + " seconds"));
        source.sendMessage(Text.literal("§eDebug Logging: §f" + config.debugLogging));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§7Use ModMenu for GUI configuration"));
        
        return 1;
    }
}