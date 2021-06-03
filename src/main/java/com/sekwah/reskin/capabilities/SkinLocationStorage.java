package com.sekwah.reskin.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkinLocationStorage implements Capability.IStorage<ISkinData> {

    private static final String SKIN_NBT = "skin";
    private static final String BODY_TYPE_NBT = "bodyType";

    @Nullable
    @Override
    public INBT writeNBT(Capability<ISkinData> capability, ISkinData instance, Direction side) {
        final CompoundNBT nbt = new CompoundNBT();
        nbt.putString(SKIN_NBT, instance.getSkin());
        nbt.putString(BODY_TYPE_NBT, instance.getModelType());
        return nbt;
    }

    @Override
    public void readNBT(Capability<ISkinData> capability, ISkinData instance, Direction side, INBT nbt) {
        if(nbt instanceof CompoundNBT) {
            CompoundNBT compoundNBT = (CompoundNBT) nbt;
            instance.setSkin(compoundNBT.getString(SKIN_NBT));
            instance.setModelType(compoundNBT.getString(BODY_TYPE_NBT));
        } else if(nbt instanceof StringNBT) {
            instance.setSkin(nbt.getAsString());
        }
    }
}
