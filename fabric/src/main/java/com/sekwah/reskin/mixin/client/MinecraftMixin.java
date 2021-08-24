package com.sekwah.reskin.mixin.client;

import com.sekwah.reskin.client.ClientSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public LocalPlayer player;

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "RETURN"))
    public void render(Screen screen, CallbackInfo ci) {
        ClientSkinManager.clearSkinCache();
        LocalPlayer client = this.player;
        if(client != null) {
            ClientSkinManager.checkSkin(client);
        }
        ClientSkinManager.cleanupSkinData();
    }

    @Inject(method="<init>" , at = @At(value = "RETURN"))
    public void init(GameConfig gameConfig, CallbackInfo ci) {
        ClientSkinManager.getTextureManager();
    }

}
