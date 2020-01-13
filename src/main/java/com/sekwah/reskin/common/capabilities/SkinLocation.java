package com.sekwah.reskin.common.capabilities;

import net.minecraftforge.fml.common.FMLCommonHandler;

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
