package com.cbm.customcobblemonmusicmod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MusicPacket {
    public static final Identifier PACKET_ID = Identifier.of("customcobblemonmusicmod", "music_packet");
    
    public enum MusicAction {
        PLAY_VICTORY,
        PLAY_EVOLUTION,
        PLAY_EVOLUTION_CONGRAT,
        PLAY_CATCH_CONGRAT,
        STOP_VICTORY,
        STOP_EVOLUTION,
        STOP_ALL,
        FADE_OUT_VICTORY
    }
    
    private final MusicAction action;
    private final float volume;
    private final int fadeDuration;
    
    public MusicPacket(MusicAction action, float volume, int fadeDuration) {
        this.action = action;
        this.volume = volume;
        this.fadeDuration = fadeDuration;
    }
    
    public MusicPacket(MusicAction action) {
        this(action, 1.0f, 2000);
    }
    
    public static MusicPacket read(PacketByteBuf buf) {
        MusicAction action = buf.readEnumConstant(MusicAction.class);
        float volume = buf.readFloat();
        int fadeDuration = buf.readInt();
        return new MusicPacket(action, volume, fadeDuration);
    }
    
    public void write(PacketByteBuf buf) {
        buf.writeEnumConstant(action);
        buf.writeFloat(volume);
        buf.writeInt(fadeDuration);
    }
    
    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        MusicPacket packet = MusicPacket.read(buf);
        
        // Send to client
        PacketByteBuf responseBuf = PacketByteBufs.create();
        packet.write(responseBuf);
        
        // Note: This is a simplified implementation
        // In a real implementation, you would use proper packet registration
    }
    
    public MusicAction getAction() {
        return action;
    }
    
    public float getVolume() {
        return volume;
    }
    
    public int getFadeDuration() {
        return fadeDuration;
    }
}