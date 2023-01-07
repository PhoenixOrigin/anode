package net.ano;


import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

import java.util.logging.Logger;

public class anode {
    public static final String MOD_ID = "anode";
    public static Logger logger = null;
    public static String classname;

    public static void init() {
        logger = Logger.getAnonymousLogger();
        CommandDispatcher<CommandSourceStack> disp = new CommandDispatcher<>();
        CommandManager.register(disp);
    }

}
