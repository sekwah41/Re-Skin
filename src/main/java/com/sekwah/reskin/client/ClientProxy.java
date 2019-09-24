package com.sekwah.reskin.client;

import com.sekwah.reskin.client.commands.CommandClearSkinCache;
import com.sekwah.reskin.common.CommonProxy;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    public void init() {
        super.init();
        ClientCommandHandler.instance.registerCommand(new CommandClearSkinCache());
        MinecraftForge.EVENT_BUS.register(new ClientEventHook());
    }

    public void postInit() {
        super.init();
        ClientSkinManager.getTextureManager();
    }
}
