package com.sekwah.reskin.client;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sekwah.reskin.ReSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * This handles serving the skins for rendering as well as downloading the skins
 */
public  class ClientSkinManager {

    private static TextureManager textureManager;

    private static ResourceLocation missing = new ResourceLocation("textures/entity/steve.png");

    private static Map<UUID, ResourceLocation> playerSkinMap = Maps.newHashMap();

    private static Map<UUID, ResourceLocation> originalSkinMap = Maps.newHashMap();

    /**
     * URL map
     */
    private static Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    private static List<SkinLoadJob> texturesToLoad = new ArrayList<>();

    public static void clearSkinCache() {
        for(Map.Entry<String, ResourceLocation> resources : cachedUrls.entrySet()) {
            textureManager.deleteTexture(resources.getValue());
        }
        cachedUrls.clear();
        //playerSkinMap.clear();
        for(Map.Entry<UUID, ResourceLocation> entry : playerSkinMap.entrySet()) {
            ResourceLocation resourceLocation = originalSkinMap.getOrDefault(entry.getKey(), missing);
            playerSkinMap.put(entry.getKey(), resourceLocation == null ? missing : resourceLocation);
        }
    }

    public static void getTextureManager() {
        textureManager = Minecraft.getMinecraft().getTextureManager();
    }

    public static void setSkin(UUID uuid, String url, boolean isTransparent) {
        if(url.equalsIgnoreCase("reset")) {
            if(originalSkinMap.containsKey(uuid)) {
                playerSkinMap.put(uuid, originalSkinMap.get(uuid));
            }
        }
        else if(cachedUrls.containsKey(url)) {
            playerSkinMap.put(uuid, cachedUrls.get(url));
        }
        else {
            texturesToLoad.add(new SkinLoadJob(uuid, url, isTransparent));
        }
    }

    public static void loadQueuedSkins() {
        Iterator<SkinLoadJob> i = texturesToLoad.iterator();
        while (i.hasNext()) {
            SkinLoadJob loadJob = i.next();
            ResourceLocation resourcelocation = new ResourceLocation("reskin", "skins/" + loadJob.url.hashCode());
            ReSkin.logger.info("Downloading skin from: " + loadJob.url);
            /**
             * TODO enable local cache with first value (for between restarts)
             */
            HDImageBufferDownload imageBuffer = new HDImageBufferDownload(loadJob.isTransparent);
            ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(null, loadJob.url, missing, new IImageBuffer()
            {
                public BufferedImage parseUserSkin(BufferedImage image)
                {
                    if (imageBuffer != null)
                    {
                        image = imageBuffer.parseUserSkin(image);
                    }

                    return image;
                }
                public void skinAvailable()
                {
                    if (imageBuffer != null)
                    {
                        imageBuffer.skinAvailable();
                    }
                }
            });
            textureManager.loadTexture(resourcelocation, threaddownloadimagedata);
            cachedUrls.put(loadJob.url, resourcelocation);
            playerSkinMap.put(loadJob.uuid, resourcelocation);
            i.remove();
        }
    }

    public static void checkSkin(AbstractClientPlayer player) {
        if(player.playerInfo == null) return;
        ResourceLocation currentSkin = player.playerInfo.playerTextures.get(MinecraftProfileTexture.Type.SKIN);
        ResourceLocation wantedSkin = playerSkinMap.get(player.getUniqueID());
        if(wantedSkin != null && currentSkin != wantedSkin) {
            if(!originalSkinMap.containsKey(player.getUniqueID())) {
                originalSkinMap.put(player.getUniqueID(), player.playerInfo.playerTextures.get(MinecraftProfileTexture.Type.SKIN));
            }
            player.playerInfo.playerTextures.put(MinecraftProfileTexture.Type.SKIN, wantedSkin);
        }
    }
}
