package com.sekwah.reskin.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class SkinCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        SetSkinCommand.register(dispatcher);
        ClearSkinCacheCommand.register(dispatcher);
        ResetSkinCommand.register(dispatcher);
    }

}
