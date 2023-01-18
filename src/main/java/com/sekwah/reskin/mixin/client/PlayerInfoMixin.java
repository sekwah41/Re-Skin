package com.sekwah.reskin.mixin.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.core.client.ClientSkinData;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin {

    @Shadow @Nullable public String skinModel;

    @Inject(method = "lambda$registerTextures$0", at = @At(value = "TAIL"))
    protected void registerTextures(MinecraftProfileTexture.Type type, ResourceLocation resourceLocation, MinecraftProfileTexture profileTexture, CallbackInfo ci) {
        if(type != MinecraftProfileTexture.Type.SKIN) return;

        synchronized (ClientSkinManager.originalSkinMap) {
            ClientSkinManager.originalSkinMap.put(((PlayerInfoAccessor) (Object) this).getGameProfile().getId(), new ClientSkinData(resourceLocation, this.skinModel));
        }
    }
}
