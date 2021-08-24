package com.sekwah.reskin.mixin.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(PlayerInfo.class)
public interface PlayerInfoAccessor {

    @Accessor("textureLocations")
    Map<MinecraftProfileTexture.Type, ResourceLocation> getTextureLocations();

    @Accessor("skinModel")
    String getSkinModel();

    @Accessor("skinModel")
    void setSkinModel(String skinModel);

}
