package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.core.arguments.URLArgument;
import com.sekwah.reskin.config.SkinConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetSkinCommand {

    private static final String URL_ARG = "url";

    private static SuggestionProvider<CommandSourceStack> URL_SUGGESTIONS = (ctx, builder)
            -> SharedSuggestionProvider.suggest(new String[]{"https://", "https://i.imgur.com/mORJxcm.png"}, builder);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSourceStack> setSkin = literal("setskin")
                .then(argument(URL_ARG, URLArgument.urlArg())
                        .requires((sender) -> (!SkinConfig.SELF_SKIN_NEEDS_OP.get() || !SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                        .suggests(URL_SUGGESTIONS)
                        .executes(ctx -> {
                            ServerPlayer entity = ctx.getSource().getPlayerOrException();
                            String url = URLArgument.getURL(ctx, URL_ARG);
                            return execute(ctx.getSource(), Collections.singletonList(entity), url);
                        })
                        .then(argument("targets", EntityArgument.players())
                                .requires(sender -> (!SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                                .executes(ctx -> {
                                    String url = URLArgument.getURL(ctx, URL_ARG);
                                    Collection<ServerPlayer> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                                    return execute(ctx.getSource(), targetPlayers, url);
                                })));

        dispatcher.register(setSkin);
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, String skinUrl) {
        String url = skinUrl;
        List<? extends String> whitelist = SkinConfig.SKIN_SERVER_WHITELIST.get();
        long passedWhitelist = whitelist.stream().filter(value -> url.startsWith(value)).count();
        if (Boolean.TRUE.equals(SkinConfig.ENABLE_SKIN_SERVER_WHITELIST.get()) && passedWhitelist == 0) {
            MutableComponent message = Component.translatable("setskin.notwhitelisted");
            Style redMessage = message.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.RED));
            source.sendSuccess(message.setStyle(redMessage), false);
            return -1;
        }
        targets.forEach(target -> {
            if (target == null) {
                return;
            }
            source.sendSuccess(Component.translatable("setskin.setplayerskin", target.getScoreboardName(), url), false);
            CustomSkinManager.setSkin(target, url);
        });
        if (targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
