package com.sekwah.reskin;

import com.sekwah.reskin.capabilities.CapabilityHandler;
import com.sekwah.reskin.client.ClientSkinManager;
import com.sekwah.reskin.commands.SkinCommands;
import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.reskin.network.PacketHandler;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ReSkin.MOD_ID)
public class ReSkin {

    public static final String MOD_ID = "reskin";

    public static final Logger LOGGER = LogManager.getLogger("Re:Skin");

    public ReSkin() {

        ModLoadingContext loadingContext = ModLoadingContext.get();
        loadingContext.registerConfig(ModConfig.Type.COMMON, SkinConfig.SERVER_CONFIG, "re-skin.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientSkinManager.getTextureManager();
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        CapabilityHandler.register();
    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        SkinCommands.register(event.getDispatcher());
    }

}
