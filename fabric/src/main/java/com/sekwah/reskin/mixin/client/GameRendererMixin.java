package com.sekwah.reskin.mixin.client;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(float f, long l, boolean bl, CallbackInfo ci) {
        ClientSkinManager.loadQueuedSkins();
        LocalPlayer client = Minecraft.getInstance().player;
        if(client != null) {
            ClientSkinManager.checkSkin(client);
        }
    }

}
