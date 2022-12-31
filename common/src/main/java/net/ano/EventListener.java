package net.ano;

import dev.architectury.event.CompoundEventResult;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

public class EventListener {

    public static CompoundEventResult<Component> processChat(ChatType.Bound bound, Component component){
        return CompoundEventResult.pass();

    }

}