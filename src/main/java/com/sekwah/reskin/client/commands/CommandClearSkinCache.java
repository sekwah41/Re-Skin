package com.sekwah.reskin.client.commands;

import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.common.CustomSkinManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.TextComponentHelper;

public class CommandClearSkinCache extends CommandBase {
    @Override
    public String getName() {
        return "clearskincache";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/clearskincache";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ClientSkinManager.clearSkinCache();
        sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "setskin.clearedcache"));
    }
}
