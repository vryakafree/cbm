package com.cbm.customcobblemonmusicmod;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class CobblemonSoundMixin {
    
    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    private void onPlaySound(SoundInstance sound, CallbackInfo ci) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Get the sound ID
        Identifier soundId = sound.getId();
        
        // Enhanced debug logging for all sounds when debug is enabled
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("=== SOUND INTERCEPTION DEBUG ===");
            CustomCobblemonMusicMod.LOGGER.info("Sound played: " + soundId);
            CustomCobblemonMusicMod.LOGGER.info("Namespace: " + soundId.getNamespace());
            CustomCobblemonMusicMod.LOGGER.info("Path: " + soundId.getPath());
            CustomCobblemonMusicMod.LOGGER.info("Volume: " + sound.getVolume());
            CustomCobblemonMusicMod.LOGGER.info("Pitch: " + sound.getPitch());
            CustomCobblemonMusicMod.LOGGER.info("Category: " + sound.getCategory());
            CustomCobblemonMusicMod.LOGGER.info("Cobblemon sound control enabled: " + config.enableCobblemonSoundControl);
            CustomCobblemonMusicMod.LOGGER.info("Is Cobblemon sound: " + CobblemonSoundInterceptor.isCobblemonSound(soundId));
            CustomCobblemonMusicMod.LOGGER.info("================================");
        }
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            if (config.debugLogging && soundId.getNamespace().equals("cobblemon")) {
                CustomCobblemonMusicMod.LOGGER.info("Cobblemon sound control disabled, playing original sound: " + soundId);
            }
            return;
        }
        
        // Check if this is a Cobblemon sound
        if (!CobblemonSoundInterceptor.isCobblemonSound(soundId)) {
            if (config.debugLogging && soundId.getNamespace().equals("cobblemon")) {
                CustomCobblemonMusicMod.LOGGER.info("Sound not recognized as Cobblemon sound: " + soundId);
            }
            return;
        }
        
        // Debug logging for intercepted Cobblemon sounds
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("*** INTERCEPTING COBBLEMON SOUND ***");
            CustomCobblemonMusicMod.LOGGER.info("Sound ID: " + soundId);
            CustomCobblemonMusicMod.LOGGER.info("Original volume: " + sound.getVolume() + ", pitch: " + sound.getPitch());
            CustomCobblemonMusicMod.LOGGER.info("Category: " + CobblemonSoundInterceptor.getCobblemonSoundCategory(soundId));
            CustomCobblemonMusicMod.LOGGER.info("*******************************");
        }
        
        // Cancel the original sound
        ci.cancel();
        
        try {
            // Create and play the modified sound
            SoundInstance modifiedSound = CobblemonSoundInterceptor.createModifiedCobblemonSound(
                soundId, 
                sound.getVolume(), 
                sound.getPitch()
            );
            
            // Play the modified sound
            ((SoundManager) (Object) this).play(modifiedSound);
            
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Successfully created and played modified sound for: " + soundId);
            }
        } catch (Exception e) {
            CustomCobblemonMusicMod.LOGGER.error("Error creating modified Cobblemon sound for " + soundId + ": " + e.getMessage());
            e.printStackTrace();
            // Fallback: play original sound if modification fails
            try {
                ((SoundManager) (Object) this).play(sound);
                if (config.debugLogging) {
                    CustomCobblemonMusicMod.LOGGER.info("Fallback: played original sound for: " + soundId);
                }
            } catch (Exception fallbackError) {
                CustomCobblemonMusicMod.LOGGER.error("Failed to play fallback sound for " + soundId + ": " + fallbackError.getMessage());
            }
        }
    }
}