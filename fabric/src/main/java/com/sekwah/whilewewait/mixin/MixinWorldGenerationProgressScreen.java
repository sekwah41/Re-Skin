package com.sekwah.whilewewait.mixin;

import com.sekwah.whilewewait.audio.AudioManager;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class MixinWorldGenerationProgressScreen {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void startMusic(WorldGenerationProgressTracker worldGenerationProgressTracker_1, CallbackInfo ci) {
        AudioManager.startMusic();
    }

    @Inject(method = "drawChunkMap", at = @At("HEAD"), cancellable = true)
    private static void drawChunkMap(net.minecraft.client.util.math.MatrixStack matrixStack, WorldGenerationProgressTracker worldGenerationProgressTracker_1, int int_1, int int_2, int int_3, int int_4, CallbackInfo ci) {
        ci.cancel(); // cancel drawing that weird rectangle
    }
}
