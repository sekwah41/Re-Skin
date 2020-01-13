package com.sekwah.reskin.client;

import com.sekwah.reskin.ReSkin;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ClientEventHook {

    @SubscribeEvent
    public void leaveServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ReSkin.MODID)) ConfigManager.sync(ReSkin.MODID, Config.Type.INSTANCE);
    }

    @SubscribeEvent
    public void doRender(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ClientSkinManager.loadQueuedSkins();
        }
        EntityPlayer client = FMLClientHandler.instance().getClientPlayerEntity();
        if(client != null) {
            ClientSkinManager.checkSkin((AbstractClientPlayer) client);
        }
    }

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Pre event) {
        EntityPlayer client = FMLClientHandler.instance().getClientPlayerEntity();
        if(event.getEntityPlayer() instanceof AbstractClientPlayer && event.getEntityPlayer() != client) {
            ClientSkinManager.checkSkin((AbstractClientPlayer) event.getEntityPlayer());
        }
    }

}
