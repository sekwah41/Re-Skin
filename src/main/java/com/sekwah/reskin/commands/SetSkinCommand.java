package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.commands.arguments.URLArgument;
import com.sekwah.reskin.config.SkinConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class SetSkinCommand {

    private static final String URL_ARG = "url";

    private static SuggestionProvider<CommandSource> URL_SUGGESTIONS = (ctx, builder)
            -> ISuggestionProvider.suggest(new String[]{"https://", "https://i.imgur.com/mORJxcm.png"}, builder);

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSource> setSkin = literal("setskin")
                .requires((sender) -> (!SkinConfig.SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                .then(argument(URL_ARG, URLArgument.urlArg())
                        .suggests(URL_SUGGESTIONS)
                        .executes(ctx -> {
                            ServerPlayerEntity entity = ctx.getSource().getPlayerOrException();
                            String url = URLArgument.getURL(ctx, URL_ARG);
                            return execute(ctx.getSource(), Collections.singletonList(entity), url);
                        })
                        .requires(sender -> (!SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                        .then(argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    String url = URLArgument.getURL(ctx, URL_ARG);
                                    Collection<ServerPlayerEntity> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                                    return execute(ctx.getSource(), targetPlayers, url);
                                })));

        dispatcher.register(setSkin);
    }

    private static int execute(CommandSource source, Collection<ServerPlayerEntity> targets, String skinUrl) {
        String url = skinUrl;
        List<? extends String> whitelist = SkinConfig.SKIN_SERVER_WHITELIST.get();
        long passedWhitelist = whitelist.stream().filter(value -> url.startsWith(value)).count();
        if (Boolean.TRUE.equals(SkinConfig.ENABLE_SKIN_SERVER_WHITELIST.get()) && passedWhitelist == 0) {
            TranslationTextComponent message = new TranslationTextComponent("setskin.notwhitelisted");
            Style redMessage = message.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.RED));
            source.sendSuccess(message.setStyle(redMessage), false);
            return -1;
        }
        targets.forEach(target -> {
            if (target == null) {
                return;
            }
            source.sendSuccess(new TranslationTextComponent("setskin.setplayerskin", target.getDisplayName(), url), false);
            CustomSkinManager.setSkin(target, url);
        });
        if (targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
