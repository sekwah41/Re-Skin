package com.sekwah.reskin.network;

import com.sekwah.reskin.ReSkin;
import net.minecraft.resources.ResourceLocation;

public class NetworkConst {
    public static final ResourceLocation CLIENT_SKIN_CHANGE_PACKET = new ResourceLocation(ReSkin.MOD_ID, "client_skin_change");
    public static final ResourceLocation CLIENT_CLEAR_SKIN_CACHE_PACKET = new ResourceLocation(ReSkin.MOD_ID, "client_clear_skin_cache");
}
