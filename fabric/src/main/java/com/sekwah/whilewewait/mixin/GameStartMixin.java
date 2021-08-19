package com.sekwah.whilewewait.mixin;

import com.sekwah.whilewewait.audio.AudioManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class GameStartMixin {

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void startStopMusic(Screen screen, CallbackInfo ci) {
        if(screen instanceof LevelLoadingScreen || screen instanceof DownloadingTerrainScreen || screen instanceof SaveLevelScreen || screen instanceof ConnectScreen) {
            AudioManager.startMusic();
        }
        else if(!(screen instanceof ProgressScreen)) {
            AudioManager.stopMusic();
        }
    }
}
