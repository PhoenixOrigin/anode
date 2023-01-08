package net.ano.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.ano.CommandManager;
import net.ano.anode;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestions.class)
public abstract class CommandSuggestionsMixin {
    @Shadow
    @Final
    EditBox input;

    private ParseResults<CommandSourceStack> clientParse;

    @Redirect(
            method = "updateCommandInfo",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lcom/mojang/brigadier/CommandDispatcher;getCompletionSuggestions(Lcom/mojang/brigadier/ParseResults;I)Ljava/util/concurrent/CompletableFuture;",
                    remap = false))
    private CompletableFuture<Suggestions> redirectSuggestions(
            CommandDispatcher<SharedSuggestionProvider> serverDispatcher,
            ParseResults<SharedSuggestionProvider> serverParse,
            int cursor) {
        return CommandManager.getCompletionSuggestions(
                input.getValue(), serverDispatcher, clientParse, serverParse, cursor);
    }

    @Redirect(
            method = "updateCommandInfo",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lcom/mojang/brigadier/CommandDispatcher;parse(Lcom/mojang/brigadier/StringReader;Ljava/lang/Object;)Lcom/mojang/brigadier/ParseResults;",
                    remap = false))
    private ParseResults<SharedSuggestionProvider> redirectParse(
            CommandDispatcher<SharedSuggestionProvider> serverDispatcher, StringReader command, Object source) {
        clientParse = anode.disp.parse(command, anode.getPlayer().createCommandSourceStack());

        return serverDispatcher.parse(command, (SharedSuggestionProvider) source);
    }
}

