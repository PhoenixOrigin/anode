package net.ano.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ano.CharacterManager;
import net.ano.EventListener;
import net.ano.anode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.brigadier.StringReader;

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
            method = "sendChat",
            at = @At(
                    value = "HEAD"
            )
    )
    public void onChatSend(String string, CallbackInfo ci){
        assert string.startsWith("/ano") || string.startsWith("/anode");

        try {
            anode.node.parse(new StringReader(string), new CommandContextBuilder<CommandSourceStack>(anode.disp, Minecraft.getInstance().player.createCommandSourceStack(), anode.node, 0));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(
            method = "sendCommand",
            at = @At(
                    value = "HEAD"
            )
    )
    private void sendCommand(String string, CallbackInfo ci){
        try {
            anode.node.parse(new StringReader(string), new CommandContextBuilder<>(anode.disp, Minecraft.getInstance().player.createCommandSourceStack(), anode.node, 0));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
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
