package net.ano.fabric;

import net.ano.anode;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.multiplayer.chat.ChatListener;

public class anodeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        anode.init();
    }
}
