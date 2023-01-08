package net.ano;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;

import java.util.logging.Logger;

public class anode {
    public static final String MOD_ID = "anode";
    public static Logger logger = null;
    public static String classname;
    public static CommandDispatcher<CommandSourceStack> disp = null;
    public static LiteralCommandNode<CommandSourceStack> node = null;
    private static Minecraft minecraft = null;
    private static LocalPlayer player = null;
    private static ClientPacketListener connection = null;

    public static void init() {
        logger = Logger.getAnonymousLogger();
        disp = new CommandDispatcher<>();
        CommandManager.register(disp);
        minecraft = Minecraft.getInstance();
        player = minecraft.player;
        connection = minecraft.getConnection();
    }

    public static LocalPlayer getPlayer() {
        return player;
    }

    public static Minecraft getMc() {
        return minecraft;
    }

    public static ClientPacketListener getConnection() {
        return connection;
    }

}
