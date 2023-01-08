package net.ano.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ano.CharacterManager;
import net.ano.EventListener;
import net.ano.anode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
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
            method = "sendCommand",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void sendCommand(String string, CallbackInfo ci) {
        assert string.startsWith("/anode");
        final ParseResults<CommandSourceStack> parse = anode.disp.parse(new StringReader(string), anode.getPlayer().createCommandSourceStack());
        if (!parse.getExceptions().isEmpty()
                || (parse.getContext().getCommand() == null
                && parse.getContext().getChild() == null)) {
            return;
        }
        if (ci.isCancellable()) ci.cancel();
        try {
            anode.disp.execute(parse);
        } catch (CommandSyntaxException e) {
            anode.getPlayer().sendSystemMessage(Component.literal(ChatFormatting.RED + "Command failure"));
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
