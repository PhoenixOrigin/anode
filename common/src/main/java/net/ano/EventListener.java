package net.ano;

import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.common.ChatEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListener {

    public static void onChat(Component chat){
        //processing here
    }
}