package net.ano;

import com.google.common.base.Suppliers;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.common.ChatEvent;
import dev.architectury.registry.registries.Registries;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Supplier;

public class anode {
    public static final String MOD_ID = "anode";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    public static Minecraft mc = null;
    public static void init() {
        mc = Minecraft.getInstance();
        ClientChatEvent.RECEIVED.register(EventListener::processChat);
    }

}
