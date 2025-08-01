
package com.cbm.cobblemonbattlemusic;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CobblemonBattleMusic implements ModInitializer {
    public static final String MOD_ID = "cobblemonbattlemusic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Sound Events
    public static final SoundEvent BATTLE_MUSIC = registerSoundEvent("battle_music");
    public static final SoundEvent LOW_HEALTH_MUSIC = registerSoundEvent("low_health_music");
    public static final SoundEvent VICTORY_MUSIC = registerSoundEvent("victory_music");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Cobblemon Battle Music mod");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
