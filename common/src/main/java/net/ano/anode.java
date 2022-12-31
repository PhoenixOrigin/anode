package net.ano;

import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.ClickEvent;

import java.util.logging.Logger;

public class anode {
    public static final String MOD_ID = "anode";
    public static Minecraft mc = null;
    public static Logger logger = null;
    public static void init() {
        mc = Minecraft.getInstance();
        ClientChatEvent.RECEIVED.register(EventListener::processChat);
        logger = Logger.getAnonymousLogger();
    }

}
