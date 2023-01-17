package com.sekwah.reskin.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public class SkinCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        SetSkinCommand.register(dispatcher);
        SetModelCommand.register(dispatcher);
        ClearSkinCacheCommand.register(dispatcher);
        ClearSkinCommand.register(dispatcher);
    }

}
