package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.config.SkinConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Collections;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class SetModelCommand {

    private static SuggestionProvider<CommandSource> MODEL_SUGGESTIONS = (ctx, builder)
            -> ISuggestionProvider.suggest(new String[]{"default", "slim"}, builder);

    private static final String MODEL_ARG = "modelType";

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSource> setModel = literal("setmodel")
                .requires((sender) -> (!SkinConfig.SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                .then(argument(MODEL_ARG, StringArgumentType.word())
                        .suggests(MODEL_SUGGESTIONS)
                        .executes(ctx -> {
                            ServerPlayerEntity entity = ctx.getSource().getPlayerOrException();
                            String modelType = StringArgumentType.getString(ctx, MODEL_ARG);
                            return execute(ctx.getSource(), Collections.singletonList(entity), modelType);
                        })
                        .requires((sender) -> (!SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                        .then(argument("targets", EntityArgument.players())
                                .executes(ctx -> {
                                    Collection<ServerPlayerEntity> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                                    String modelType = StringArgumentType.getString(ctx, MODEL_ARG);
                                    return execute(ctx.getSource(), targetPlayers, modelType);
                                })));

        dispatcher.register(setModel);
    }

    private static int execute(CommandSource source, Collection<ServerPlayerEntity> targets, String modelType) {
        targets.forEach(target -> {
            if (target == null) {
                return;
            }
            source.sendSuccess(new TranslationTextComponent("setskin.setplayermodel", target.getDisplayName(), modelType), false);
            CustomSkinManager.setModel(target, modelType);
        });
        if (targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
