package com.sekwah.reskin.client;

import com.sekwah.reskin.ReSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ReSkin.MOD_ID)
public class ClientEventHook {

    @SubscribeEvent
    public static void doRender(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ClientSkinManager.loadQueuedSkins();
        }
        ClientPlayerEntity client = Minecraft.getInstance().player;
        if(client != null) {
            ClientSkinManager.checkSkin(client);
        }
    }

    @SubscribeEvent
    public static void leaveWorldEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        ClientSkinManager.clearSkinCache();
        ClientPlayerEntity client = event.getPlayer();
        if(client instanceof AbstractClientPlayerEntity) {
            ClientSkinManager.checkSkin(client);
        }
        ClientSkinManager.cleanupSkinData();
    }

    @SubscribeEvent
    public static void renderPlayer(RenderPlayerEvent.Pre event) {
        ClientPlayerEntity client = Minecraft.getInstance().player;
        if(event.getPlayer() instanceof AbstractClientPlayerEntity && event.getPlayer() != client) {
            ClientSkinManager.checkSkin((AbstractClientPlayerEntity) event.getPlayer());
        }
    }

}
