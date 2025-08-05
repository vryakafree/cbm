package com.cbm.customcobblemonmusicmod.mixins;

import com.cbm.customcobblemonmusicmod.CobblemonSoundInterceptor;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    
    @ModifyArg(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), index = 0)
    private SoundInstance interceptSound(SoundInstance soundInstance) {
        return CobblemonSoundInterceptor.interceptCobblemonSound(soundInstance);
    }
}