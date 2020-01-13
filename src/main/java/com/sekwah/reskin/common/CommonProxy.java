package com.sekwah.reskin.common;

import com.sekwah.reskin.common.capabilities.SkinLocation;
import com.sekwah.reskin.common.capabilities.SkinLocationStorage;
import com.sekwah.reskin.common.capabilities.ISkinLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.UUID;

public class CommonProxy {
    public void init() {
        MinecraftForge.EVENT_BUS.register(new ServerEventHook());
        CapabilityManager.INSTANCE.register(ISkinLocation.class, new SkinLocationStorage(), SkinLocation::new);
    }

    public boolean isServer() {
        return true;
    }

    public void postInit() {
    }

    public EntityPlayer getFromUUID(UUID uuid) {
        return FMLServerHandler.instance().getServer().getPlayerList().getPlayerByUUID(uuid);
    }
}
