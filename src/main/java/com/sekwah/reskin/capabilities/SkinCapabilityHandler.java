package com.sekwah.reskin.capabilities;

import com.sekwah.reskin.ReSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ReSkin.MOD_ID)
public class SkinCapabilityHandler {

    public static final ResourceLocation SKIN_LOCATION = new ResourceLocation(ReSkin.MOD_ID, "skin");

    public static final Capability<ISkinData> SKIN_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SKIN_LOCATION, new SkinData());
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(SKIN_DATA).ifPresent(original ->
                event.getEntity().getCapability(SKIN_DATA).ifPresent(future ->
                        future.deserializeNBT(original.serializeNBT())));
    }


}
