package com.sekwah.reskin.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sekwah.reskin.commands.arguments.URLArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;

public class SkinCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        SetSkinCommand.register(dispatcher);
        SetModelCommand.register(dispatcher);
        ClearSkinCacheCommand.register(dispatcher);
        ClearSkinCommand.register(dispatcher);
    }

    public static void registerNewArgTypes() {
        ArgumentTypes.register("reskin:url_argument", URLArgument.class, new ArgumentSerializer<>(URLArgument::urlArg));
    }

}
