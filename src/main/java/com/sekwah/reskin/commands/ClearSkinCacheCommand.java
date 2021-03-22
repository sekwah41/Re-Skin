package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.network.PacketHandler;
import com.sekwah.reskin.network.client.ClientClearSkinCache;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;

import static net.minecraft.command.Commands.literal;

public class ClearSkinCacheCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSource> clear = literal("clearskincache").executes(ClearSkinCacheCommand::run);

        dispatcher.register(clear);
    }

    private static int run(CommandContext<CommandSource> ctx) {
        try {
            PacketHandler.sendToPlayer(new ClientClearSkinCache(), ctx.getSource().getPlayerOrException());
            CustomSkinManager.sendAllToPlayer(ctx.getSource().getPlayerOrException(), false);
        } catch (CommandSyntaxException e) {
            ReSkin.LOGGER.info("This command can only be run from the console");
        }
        ctx.getSource().sendSuccess(new TranslationTextComponent("setskin.clearedcache"), false);
        return Command.SINGLE_SUCCESS;
    }
}
