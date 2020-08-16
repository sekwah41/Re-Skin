package com.sekwah.reskin.capabilities;

public class SkinLocation implements ISkinLocation {

    private String url = "";

    @Override
    public String getSkin() {
        return this.url;
    }

    @Override
    public void setSkin(String url) {
        this.url = url == null ? "" : url;
    }
}
