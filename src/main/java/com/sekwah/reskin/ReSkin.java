package com.sekwah.reskin;

import com.sekwah.reskin.capabilities.CapabilityHandler;
import com.sekwah.reskin.commands.SkinCommands;
import com.sekwah.reskin.network.MessageHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ReSkin.MOD_ID)
public class ReSkin {

    public static final String MOD_ID = "reskin";

    public static final Logger LOGGER = LogManager.getLogger("Re:Skin");

    public ReSkin() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::onModConfigEvent);

        // The @Mod.EventSubscriber doesn't seem to work also this is a little cleaner as you can comment it out
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Nothing implemented yet.
    }

    private void setup(final FMLCommonSetupEvent event) {
        MessageHandler.init();
        CapabilityHandler.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        SkinCommands.register(event.getCommandDispatcher());
    }

    private void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        // Configs not implemented yet
    }


}
