package net.ano.forge;

import dev.architectury.platform.forge.EventBuses;
import net.ano.anode;
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
