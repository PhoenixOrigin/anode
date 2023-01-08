package net.ano;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class CommandManager {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> node = dispatcher.register(new CommandManager().getBaseCommandBuilder());
        anode.node = dispatcher.register(Commands.literal("anode").redirect(node));
    }

    public LiteralArgumentBuilder<CommandSourceStack> getBaseCommandBuilder() {
        LiteralCommandNode<CommandSourceStack> getClassNode = Commands.literal("getClass")
                .executes(this::getPlayerClass)
                .build();

        LiteralCommandNode<CommandSourceStack> getFeaturesNode = Commands.literal("getFeatures")
                .executes(this::getFeatures)
                .build();

        return Commands.literal("anode")
                .then(getClassNode)
                .then(Commands.literal("gc").redirect(getClassNode))
                .then(getFeaturesNode)
                .then(Commands.literal("gf").redirect(getFeaturesNode))
                .executes(this::serverHelp);
    }

    public static CompletableFuture<Suggestions> getCompletionSuggestions(String cmd, CommandDispatcher<SharedSuggestionProvider> serverDispatcher, ParseResults<CommandSourceStack> clientParse, ParseResults<SharedSuggestionProvider> serverParse, int cursor) {
        StringReader stringReader = new StringReader(cmd);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }

        CommandDispatcher<CommandSourceStack> clientDispatcher = anode.disp;

        CompletableFuture<Suggestions> clientSuggestions =
                clientDispatcher.getCompletionSuggestions(clientParse, cursor);
        CompletableFuture<Suggestions> serverSuggestions =
                serverDispatcher.getCompletionSuggestions(serverParse, cursor);

        CompletableFuture<Suggestions> result = new CompletableFuture<>();

        CompletableFuture.allOf(clientSuggestions, serverSuggestions).thenRun(() -> {
            final List<Suggestions> suggestions = new ArrayList<>();
            suggestions.add(clientSuggestions.join());
            suggestions.add(serverSuggestions.join());
            result.complete(Suggestions.merge(stringReader.getString(), suggestions));
        });

        return result;
    }


    private int serverHelp(CommandContext<CommandSourceStack> context) {
        MutableComponent help = Component.literal(
                ChatFormatting.RED +
                        """
                                ANODE Help Message
                                                
                                Commands:
                                    getClass:
                                        Usage: /anode getClass
                                               /anode gc
                                        Description: Opens the compass menu and analyses your class for wars. Opening your compass menu has the same effect
                                        Status: STABLE
                                    getFeatures:
                                        Usage: /anode getFeatures
                                               /anode gf
                                        Description: Prints a list of anode features
                                        Status: STABLE
                                    help:
                                        Usage: /anode <anything>
                                        Description: Help Menu (this)
                                        Status: STABLE
                                      
                                For any bugs / glitches message PhoenixOrigin#7083, Wolv#1065, Andrew#9823 or any high ranking titan's brilliance member. 
                                """);

        context.getSource().sendSuccess(help, false);

        return 1;
    }

    private int getFeatures(CommandContext<CommandSourceStack> context) {
        MutableComponent help = Component.literal(
                ChatFormatting.RED +
                        """
                                ANODE Features List
                                                        
                                Features:
                                    warChoke:
                                        Usage: Triggers on GUI open
                                        Description: Changes the texture of choked territories. Uses 2 api calls
                                        Status: ALPHA
                                    warTrack:
                                        Usage: Automatically triggers after a war starts
                                        Description: When you get into the war, it will parse the territory data and your class and send it to our war tracking api
                                        Status: BETA
                                                        
                                For any bugs / glitches message PhoenixOrigin#7083, Wolv#1065, Andrew#9823 or any high ranking titan's brilliance member. 
                                """);

        context.getSource().sendSuccess(help, false);

        return 0;
    }

    private int getPlayerClass(CommandContext<CommandSourceStack> context) {
        CharacterManager.openClassMenu();
        context.getSource().sendSuccess(Component.literal("Successfully tracker class"), false);
        return 0;
    }

}
