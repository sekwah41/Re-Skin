package com.sekwah.reskin.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface PacketEncoder {

    ResourceLocation getPacketId();

    FriendlyByteBuf encode();

}
