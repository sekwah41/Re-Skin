package com.sekwah.reskin.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SkinCapabilityHandler {

    @CapabilityInject(ISkinData.class)
    public static final Capability<ISkinData> SKIN_LOC = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ISkinData.class);
    }

}
