package net.ano;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.registry.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class anode {
    public static final String MOD_ID = "anode";
    public static Minecraft mc = null;
    public static void init() {
        mc = Minecraft.getInstance();
        ClientChatEvent.RECEIVED.register(EventListener::processChat);
    }

}
