package com.sekwah.reskin.common;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHook {

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {

        }
    }

}
