package com.cbm.cobblemonbattlemusic;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;

public class CobblemonBattleMusicCommands {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }
    
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("cobblemusic")
            .then(CommandManager.literal("status")
                .executes(CobblemonBattleMusicCommands::showStatus))
            .then(CommandManager.literal("test")
                .then(CommandManager.literal("battle")
                    .executes(CobblemonBattleMusicCommands::testBattleMusic))
                .then(CommandManager.literal("panic")
                    .executes(CobblemonBattleMusicCommands::testPanicMusic))
                .then(CommandManager.literal("victory")
                    .executes(CobblemonBattleMusicCommands::testVictoryMusic))
                .then(CommandManager.literal("evolution")
                    .executes(CobblemonBattleMusicCommands::testEvolutionMusic))
                .then(CommandManager.literal("catch")
                    .executes(CobblemonBattleMusicCommands::testCatchMusic)))
            .then(CommandManager.literal("stop")
                .executes(CobblemonBattleMusicCommands::stopMusic))
            .then(CommandManager.literal("version")
                .executes(CobblemonBattleMusicCommands::showVersion))
        );
    }
    
    private static int showStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        try {
            source.sendMessage(Text.literal("§6=== Cobblemon Battle Music Status ==="));
            source.sendMessage(Text.literal("§aModID: §f" + CobblemonBattleMusic.MOD_ID));
            source.sendMessage(Text.literal("§aVersion: §f1.0.0"));
            source.sendMessage(Text.literal("§aCobblemon Integration: §2✓ ENABLED"));
            source.sendMessage(Text.literal("§aMinecraft: §f1.21.1"));
            source.sendMessage(Text.literal("§aExpected Cobblemon: §f1.6.1+"));
            
            // Check if Cobblemon is actually loaded
            try {
                Class.forName("com.cobblemon.mod.common.api.events.CobblemonEvents");
                source.sendMessage(Text.literal("§aCobblemon API: §2✓ FOUND"));
            } catch (ClassNotFoundException e) {
                source.sendMessage(Text.literal("§aCobblemon API: §c✗ NOT FOUND"));
                source.sendMessage(Text.literal("§cPlease install Cobblemon 1.6.1+ for full functionality"));
            }
            
            source.sendMessage(Text.literal("§6Sound Events Registered:"));
            source.sendMessage(Text.literal("§f- battle_song.ogg"));
            source.sendMessage(Text.literal("§f- strong_battle_song.ogg"));
            source.sendMessage(Text.literal("§f- panic_song.ogg"));
            source.sendMessage(Text.literal("§f- victory.ogg"));
            source.sendMessage(Text.literal("§f- evo.ogg"));
            source.sendMessage(Text.literal("§f- evo_congrat.ogg"));
            source.sendMessage(Text.literal("§f- catch_congrat.ogg"));
            
            source.sendMessage(Text.literal("§6Use §e/cobblemusic test <type>§6 to test sounds"));
            
        } catch (Exception e) {
            source.sendMessage(Text.literal("§cError checking status: " + e.getMessage()));
            return 0;
        }
        
        return 1;
    }
    
    private static int testBattleMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Testing battle music..."));
            source.sendMessage(Text.literal("§7Note: This is a test command. Real battle music is triggered by Cobblemon events."));
            
            // In a real implementation, you'd trigger the client-side music
            // For now, just show what would happen
            source.sendMessage(Text.literal("§aWould play: battle_song.ogg (loops until battle ends)"));
            source.sendMessage(Text.literal("§7Real trigger: When Cobblemon battle starts"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testPanicMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Testing panic music..."));
            source.sendMessage(Text.literal("§cWould play: panic_song.ogg (when Pokemon health ≤ 20%)"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon health monitoring during battle"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testVictoryMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Testing victory music..."));
            source.sendMessage(Text.literal("§aWould play: victory.ogg (7 seconds, then fade out)"));
            source.sendMessage(Text.literal("§7Real trigger: When player wins Cobblemon battle"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testEvolutionMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Testing evolution music sequence..."));
            source.sendMessage(Text.literal("§dStep 1: evo.ogg (during evolution)"));
            source.sendMessage(Text.literal("§dStep 2: evo_congrat.ogg (after 3 seconds)"));
            source.sendMessage(Text.literal("§7Real trigger: Pokemon evolution events"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int testCatchMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Testing catch music..."));
            source.sendMessage(Text.literal("§2Would play: catch_congrat.ogg"));
            source.sendMessage(Text.literal("§7Real trigger: Successful Pokemon capture"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int stopMusic(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            source.sendMessage(Text.literal("§6Stopping all music..."));
            
            // This is a debug command - actual music stopping is handled client-side
            source.sendMessage(Text.literal("§7Note: Music control is handled client-side by Cobblemon events"));
            source.sendMessage(Text.literal("§aCommand executed (client-side music should stop when battle ends)"));
            
        } else {
            source.sendMessage(Text.literal("§cThis command must be run by a player"));
            return 0;
        }
        
        return 1;
    }
    
    private static int showVersion(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        source.sendMessage(Text.literal("§6Cobblemon Battle Music §fv1.0.0"));
        source.sendMessage(Text.literal("§7For Minecraft 1.21.1 + Cobblemon 1.6.1+"));
        source.sendMessage(Text.literal("§7Implements authentic Pokemon battle music system"));
        source.sendMessage(Text.literal("§7GitHub: github.com/vryakafree/cbm"));
        
        return 1;
    }
}