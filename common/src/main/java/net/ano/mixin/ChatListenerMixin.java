package net.ano.mixin;

import com.mojang.authlib.GameProfile;
import net.ano.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
    @Inject(at = @At("HEAD"), method = "handlePlayerChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/network/chat/ChatType$Bound;)V")
    private void handlePlayerChat(PlayerChatMessage playerChatMessage, GameProfile gameProfile, ChatType.Bound bound, CallbackInfo ci) {
        System.out.println("wokring");
        EventListener.processChat(playerChatMessage.unsignedContent());
    }
}