package com.sekwah.reskin.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sekwah.reskin.commands.arguments.URLArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;

public class SkinCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
//        SetSkinCommand.register(dispatcher);
//        SetModelCommand.register(dispatcher);
//        ClearSkinCacheCommand.register(dispatcher);
//        ClearSkinCommand.register(dispatcher);
    }

    public static void registerNewArgTypes() {
        ArgumentTypes.register("reskin:url_argument", URLArgument.class, new EmptyArgumentSerializer<>(URLArgument::urlArg));
    }

}
