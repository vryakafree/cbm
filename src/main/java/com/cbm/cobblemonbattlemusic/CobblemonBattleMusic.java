
package com.cbm.cobblemonbattlemusic;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;

public class CobblemonBattleMusic implements ModInitializer {
    private static final Identifier BATTLE_MUSIC = new Identifier("cobblemonbattlemusic", "battle_music");
    private static final Identifier LOW_HEALTH_MUSIC = new Identifier("cobblemonbattlemusic", "low_health_music");
    private static final Identifier VICTORY_MUSIC = new Identifier("cobblemonbattlemusic", "victory_music");

    @Override
    public void onInitialize() {
        // Register events and logic here
    }

    public static void playBattleMusic() {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(BATTLE_MUSIC), 1.0F));
    }

    public static void playLowHealthMusic() {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(LOW_HEALTH_MUSIC), 1.0F));
    }

    public static void playVictoryMusic() {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(VICTORY_MUSIC), 1.0F));
        // Schedule volume reduction after 8 seconds
        new Thread(() -> {
            try {
                Thread.sleep(8000);
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(VICTORY_MUSIC), 0.3F));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
