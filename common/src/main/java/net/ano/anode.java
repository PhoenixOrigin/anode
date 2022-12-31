package net.ano;

import com.google.common.base.Suppliers;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.registry.registries.Registries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.world.BossEvent;

import java.util.function.Supplier;

public class anode {
    public static final String MOD_ID = "anode";
    public static Minecraft mc = null;
    public static void init() {
        mc = Minecraft.getInstance();
        ClientChatEvent.RECEIVED.register(EventListener::processChat);

    }

}
