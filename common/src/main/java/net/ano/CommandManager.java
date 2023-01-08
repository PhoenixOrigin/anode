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
        dispatcher.register(Commands.literal("ano").redirect(node));
    }

    public LiteralArgumentBuilder<CommandSourceStack> getBaseCommandBuilder() {
        LiteralCommandNode<CommandSourceStack> updateClassNode = Commands.literal("updateClass")
                .executes(this::updatePlayerClass)
                .build();

        LiteralCommandNode<CommandSourceStack> ucNode = Commands.literal("uc")
                .executes(this::updatePlayerClass)
                .build();

        LiteralCommandNode<CommandSourceStack> getClassNode = Commands.literal("getClass")
                .executes(this::getPlayerClass)
                .build();

        LiteralCommandNode<CommandSourceStack> gcNode = Commands.literal("gc")
                .executes(this::getPlayerClass)
                .build();

        LiteralCommandNode<CommandSourceStack> getFeaturesNode = Commands.literal("getFeatures")
                .executes(this::getFeatures)
                .build();

        LiteralCommandNode<CommandSourceStack> gfNode = Commands.literal("gf")
                .executes(this::getFeatures)
                .build();

        return Commands.literal("anode")
                .then(getClassNode)
                .then(gcNode)
                .then(updateClassNode)
                .then(ucNode)
                .then(getFeaturesNode)
                .then(gfNode)
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
                                    updateClass:
                                        Usage: /anode updateClass
                                               /anode uc
                                               /ano updateClass
                                               /ano uc
                                        Description: Analyzes your current class.
                                        Status: STABLE
                                    getClass:
                                        Usage: /anode getClass
                                               /anode gc
                                               /ano getClass
                                               /ano gc
                                        Description: Prints your current class.
                                        Status: STABLE
                                    getFeatures:
                                        Usage: /anode getFeatures
                                               /anode gf
                                               /ano getFeatures
                                               /ano gf
                                        Description: Prints a list of anode features
                                        Status: STABLE
                                    help:
                                        Usage: /anode <anything>
                                               /ano <anything>
                                        Description: Help Menu (this)
                                        Status: STABLE
                                      
                                For any bugs / glitches message PhoenixOrigin#7083, Andrew#9823 or any high ranking titan's brilliance member.
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
                                        Description: Changes the texture of choked territories.
                                        Status: ALPHA
                                    warTrack:
                                        Usage: Automatically triggers after a war starts
                                        Description: Sends war attempts to API.
                                        Status: BETA
                                                        
                                For any bugs / glitches message PhoenixOrigin#7083, Wolv#1065, Andrew#9823 or any high ranking titan's brilliance member.
                                """);

        context.getSource().sendSuccess(help, false);

        return 0;
    }

    private int updatePlayerClass(CommandContext<CommandSourceStack> context) {
        CharacterManager.openClassMenu();
        context.getSource().sendSuccess(Component.literal("Successfully tracker class"), false);
        return 0;
    }

    private int getPlayerClass(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(Component.literal(ChatFormatting.AQUA + "Current player class: " + anode.classname), false);
        return 0;
    }

}
