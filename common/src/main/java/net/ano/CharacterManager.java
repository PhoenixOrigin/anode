package net.ano;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterManager {

    private static boolean compassMenu = false;
    private static final Pattern INFO_MENU_CLASS_PATTERN
            = Pattern.compile("§7Class: §r§f(.+)");
    public static void openClassMenu(){
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener packets = mc.getConnection();
        int prevItem = mc.player.getInventory().selected;
        packets.send(new ServerboundSetCarriedItemPacket(6));
        packets.send(new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, 1));
        packets.send(new ServerboundSetCarriedItemPacket(prevItem));
    }

    public static void containerItemsSet(ClientboundContainerSetContentPacket packet){
        if(!compassMenu) return;
        ItemStack stack = packet.getItems().get(7);
        Minecraft.getInstance().player.closeContainer();
        compassMenu = false;
        List<String> lore = ItemUtils.getLore(stack);
        for (String line : lore){
            Matcher classMatcher = INFO_MENU_CLASS_PATTERN.matcher(line);

            if (classMatcher.matches()) {
                anode.classname = classMatcher.group(1);
                return;
            }
        }
    }

    public static void containerOpen(ClientboundOpenScreenPacket packet){
        if(ChatFormatting.stripFormatting(packet.getTitle().getString()).equals("Character Info")) compassMenu = true;
    }

}
