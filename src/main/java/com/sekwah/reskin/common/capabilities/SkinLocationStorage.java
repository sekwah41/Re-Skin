package com.sekwah.reskin.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkinLocationStorage implements Capability.IStorage<ISkinLocation> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISkinLocation> capability, ISkinLocation instance, EnumFacing side) {
        return new NBTTagString(instance.getSkin());
    }

    @Override
    public void readNBT(Capability<ISkinLocation> capability, ISkinLocation instance, EnumFacing side, NBTBase nbt) {
        if(nbt instanceof NBTTagString) {
            instance.setSkin(((NBTTagString) nbt).getString());
        }
    }
}
