package com.sekwah.reskin.server;

import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.capabilities.SkinLocationProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerEventHook {

    public static final ResourceLocation SKIN_LOCATION = new ResourceLocation(ReSkin.MOD_ID, "skin");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(SKIN_LOCATION, new SkinLocationProvider());
        }
    }

    /*@SubscribeEvent
    public void entityConstruction(EntityEvent.EntityConstructing event) {
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

        }
    }*/

}
