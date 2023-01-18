package com.sekwah.reskin.client;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sekwah.reskin.ReSkin;
import com.sekwah.reskin.capabilities.SkinCapabilityHandler;
import com.sekwah.reskin.core.client.ClientSkinData;
import com.sekwah.reskin.core.client.HDDownloadingTexture;
import com.sekwah.reskin.core.client.SkinLoadJob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class ClientSkinManager {

    private static TextureManager textureManager;

    private static ClientSkinData missing = new ClientSkinData(new ResourceLocation("textures/entity/steve.png"), "default");

    public static final Map<UUID, ClientSkinData> originalSkinMap = Maps.newHashMap();

    private static Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    public static void getTextureManager() {
        textureManager = Minecraft.getInstance().getTextureManager();
    }

    public static void clearSkinCache() {
        // Release all cached resources and clear the cache list
        for(ResourceLocation resource : cachedUrls.values()) {
            textureManager.release(resource);
        }
        cachedUrls.clear();
    }

    public static void cleanupSkinData() {
        originalSkinMap.clear();
    }

    public static void loadSkin(SkinLoadJob loadJob) {
        ResourceLocation resourceLocation = new ResourceLocation("reskin", "skins/" + loadJob.url.hashCode());
        ReSkin.LOGGER.info("Downloading skin from: {}", loadJob.url);

        HDDownloadingTexture downloadingTexture = new HDDownloadingTexture(null, loadJob.url, missing.resourceLocation, loadJob.isTransparent, null);
        if(downloadingTexture != null) {
            textureManager.register(resourceLocation, downloadingTexture);
        }
        else {
            resourceLocation = missing.resourceLocation;
        }

        cachedUrls.put(loadJob.url, resourceLocation);
    }

    public static void checkSkin(AbstractClientPlayer player) {

        player.getCapability(SkinCapabilityHandler.SKIN_DATA).ifPresent(skin -> {
            if(!cachedUrls.containsKey(skin.getSkinUrl()) && !skin.getSkinUrl().equals("reset")) {
                loadSkin(new SkinLoadJob(skin.getSkinUrl(), skin.isTransparent()));
            }
            if(player.playerInfo == null) return;

            ResourceLocation currentSkin = player.playerInfo.textureLocations.get(MinecraftProfileTexture.Type.SKIN);

            if(!skin.getSkinUrl().equals("")) {
                if(skin.getSkinUrl().equals("reset")) {
                    var originalSkin = originalSkinMap.get(player.getUUID());
                    if(originalSkin != null) {
                        if( currentSkin != originalSkin.resourceLocation) {
                            player.playerInfo.textureLocations.put(MinecraftProfileTexture.Type.SKIN, originalSkin.resourceLocation);
                            player.playerInfo.skinModel = originalSkin.modelType;
                        }
                    } else if(currentSkin != null){
                        player.playerInfo.textureLocations.put(MinecraftProfileTexture.Type.SKIN, null);
                        player.playerInfo.skinModel = null;
                    }
                } else {
                    if(currentSkin != cachedUrls.get(skin.getSkinUrl())) {
                        player.playerInfo.textureLocations.put(MinecraftProfileTexture.Type.SKIN, cachedUrls.get(skin.getSkinUrl()));
                    }
                    player.playerInfo.skinModel = skin.getModelType();
                }
            }

        });
    }
}
