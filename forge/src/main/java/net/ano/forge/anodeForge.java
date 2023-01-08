package net.ano.forge;

import net.ano.anode;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL13;


@Mod(anode.MOD_ID)
public class anodeForge {
    public anodeForge() {
        anode.init();
    }

    @SubscribeEvent
    //@SideOnly(Side.CLIENT)
    public void rotateEntities(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        GL13.glPushMatrix();
        GL13.glTranslatef(0.0F, (float) (event.getEntity().getY() * 2f + event.getEntity().getBoundingBox().getYsize()), 0.0F);
        GL13.glRotatef(180f, 0, 0, 0);
        GL13.glRotatef(180f, 0, 1, 0);

    }

    @SubscribeEvent
    //@SideOnly(Side.CLIENT)
    public void rotateEntities(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        GL13.glPopMatrix();
    }

}
