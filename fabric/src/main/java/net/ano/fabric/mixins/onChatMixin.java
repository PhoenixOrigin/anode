package net.ano.fabric.mixins;

import dev.architectury.event.events.common.ChatEvent;
import dev.architectury.networking.simple.MessageType;
import net.fabricmc.fabric.mixin.client.rendering.MixinInGameHud;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.w3c.dom.Text;

@Mixin(MixinInGameHud.class)
public class onChatMixin {

    @Inject(method = "onGameMessage", at = @At("RETURN"), cancellable = true)
    public void onGameMessage(MessageType type, Text message, CallbackInfo ci) {
        Component.
    }

}