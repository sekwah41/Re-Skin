package com.sekwah.reskin.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerInfo.class)
public interface PlayerInfoAccessor {

    @Accessor("profile")
    GameProfile getGameProfile();
}
