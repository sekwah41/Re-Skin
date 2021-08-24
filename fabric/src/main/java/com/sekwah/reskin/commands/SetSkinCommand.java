package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.commands.arguments.URLArgument;
import com.sekwah.reskin.config.SkinConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetSkinCommand {

    private static final String URL_ARG = "url";

    private static SuggestionProvider<CommandSourceStack> URL_SUGGESTIONS = (ctx, builder)
            -> SharedSuggestionProvider.suggest(new String[]{"https://", "https://i.imgur.com/mORJxcm.png"}, builder);

    private static SkinConfig config = AutoConfig.getConfigHolder(SkinConfig.class).getConfig();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSourceStack> setSkin = literal("setskin")
                .requires((sender) -> (!config.selfSkinNeedsOp || sender.hasPermission(2)))
                .then(argument(URL_ARG, URLArgument.urlArg())
                        .suggests(URL_SUGGESTIONS)
                        .executes(ctx -> {
                            ServerPlayer entity = ctx.getSource().getPlayerOrException();
                            String url = URLArgument.getURL(ctx, URL_ARG);
                            return execute(ctx.getSource(), Collections.singletonList(entity), url);
                        })
                        .requires(sender -> (!config.othersSkinNeedsOp || sender.hasPermission(2)))
                        .then(argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    String url = URLArgument.getURL(ctx, URL_ARG);
                                    Collection<ServerPlayer> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                                    return execute(ctx.getSource(), targetPlayers, url);
                                })));

        dispatcher.register(setSkin);
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, String skinUrl) {
        String url = skinUrl;
        List<? extends String> whitelist = Arrays.asList(config.skinServerWhitelist.split(";"));
        long passedWhitelist = whitelist.stream().filter(value -> url.startsWith(value)).count();
        if (Boolean.TRUE.equals(config.enableSkinServerWhitelist) && passedWhitelist == 0) {
            TranslatableComponent message = new TranslatableComponent("setskin.notwhitelisted");
            Style redMessage = message.getStyle().withColor(TextColor.fromLegacyFormat(ChatFormatting.RED));
            source.sendSuccess(message.setStyle(redMessage), false);
            return -1;
        }
        targets.forEach(target -> {
            if (target == null) {
                return;
            }
            source.sendSuccess(new TranslatableComponent("setskin.setplayerskin", target.getDisplayName(), url), false);
            CustomSkinManager.setSkin(target, url);
        });
        if (targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
