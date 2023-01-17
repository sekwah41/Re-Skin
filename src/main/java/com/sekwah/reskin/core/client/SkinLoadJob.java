package com.sekwah.reskin.core.client;

import java.util.UUID;

public class SkinLoadJob {

    public final String url;
    public final boolean isTransparent;

    public SkinLoadJob(String url, boolean isTransparent) {
        this.url = url;
        this.isTransparent = isTransparent;
    }

}
