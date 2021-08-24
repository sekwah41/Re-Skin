package com.sekwah.reskin.mixin.server;

import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.components.ISkinData;
import com.sekwah.reskin.components.SkinComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(method="addPlayer", at = @At(value = "INVOKE"))
    public void remove(int i, AbstractClientPlayer player, CallbackInfo ci) {
        ISkinData skinData = SkinComponent.SKIN_DATA.get(player);
        CustomSkinManager.setSkin(player,
                skinData.getSkin());
    }

}
