package com.sekwah.reskin.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkinLocationStorage implements Capability.IStorage<ISkinLocation> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ISkinLocation> capability, ISkinLocation instance, Direction side) {
        return StringNBT.valueOf(instance.getSkin());
    }

    @Override
    public void readNBT(Capability<ISkinLocation> capability, ISkinLocation instance, Direction side, INBT nbt) {
        if(nbt instanceof StringNBT) {
            instance.setSkin(nbt.getAsString());
        }
    }
}
