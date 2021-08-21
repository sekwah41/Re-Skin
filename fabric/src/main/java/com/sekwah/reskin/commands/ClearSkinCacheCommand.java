package com.sekwah.reskin.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.sekwah.reskin.config.SkinConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;

import static net.minecraft.commands.Commands.literal;

public class ClearSkinCacheCommand {

    private static SkinConfig config = AutoConfig.getConfigHolder(SkinConfig.class).getConfig();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        // Thing to note, arguments are handled in alphabetical order.
        LiteralArgumentBuilder<CommandSourceStack> clear = literal("clearskincache").executes(ClearSkinCacheCommand::run);

        dispatcher.register(clear);
    }

    private static int run(CommandContext<CommandSourceStack> ctx) {
        // TODO uncomment
//        try {
//            PacketHandler.sendToPlayer(new ClientClearSkinCache(), ctx.getSource().getPlayerOrException());
//            CustomSkinManager.sendAllToPlayer(ctx.getSource().getPlayerOrException(), false);
//        } catch (CommandSyntaxException e) {
//            ReSkin.LOGGER.info("This command can only be run from the console");
//        }
        ctx.getSource().sendSuccess(new TranslatableComponent("setskin.clearedcache"), false);
        return Command.SINGLE_SUCCESS;
    }
}
