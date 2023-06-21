package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.config.SkinConfig;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ClearSkinCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSourceStack> setSkin = literal("clearskin")
                .requires((sender) -> (!SkinConfig.SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                .executes(ctx -> {
                    ServerPlayer entity = ctx.getSource().getPlayerOrException();
                    return execute(ctx.getSource(), Collections.singletonList(entity));
                })
                .then(argument("targets", EntityArgument.players())
                        .requires((sender) -> (!SkinConfig.OTHERS_SELF_SKIN_NEEDS_OP.get() || sender.hasPermission(2)))
                        .executes(ctx -> {
                            Collection<ServerPlayer> targetPlayers = EntityArgument.getPlayers(ctx, "targets");
                            return execute(ctx.getSource(), targetPlayers);
                        }));

        dispatcher.register(setSkin);
    }

    private static int execute(CommandSourceStack source, Collection<ServerPlayer> targets) {
        targets.forEach(target -> {
            if(target == null) {
                return;
            }
            source.sendSuccess(() -> Component.translatable("setskin.resetplayer", target.getDisplayName()), false);
            CustomSkinManager.resetSkin(target);
        });
        if(targets.size() == 0) {
            return -1;
        }
        return Command.SINGLE_SUCCESS;
    }
}
