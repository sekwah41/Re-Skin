package com.sekwah.reskin;

import com.sekwah.reskin.capabilities.SkinCapabilityHandler;
import net.minecraft.world.entity.player.Player;

public class CustomSkinManager {

    public static void setSkin(Player target, String url) {
        if(target != null) {
            target.getCapability(SkinCapabilityHandler.SKIN_DATA, null).ifPresent(skinCap -> {
                skinCap.setSkin(url);
            });
        }
    }

    /**
     * Reset the skin of the target player.
     *
     * @param target the target player
     */
    public static void resetSkin(Player target) {
        setSkin(target, "reset");
    }

    public static void setModel(Player target, String modelType) {
        if(target != null) {
            target.getCapability(SkinCapabilityHandler.SKIN_DATA, null).ifPresent(skinCap -> {
                skinCap.setModelType(modelType);
            });
        }
    }
}
