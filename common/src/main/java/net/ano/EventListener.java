package net.ano;

import dev.architectury.event.CompoundEventResult;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventListener {

    private static final Pattern pattern = Pattern.compile("/§3\\[WAR§3] The battle has begun!/gm");
    private static boolean inWar = false;
    public static CompoundEventResult<Component> processChat(ChatType.Bound bound, Component component) {
        Matcher matcher = pattern.matcher(ComponentUtils.getCoded(component));
        if (!matcher.matches()) return CompoundEventResult.pass();
        inWar = true;
        return CompoundEventResult.pass();
    }

}