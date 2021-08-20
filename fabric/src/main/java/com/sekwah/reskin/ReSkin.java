package com.sekwah.reskin;

import com.sekwah.reskin.commands.SkinCommands;
import com.sekwah.reskin.config.SkinConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReSkin implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Re:Skin");

    @Override
    public void onInitialize() {
        /**
         * To get
         * SkinConfig config = AutoConfig.getConfigHolder(SkinConfig.class).getConfig();
         */
        AutoConfig.register(SkinConfig.class, GsonConfigSerializer::new);

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            SkinCommands.register(dispatcher);
        });
    }
}
