package com.sekwah.reskin.client;

import java.util.UUID;

public class SkinLoadJob {

    public final UUID uuid;
    public final String url;
    public final boolean isTransparent;

    public SkinLoadJob(UUID uuid, String url, boolean isTransparent) {
        this.uuid = uuid;
        this.url = url;
        this.isTransparent = isTransparent;
    }

}
