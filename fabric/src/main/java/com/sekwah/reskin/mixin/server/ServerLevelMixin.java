package com.sekwah.reskin.mixin.server;

import com.sekwah.reskin.CustomSkinManager;
import com.sekwah.reskin.components.ISkinData;
import com.sekwah.reskin.components.SkinComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(method="addPlayer", at = @At(value = "INVOKE"))
    public void remove(ServerPlayer player, CallbackInfo ci) {
        ISkinData skinData = SkinComponent.SKIN_DATA.get(player);
        CustomSkinManager.setSkin(player,
                skinData.getSkin());
        CustomSkinManager.sendAllToPlayer(player, true);
    }

}
