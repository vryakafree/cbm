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
        dispatcher.register(CommandManager.literal("cobblemusic")
            .then(CommandManager.literal("status")
                .executes(CustomCobblemonMusicModCommands::showStatus))
            .then(CommandManager.literal("test")
                .then(CommandManager.literal("victory")
                    .executes(CustomCobblemonMusicModCommands::testVictoryMusic))
                .then(CommandManager.literal("evolution")
                    .executes(CustomCobblemonMusicModCommands::testEvolutionMusic))
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
        
        source.sendMessage(Text.literal("§6=== Custom Cobblemon Music Mod Status ==="));
        source.sendMessage(Text.literal("§eVersion: §f1.0.1"));
        source.sendMessage(Text.literal("§eActive Music System: §fVictory, Evolution & Catch only"));
        source.sendMessage(Text.literal("§eBattle Music: §fHandled by Cobblemon/Resource Packs"));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Enabled Features:"));
        source.sendMessage(Text.literal("§7- Victory Music: §" + (config.enableVictoryMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Evolution Music: §" + (config.enableEvolutionMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal("§7- Catch Music: §" + (config.enableCatchMusic ? "a✓" : "c✗")));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§6Commands:"));
        source.sendMessage(Text.literal("§7- /cobblemusic test victory"));
        source.sendMessage(Text.literal("§7- /cobblemusic test evolution"));
        source.sendMessage(Text.literal("§7- /cobblemusic test catch"));
        source.sendMessage(Text.literal("§7- /cobblemusic stop"));
        
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
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.victoryMusicVolume * 100) + "%"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testEvolutionMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
            
            if (!config.enableEvolutionMusic) {
                source.sendMessage(Text.literal("§cEvolution music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing evolution music..."));
            source.sendMessage(Text.literal("§ePlaying: evo.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.evolutionMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon evolution start"));
            
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
                source.sendMessage(Text.literal("§cEvolution music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing evolution congratulations music..."));
            source.sendMessage(Text.literal("§ePlaying: evo_congrat.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.evolutionCongratMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon evolution complete"));
            
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
                source.sendMessage(Text.literal("§cCatch music is disabled in config"));
                return 0;
            }
            
            source.sendMessage(Text.literal("§6Testing catch music..."));
            source.sendMessage(Text.literal("§ePlaying: catch_congrat.ogg"));
            source.sendMessage(Text.literal("§7Volume: " + (int)(config.catchCongratMusicVolume * 100) + "%"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon caught"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int stopMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Stopping all custom music..."));
            source.sendMessage(Text.literal("§7Note: This only stops mod's custom music"));
            source.sendMessage(Text.literal("§7Battle music is handled by Cobblemon/Resource Packs"));
            
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
        source.sendMessage(Text.literal("§eEvolution Music: §f" + config.enableEvolutionMusic + " (Vol: " + (int)(config.evolutionMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eEvolution Congrat: §f" + config.enableEvolutionMusic + " (Vol: " + (int)(config.evolutionCongratMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eCatch Music: §f" + config.enableCatchMusic + " (Vol: " + (int)(config.catchCongratMusicVolume * 100) + "%)"));
        source.sendMessage(Text.literal("§eVictory Duration: §f" + (config.victoryMusicDuration / 1000) + " seconds"));
        source.sendMessage(Text.literal("§eDebug Logging: §f" + config.debugLogging));
        source.sendMessage(Text.literal(""));
        source.sendMessage(Text.literal("§7Use ModMenu for GUI configuration"));
        
        return 1;
    }
}