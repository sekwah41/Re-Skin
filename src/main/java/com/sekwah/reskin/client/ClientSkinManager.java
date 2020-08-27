package com.sekwah.reskin.client;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientSkinManager {

    private static TextureManager textureManager;

    private static ResourceLocation missing = new ResourceLocation("textures/entity/steve.png");

    private static Map<UUID, ResourceLocation> playerSkinMap = Maps.newHashMap();

    private static Map<UUID, ResourceLocation> originalSkinMap = Maps.newHashMap();

    /**
     * URL map
     */
    private static Map<String, ResourceLocation> cachedUrls = Maps.newHashMap();

    private static List<SkinLoadJob> texturesToLoad = new ArrayList<>();

    public static void setSkin(UUID uuid, String url, boolean isTransparent) {

    }
}
