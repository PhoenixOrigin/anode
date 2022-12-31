package net.ano.fabric;

import net.ano.anode;
import net.fabricmc.api.ModInitializer;

public class anodeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        anode.init();
    }
}
