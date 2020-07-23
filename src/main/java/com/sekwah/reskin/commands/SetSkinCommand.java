package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.BiConsumer;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class SetSkinCommand {

    private static SuggestionProvider<CommandSource> URL_SUGGESTIONS = (ctx, builder)
            -> ISuggestionProvider.suggest(new String[]{"https://", "https://imgur.com/mORJxcm"}, builder);

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSource> setSkin = literal("setskin")
                .then(argument("url", MessageArgument.message())
                        .suggests(URL_SUGGESTIONS)
                            .executes(ctx -> {
                                ServerPlayerEntity entity = ctx.getSource().asPlayer();
                                ITextComponent url = MessageArgument.getMessage(ctx, "url");
                                return execute(ctx.getSource(), entity, url);
                            }))
                .then(argument("targets", EntityArgument.players())
                        .then(argument("url", MessageArgument.message())
                        .suggests(URL_SUGGESTIONS)
                            .executes(ctx -> {
                                // Test command /setskin Dev https://imgur.com/mORJxcm
                                ServerPlayerEntity targetPlayer = EntityArgument.getPlayer(ctx, "targets");
                                ITextComponent url = MessageArgument.getMessage(ctx, "url");
                                return execute(ctx.getSource(), targetPlayer, url);
                            })));

        dispatcher.register(setSkin);
    }

    private static int execute(CommandSource source, ServerPlayerEntity target, ITextComponent skinUrl) {
        if(target == null) {
            return -1;
        }
        String url = skinUrl.getString().split(" ")[0];
        if(url.contains(" ")) {
            return Command.SINGLE_SUCCESS;
        }
        source.sendFeedback(new TranslationTextComponent("setskin.setplayer", target.getDisplayName(), url), false);

        return Command.SINGLE_SUCCESS;
    }
}
