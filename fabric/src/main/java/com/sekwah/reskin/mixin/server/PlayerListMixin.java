package com.sekwah.reskin.mixin.server;

import com.sekwah.reskin.CustomSkinManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method="remove", at = @At(value = "INVOKE"))
    public void remove(ServerPlayer serverPlayer, CallbackInfo ci) {
        CustomSkinManager.playerLoggedOut(serverPlayer.getUUID());
    }

}
