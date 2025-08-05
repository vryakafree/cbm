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
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            return;
        }
        
        // Get the sound ID
        Identifier soundId = sound.getId();
        
        // Check if this is a Cobblemon sound
        if (!soundId.getNamespace().equals("cobblemon")) {
            return;
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
        
        // Debug logging
        if (config.debugLogging) {
            CustomCobblemonMusicMod.LOGGER.info("Intercepted Cobblemon sound: " + soundId + 
                " | Original vol: " + sound.getVolume() + ", pitch: " + sound.getPitch());
        }
    }
}