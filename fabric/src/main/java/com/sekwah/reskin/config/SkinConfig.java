package com.sekwah.reskin.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "reskin")
public class SkinConfig implements ConfigData {

    public boolean allowTransparentSkin = false;
    public boolean selfSkinNeedsOp = false;
    public boolean othersSkinNeedsOp = true;
    public boolean enableSkinServerWhitelist = true;

    // Need to add something here
    public String[] skinServerWhitelist = {"https://i.imgur.com/"};

//    static {
//        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
//
//        //SERVER_BUILDER.comment("Server side variable allowing transparent skins to be set.").push(CATEGORY_SERVER);
//
//        ALLOW_TRANSPARENT_SKIN = SERVER_BUILDER.comment("Server side variable allowing transparent skins to be set")
//                .define("allowTransparentSkin", false);
//
//        SELF_SKIN_NEEDS_OP = SERVER_BUILDER.comment("Does setting their own skins need op?")
//                .define("setSelfSkinsNeedsOp", false);
//
//        OTHERS_SELF_SKIN_NEEDS_OP = SERVER_BUILDER.comment("Does setting other peoples skins need op?")
//                .define("setOtherSkinsNeedsOp", true);
//
//        ENABLE_SKIN_SERVER_WHITELIST = SERVER_BUILDER.comment("Server skin whitelist")
//                .define("enableSkinServerWhitelist", true);
//
//        SKIN_SERVER_WHITELIST = SERVER_BUILDER.comment("Server skin whitelist")
//                .defineList("enforceSkinWhitelist", Collections.singletonList("https://i.imgur.com/"), (value) -> true);
//
//        //SERVER_BUILDER.pop();
//
//        SERVER_CONFIG = SERVER_BUILDER.build();
//    }

}
