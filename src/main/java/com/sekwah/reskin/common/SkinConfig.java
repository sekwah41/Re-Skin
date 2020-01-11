package com.sekwah.reskin.common;

import com.sekwah.reskin.ReSkin;
import net.minecraftforge.common.config.Config;

@Config(modid = ReSkin.MODID, name = ReSkin.MODID)
public class SkinConfig {

    @Config.Comment("Server side variable allowing transparent skins to be set.")
    @Config.Name("Allow Transparent Skin")
    public static boolean allowTransparentSkin = false;

}
