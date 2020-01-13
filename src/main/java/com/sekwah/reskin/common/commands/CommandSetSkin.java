package com.sekwah.reskin.common.commands;

import com.sekwah.reskin.common.CustomSkinManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.command.TextComponentHelper;

public class CommandSetSkin extends CommandBase {
    @Override
    public String getName() {
        return "setskin";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setskin";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "setskin.missingargs"));
        }
        else if(args.length == 1){
            if(sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                CustomSkinManager.setSkin(player, args[0]);
            }
            else {
                sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "setskin.notplayer"));
            }
        }
    }
}
