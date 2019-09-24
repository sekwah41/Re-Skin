package com.sekwah.reskin.client;

import java.util.UUID;

public class SkinLoadJob {

    public final UUID uuid;
    public final String url;

    public SkinLoadJob(UUID uuid, String url) {
        this.uuid = uuid;
        this.url = url;
    }

}
