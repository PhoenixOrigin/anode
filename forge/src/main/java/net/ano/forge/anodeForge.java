package net.ano.forge;

import dev.architectury.platform.forge.EventBuses;
import net.ano.EventListener;
import net.ano.anode;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.util.profiling.jfr.event.PacketEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(anode.MOD_ID)
public class anodeForge {
    public anodeForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(anode.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        anode.init();
    }
}
