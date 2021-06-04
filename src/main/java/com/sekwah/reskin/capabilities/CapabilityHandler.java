package com.sekwah.reskin.capabilities;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {

    public static void register() {
        CapabilityManager.INSTANCE.register(ISkinData.class, new SkinLocationStorage(), SkinData::new);
    }

}
