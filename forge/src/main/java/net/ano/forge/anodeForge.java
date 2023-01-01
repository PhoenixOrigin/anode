package net.ano.forge;

import net.ano.anode;
import net.minecraft.client.model.EntityModel;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.swing.text.html.parser.Entity;

@Mod(anode.MOD_ID)
public class anodeForge {
    public anodeForge() {
        anode.init();
    }

    @SubscribeEvent
    public static void preRenderPlayer(RenderPlayerEvent.Pre event){
        GL12.glRotated(180, event.getEntity().getBlockX(), event.getEntity().getBlockY(), event.getEntity().getBlockZ());
    }

}
