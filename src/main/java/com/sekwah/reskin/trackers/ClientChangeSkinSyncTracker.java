package com.sekwah.reskin.trackers;

import com.sekwah.reskin.network.s2c.ClientChangeSkin;
import com.sekwah.sekclib.capabilitysync.capabilitysync.tracker.SyncTrackerSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class ClientChangeSkinSyncTracker implements SyncTrackerSerializer<ClientChangeSkin> {
    @Override
    public void encode(ClientChangeSkin objectToSend, FriendlyByteBuf outBuffer) {
        ClientChangeSkin.encode(objectToSend, outBuffer);
    }

    @Override
    public ClientChangeSkin decode(FriendlyByteBuf inBuffer) {
        return ClientChangeSkin.decode(inBuffer);
    }
}
