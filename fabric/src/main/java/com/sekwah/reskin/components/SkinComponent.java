package com.sekwah.reskin.components;

import com.sekwah.reskin.ReSkin;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Used to be the equivalent of forge capabilities
 */
public class SkinComponent implements EntityComponentInitializer {

    public static final ComponentKey<ISkinData> SKIN_DATA =
            ComponentRegistry.getOrCreate(new ResourceLocation(ReSkin.MOD_ID, "skindata"), ISkinData.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(Player.class, SKIN_DATA).impl(SkinData.class).end(SkinData::new);
    }
}
