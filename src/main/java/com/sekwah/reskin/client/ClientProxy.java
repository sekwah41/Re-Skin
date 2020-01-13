package com.sekwah.reskin.client;

import com.sekwah.reskin.client.commands.CommandClearSkinCache;
import com.sekwah.reskin.common.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.UUID;

public class ClientProxy extends CommonProxy {

    public void init() {
        super.init();
        ClientCommandHandler.instance.registerCommand(new CommandClearSkinCache());
        MinecraftForge.EVENT_BUS.register(new ClientEventHook());
    }

    public boolean isServer() {
        return false;
    }

    public void postInit() {
        super.postInit();
        ClientSkinManager.getTextureManager();
    }

    public EntityPlayer getFromUUID(UUID uuid) {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }
}
