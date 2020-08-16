package com.sekwah.reskin.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkinLocationProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ISkinLocation.class)
    public static final Capability<ISkinLocation> SKIN_LOC = null;

    private LazyOptional<ISkinLocation> instance = LazyOptional.of(SKIN_LOC::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SKIN_LOC ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return SKIN_LOC.getStorage().writeNBT(SKIN_LOC, this.instance.orElse(null), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        SKIN_LOC.getStorage().readNBT(SKIN_LOC, this.instance.orElse(null), null, nbt);
    }
}
