package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.config.SkinConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetModelCommand {

    private static SuggestionProvider<CommandSourceStack> MODEL_SUGGESTIONS = (ctx, builder)
            -> SharedSuggestionProvider.suggest(new String[]{"default", "slim"}, builder);

    private static final String MODEL_ARG = "modelType";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSourceStack> setModel = literal("setmodel")
                .requires((sender) -> (!SkinConfig.SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                .then(argument(MODEL_ARG, StringArgumentType.word())
                        .suggests(MODEL_SUGGESTIONS)
                        .executes(ctx -> {
                            ServerPlayer entity = ctx.getSource().getPlayerOrException();
                            String modelType = StringArgumentType.getString(ctx, MODEL_ARG);
                            return execute(ctx.getSource(), Collections.singletonList(entity), modelType);
                        })
                        .requires((sender) -> (!SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                        .then(argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    Collection<ServerPlayer> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                                    String modelType = StringArgumentType.getString(ctx, MODEL_ARG);
                                    return execute(ctx.getSource(), targetPlayers, modelType);
                                })));

        dispatcher.register(setModel);
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets, String modelType) {
        targets.forEach(target -> {
            if (target == null) {
                return;
            }
            source.sendSuccess(new TranslatableComponent("setskin.setplayermodel", target.getDisplayName(), modelType), false);
            CustomSkinManager.setModel(target, modelType);
        });
        if (targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
