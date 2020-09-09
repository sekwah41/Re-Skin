package com.sekwah.reskin.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class SkinConfig {

    //public static final String CATEGORY_SERVER = "server";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ALLOW_TRANSPARENT_SKIN;

    public static ForgeConfigSpec.BooleanValue SELF_SKIN_NEEDS_OP;

    public static ForgeConfigSpec.BooleanValue OTHERS_SELF_SKIN_NEEDS_OP;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        //SERVER_BUILDER.comment("Server side variable allowing transparent skins to be set.").push(CATEGORY_SERVER);

        ALLOW_TRANSPARENT_SKIN = SERVER_BUILDER.comment("Server side variable allowing transparent skins to be set")
                .define("allowTransparentSkin ", false);

        SELF_SKIN_NEEDS_OP = SERVER_BUILDER.comment("Does setting their own skins need op?")
                .define("setSelfSkinsNeedsOp ", false);

        OTHERS_SELF_SKIN_NEEDS_OP = SERVER_BUILDER.comment("Does setting other peoples skins need op?")
                .define("setOtherSkinsNeedsOp ", true);

        //SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    /*@SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        // add things when needed
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        // add things when needed
    }*/
}
