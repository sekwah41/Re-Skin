package com.sekwah.reskin.common.commands;

import com.sekwah.reskin.common.CustomSkinManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.command.TextComponentHelper;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

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
        else if(args.length == 1) {
            EntityPlayer player = (EntityPlayer) sender;
            CustomSkinManager.setSkin(player.getUniqueID(), args[0]);
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "setskin.setplayer", player.getDisplayName(), args[0]));
        }
        else if(args.length == 2) {
            EntityPlayer targetPlayer = getPlayer(server, sender, args[0]);
            CustomSkinManager.setSkin(targetPlayer.getUniqueID(), args[1]);
            sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "setskin.setplayer", targetPlayer.getDisplayName(), args[1]));
        }
    }

    public List<String> getTabCompletions(MinecraftServer p_getTabCompletions_1_, ICommandSender p_getTabCompletions_2_, String[] p_getTabCompletions_3_, @Nullable BlockPos p_getTabCompletions_4_) {
        if (p_getTabCompletions_3_.length == 1) {
            return getListOfStringsMatchingLastWord(p_getTabCompletions_3_, p_getTabCompletions_1_.getOnlinePlayerNames());
        }
        return null;
    }
}
