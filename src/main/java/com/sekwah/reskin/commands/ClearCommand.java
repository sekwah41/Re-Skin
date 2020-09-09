package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.reskin.network.PacketHandler;
import com.sekwah.reskin.network.client.ClientChangeSkin;
import com.sekwah.reskin.network.client.ClientClearSkinCache;
import com.sekwah.reskin.network.server.ServerRequestSkins;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import static net.minecraft.command.Commands.literal;

public class ClearCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSource> clear = literal("clearskin").executes(ClearCommand::run);

        dispatcher.register(clear);
    }

    private static int run(CommandContext<CommandSource> ctx) {
        try {
            PacketHandler.sendToPlayer(new ClientClearSkinCache(), ctx.getSource().asPlayer());
            CustomSkinManager.sendAllToPlayer(ctx.getSource().asPlayer(), false);
        } catch (CommandSyntaxException e) {
            ReSkin.LOGGER.info("This command can only be run from the console");
        }
        ctx.getSource().sendFeedback(new TranslationTextComponent("setskin.clearedcache"), false);
        return Command.SINGLE_SUCCESS;
    }
}
