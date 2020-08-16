package com.sekwah.reskin;

import com.sekwah.reskin.capabilities.SkinLocationProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CustomSkinManager {

    public static void setSkin(ServerPlayerEntity target, String url) {
        if(target != null) {
            target.getCapability(SkinLocationProvider.SKIN_LOC, null).ifPresent(skinCap -> skinCap.setSkin(url));
        }
    }
}
