package com.sekwah.reskin;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ReSkin.modid, name = "Re:Skin", version = ReSkin.version)
public class ReSkin {

    public static final String modid = "reskin";
    public static final Logger logger = LogManager.getLogger("SekC Physics");

    public static final String version = "0.1.0";

    public static SimpleNetworkWrapper packetNetwork;

    private void packetNetwork() {
        packetNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("RSK");
    }

}
