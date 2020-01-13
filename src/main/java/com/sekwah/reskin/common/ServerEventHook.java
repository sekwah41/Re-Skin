package com.sekwah.reskin.common;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.common.capabilities.SkinLocationProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHook {

    public static final ResourceLocation SKIN_LOCATION = new ResourceLocation(ReSkin.MODID, "skin");

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getEntityPlayer().getCapability(SkinLocationProvider.SKIN_LOC, null).setSkin(
                    event.getOriginal().getCapability(SkinLocationProvider.SKIN_LOC, null).getSkin());
        }
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(SKIN_LOCATION, new SkinLocationProvider());
        }
    }

    @SubscribeEvent
    public void playerJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            CustomSkinManager.setSkin((EntityPlayer) event.getEntity(),
                    event.getEntity().getCapability(SkinLocationProvider.SKIN_LOC, null).getSkin());
            if(event.getEntity() instanceof EntityPlayerMP) {
                CustomSkinManager.sendAllToPlayer((EntityPlayerMP) event.getEntity(), true);
            }
        }
    }

    @SubscribeEvent
    public void playerLeave(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        CustomSkinManager.playerLoggedOut(event.player.getUniqueID());
    }

}
