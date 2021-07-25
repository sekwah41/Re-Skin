package com.sekwah.reskin.server;

import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.capabilities.SkinCapabilityHandler;
import com.sekwah.reskin.capabilities.SkinData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ReSkin.MOD_ID)
public class ServerEventHook {

    public static final ResourceLocation SKIN_LOCATION = new ResourceLocation(ReSkin.MOD_ID, "skin");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SKIN_LOCATION, new SkinData());
        }
    }

    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof Player player) {
            event.getEntity().getCapability(SkinCapabilityHandler.SKIN_LOC, null).ifPresent(skin -> {
                CustomSkinManager.setSkin(player,
                        skin.getSkin());
                if(event.getEntity() instanceof ServerPlayer serverPlayer) {
                    CustomSkinManager.sendAllToPlayer(serverPlayer, true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        CustomSkinManager.playerLoggedOut(event.getPlayer().getUUID());
    }

}
