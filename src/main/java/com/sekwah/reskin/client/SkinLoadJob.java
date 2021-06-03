package com.sekwah.reskin.client;

import java.util.UUID;

public class SkinLoadJob {

    public final UUID uuid;
    public final String url;
    public final String bodyType;
    public final boolean isTransparent;

    public SkinLoadJob(UUID uuid, String url, String bodyType, boolean isTransparent) {
        this.uuid = uuid;
        this.url = url;
        this.bodyType = bodyType;
        this.isTransparent = isTransparent;
    }

}
