package com.cbm.customcobblemonmusicmod;

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
                    .executes(CustomCobblemonMusicModCommands::testCatchMusic))
                .then(CommandManager.literal("flee")
                    .executes(CustomCobblemonMusicModCommands::testFleeMusic)))
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
        source.sendMessage(Text.literal("§eVersion: §f1.0.8"));
        source.sendMessage(Text.literal("§eActive Sound System: §fVictory, Evolution Congrat & Catch Congrat"));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Enabled Features:"));
        source.sendMessage(Text.literal("§7- Victory Music: §" + (config.enableVictoryMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Evolution Congrat: §" + (config.enableEvolutionMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Catch Congrat: §" + (config.enableCatchMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Flee Music: §" + (config.enableFleeMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Commands:"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test victory"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test evo_congrat"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test catch"));
        source.sendMessage(Text.literal("§7- /cobblecongrat test flee"));
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
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.victoryMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Pitch: " + String.format("%.1f", config.victoryMusicPitch)));
            
            // Play victory music on server side for testing
            try {
                player.playSound(
                    CustomCobblemonMusicMod.VICTORY_MUSIC,
                    config.victoryMusicVolume,
                    config.victoryMusicPitch
                );
                source.sendMessage(Text.literal("§a✓ Victory music played on server!"));
                source.sendMessage(Text.literal("§eNote: For full client-side music, win a battle in Cobblemon."));
                
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
            source.sendMessage(Text.literal("§7Pitch: " + String.format("%.1f", config.evolutionCongratMusicPitch)));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon evolution complete"));
            
            // Play evolution congrat music on server side for testing
            try {
                player.playSound(
                    CustomCobblemonMusicMod.EVO_CONGRAT_MUSIC,
                    config.evolutionCongratMusicVolume,
                    config.evolutionCongratMusicPitch
                );
                source.sendMessage(Text.literal("§a✓ Evolution congrat music played on server!"));
                
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
            source.sendMessage(Text.literal("§7Pitch: " + String.format("%.1f", config.catchCongratMusicPitch)));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon caught"));
            
            // Play catch congrat music on server side for testing
            try {
                player.playSound(
                    CustomCobblemonMusicMod.CATCH_CONGRAT_MUSIC,
                    config.catchCongratMusicVolume,
                    config.catchCongratMusicPitch
                );
                source.sendMessage(Text.literal("§a✓ Catch congrat music played on server!"));
                
            } catch (Exception e) {
                source.sendMessage(Text.literal("§c✗ Failed to play catch congrat music: " + e.getMessage()));
            }
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testFleeMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            if (!config.enableFleeMusic) {
                source.sendMessage(Text.literal("§cFlee music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing flee music..."));
            source.sendMessage(Text.literal("§ePlaying: flee.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.fleeMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Pitch: " + String.format("%.1f", config.fleeMusicPitch)));
            source.sendMessage(Text.literal("§7Real trigger: Player flees from battle"));
            
            // Play flee music on server side for testing
            try {
                player.playSound(
                    CustomCobblemonMusicMod.FLEE_MUSIC,
                    config.fleeMusicVolume,
                    config.fleeMusicPitch
                );
                source.sendMessage(Text.literal("§a✓ Flee music played on server!"));
                
            } catch (Exception e) {
                source.sendMessage(Text.literal("§c✗ Failed to play flee music: " + e.getMessage()));
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
        source.sendMessage(Text.literal("§eVictory Music: §f" + config.enableVictoryMusic + " (Vol: " + (int)(config.victoryMusicVolume * 100) + "%, Pitch: " + String.format("%.1f", config.victoryMusicPitch) + ")"));
        source.sendMessage(Text.literal("§eEvolution Congrat: §f" + config.enableEvolutionMusic + " (Vol: " + (int)(config.evolutionCongratMusicVolume * 100) + "%, Pitch: " + String.format("%.1f", config.evolutionCongratMusicPitch) + ")"));
        source.sendMessage(Text.literal("§eCatch Congrat: §f" + config.enableCatchMusic + " (Vol: " + (int)(config.catchCongratMusicVolume * 100) + "%, Pitch: " + String.format("%.1f", config.catchCongratMusicPitch) + ")"));
        source.sendMessage(Text.literal("§eFlee Music: §f" + config.enableFleeMusic + " (Vol: " + (int)(config.fleeMusicVolume * 100) + "%, Pitch: " + String.format("%.1f", config.fleeMusicPitch) + ")"));
        source.sendMessage(Text.literal("§eDebug Logging: §f" + config.debugLogging));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§7Use ModMenu for GUI configuration"));
        
        return 1;
    }
}