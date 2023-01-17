package com.sekwah.reskin.capabilities;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISkinData extends INBTSerializable<Tag> {
    String getSkinUrl();
    void setSkin(String url);
    String getModelType();
    void setModelType(String url);
    boolean isTransparent();
}
