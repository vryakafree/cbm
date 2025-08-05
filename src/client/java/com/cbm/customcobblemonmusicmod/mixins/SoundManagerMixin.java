package com.cbm.customcobblemonmusicmod.mixins;

import com.cbm.customcobblemonmusicmod.CobblemonSoundInterceptor;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SoundManager.class)
public class SoundSystemMixin {
    
    @ModifyArg(method = "play", at = @At("HEAD"), index = 0)
    private SoundInstance interceptSound(SoundInstance soundInstance) {
        return CobblemonSoundInterceptor.interceptCobblemonSound(soundInstance);
    }
}