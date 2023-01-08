package net.ano;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;

import java.util.logging.Logger;

public class anode {
    public static final String MOD_ID = "anode";
    public static Logger logger = null;
    public static String classname;
    public static CommandDispatcher<CommandSourceStack> disp = null;
    public static LiteralCommandNode<CommandSourceStack> node = null;
    public static Minecraft minecraft = null;

    public static void init() {
        logger = Logger.getAnonymousLogger();
        disp = new CommandDispatcher<>();
        CommandManager.register(disp);
        minecraft = Minecraft.getInstance();
    }

}
