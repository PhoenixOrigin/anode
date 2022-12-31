package net.ano.mixin;

import net.ano.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(
            method = "handlePlayerChat",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/client/multiplayer/chat/ChatListener;handlePlayerChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/network/chat/ChatType$Bound;)V"),
            cancellable = true)
    private void handlePlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci) {
        if (!Minecraft.getInstance().isSameThread()) return;
        EventListener.processChat(packet.unsignedContent());
    }

}
