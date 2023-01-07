package net.ano.mixin;

import net.ano.CharacterManager;
import net.ano.EventListener;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Inject(
            method = "handleSystemChat",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V")
    )
    private void handlePlayerChat(ClientboundSystemChatPacket clientboundSystemChatPacket, CallbackInfo ci) {
        EventListener.processChat(clientboundSystemChatPacket.content());
    }

    @Inject(
            method = "handleOpenScreen",
            at = @At(
                    value = "HEAD"
            )
    )
    private void handleOpenScreen(ClientboundOpenScreenPacket packet, CallbackInfo ci) {
        CharacterManager.containerOpen(packet);
    }

    @Inject(
            method = "handleContainerContent",
            at = @At(
                    value = "HEAD"
            )
    )
    private void handleContainerItemsSet(ClientboundContainerSetContentPacket packet, CallbackInfo ci) {
        CharacterManager.containerItemsSet(packet);
    }

}
