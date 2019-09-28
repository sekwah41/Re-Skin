package com.sekwah.reskin.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CommonProxy {
    public void init() {
        MinecraftForge.EVENT_BUS.register(new ServerEventHook());
        //CapabilityManager.INSTANCE.register(capability interface class, storage, default implementation factory);
    }

    public void postInit() {
    }
}
