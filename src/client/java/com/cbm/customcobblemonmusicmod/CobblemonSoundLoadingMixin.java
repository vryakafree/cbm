package com.cbm.customcobblemonmusicmod;

import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class CobblemonSoundLoadingMixin {
    
    @Inject(method = "registerSound(Lnet/minecraft/sound/SoundEvent;)V", at = @At("HEAD"))
    private void onRegisterSound(SoundEvent sound, CallbackInfo ci) {
        CustomCobblemonMusicModConfig config = CustomCobblemonMusicModConfig.getInstance();
        
        // Only process if Cobblemon sound control is enabled
        if (!config.enableCobblemonSoundControl) {
            return;
        }
        
        Identifier soundId = sound.getId();
        
        // Check if this is a Cobblemon sound
        if (CobblemonSoundInterceptor.isCobblemonSound(soundId)) {
            // Debug logging for sound registration
            if (config.debugLogging) {
                CustomCobblemonMusicMod.LOGGER.info("Registered Cobblemon sound: " + soundId);
            }
        }
    }
}