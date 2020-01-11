package com.sekwah.reskin;

import com.sekwah.reskin.common.CommonProxy;
import com.sekwah.reskin.common.commands.CommandSetSkin;
import com.sekwah.reskin.network.client.ClientChangeSkinPacket;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ReSkin.MODID, name = "Re:Skin", version = ReSkin.VERSION)
public class ReSkin {

    public static final String MODID = "reskin";

    public static final Logger logger = LogManager.getLogger("Re:Skin");

    public static final String VERSION = "1.1.0";

    public static SimpleNetworkWrapper packetNetwork;

    @SidedProxy(clientSide = "com.sekwah.reskin.client.ClientProxy", serverSide = "com.sekwah.reskin.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.packetNetwork();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetSkin());
    }

    private void packetNetwork() {
        packetNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("RSK");
        packetNetwork.registerMessage(ClientChangeSkinPacket.Handler.class, ClientChangeSkinPacket.class, 0, Side.CLIENT);
    }

}
