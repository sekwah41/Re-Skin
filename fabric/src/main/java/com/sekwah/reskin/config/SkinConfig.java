package com.sekwah.reskin.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "reskin")
public class SkinConfig implements ConfigData {

    public boolean allowTransparentSkin = false;
    public boolean selfSkinNeedsOp = false;
    public boolean othersSkinNeedsOp = true;
    public boolean enableSkinServerWhitelist = true;
    public String skinServerWhitelist = "https://i.imgur.com/";

}
