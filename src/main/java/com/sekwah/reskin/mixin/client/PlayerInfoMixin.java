package com.sekwah.reskin.mixin.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.core.client.ClientSkinData;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin {

    @Shadow
    @Nullable
    public String skinModel;

    @Redirect(method = "registerTextures", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SkinManager;registerSkins(Lcom/mojang/authlib/GameProfile;Lnet/minecraft/client/resources/SkinManager$SkinTextureCallback;Z)V"))
    public void registerSkins(SkinManager skinManager, GameProfile gameProfile, SkinManager.SkinTextureCallback callback, boolean requireSecure) {
        skinManager.registerSkins(gameProfile, (typeIn, location, profileTexture) -> {
            if (typeIn == MinecraftProfileTexture.Type.SKIN) {
                synchronized (ClientSkinManager.originalSkinMap) {
                    ClientSkinManager.originalSkinMap.put(gameProfile.getId(), new ClientSkinData(location, this.skinModel));
                }
            }
            callback.onSkinTextureAvailable(typeIn, location, profileTexture);
        }, requireSecure);
    }
}
