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
        
        // Debug logging for all sounds when debug is enabled
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Sound played: " + soundId + 
                " | Namespace: " + soundId.getNamespace() + 
                " | Path: " + soundId.getPath() + 
                " | Volume: " + sound.getVolume() + 
                " | Pitch: " + sound.getPitch());
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
            CustomCobblemonMusicMod.LOGGER.info("Intercepting Cobblemon sound: " + soundId + 
                " | Original vol: " + sound.getVolume() + ", pitch: " + sound.getPitch() +
                " | Category: " + CobblemonSoundInterceptor.getCobblemonSoundCategory(soundId));
        }
        
        // Cancel the original sound
        ci.cancel();
        
        // Create and play the modified sound
        SoundInstance modifiedSound = CobblemonSoundInterceptor.createModifiedCobblemonSound(
            soundId, 
            sound.getVolume(), 
            sound.getPitch()
        );
        
        // Play the modified sound
        ((SoundManager) (Object) this).play(modifiedSound);
    }
}