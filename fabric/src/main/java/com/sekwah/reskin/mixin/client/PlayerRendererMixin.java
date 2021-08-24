package com.sekwah.reskin.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType transparentHandRender(ResourceLocation resourceLocation) {
        return RenderType.entityTranslucent(resourceLocation);
    }

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(AbstractClientPlayer clientPlayer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        LocalPlayer client = Minecraft.getInstance().player;
        if(clientPlayer != client) {
            ClientSkinManager.checkSkin(clientPlayer);
        }
    }
}
