package com.sekwah.reskin.core.client;

import net.minecraft.resources.ResourceLocation;

public class ClientSkinData {

    public final ResourceLocation resourceLocation;
    public final String modelType;

    public ClientSkinData(ResourceLocation resourceLocation, String modelType) {
        this.resourceLocation = resourceLocation;
        this.modelType = modelType;
    }
}
