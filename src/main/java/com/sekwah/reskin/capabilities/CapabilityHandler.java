package com.sekwah.reskin.capabilities;

import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {

    public static void init() {
        CapabilityManager.INSTANCE.register(ISkinLocation.class, new SkinLocationStorage(), SkinLocation::new);
    }

}
