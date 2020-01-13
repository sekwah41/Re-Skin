package com.sekwah.reskin.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class SkinLocationProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ISkinLocation.class)
    public static final Capability<ISkinLocation> SKIN_LOC = null;


    private ISkinLocation instance = SKIN_LOC.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SKIN_LOC;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SKIN_LOC ? SKIN_LOC.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SKIN_LOC.getStorage().writeNBT(SKIN_LOC, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SKIN_LOC.getStorage().readNBT(SKIN_LOC, this.instance, null, nbt);
    }
}
