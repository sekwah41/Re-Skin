package com.sekwah.reskin.capabilities;

public class SkinData implements ISkinData {

    private String url = "";
    private String bodyType = "default";

    @Override
    public String getSkin() {
        return this.url;
    }

    @Override
    public void setSkin(String url) {
        this.url = url == null ? "" : url;
    }

    @Override
    public String getModelType() {
        return this.bodyType;
    }

    @Override
    public void setModelType(String bodyType) {
        this.bodyType = bodyType;
    }
}
