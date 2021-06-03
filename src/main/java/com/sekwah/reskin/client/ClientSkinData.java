package com.sekwah.reskin.client;

import net.minecraft.util.ResourceLocation;

public class ClientSkinData {

    public final ResourceLocation resourceLocation;
    public final String modelType;

    public ClientSkinData(ResourceLocation resourceLocation, String modelType) {
        this.resourceLocation = resourceLocation;
        this.modelType = modelType;
    }
}
