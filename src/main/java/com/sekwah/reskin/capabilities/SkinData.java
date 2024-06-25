package com.sekwah.reskin.capabilities;

import com.sekwah.reskin.config.SkinConfig;
import com.sekwah.sekclib.capabilitysync.capabilitysync.annotation.Sync;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Its best to use the type tag for now as if there is old data from earlier mc versions it can be a blank string
 * or other types of data. So just type cast and rip out what data you can.
 */
public class SkinData implements ISkinData, ICapabilityProvider {

    @Sync(syncGlobally = true)
    private String url = "";

    @Sync(syncGlobally = true)
    private String bodyType = "default";

    @Sync(syncGlobally = true)
    public boolean isTransparent = SkinConfig.ALLOW_TRANSPARENT_SKIN.get();

    private static final String SKIN_TAG = "skin";
    private static final String BODY_TYPE_TAG = "bodyType";

    private final LazyOptional<ISkinData> holder = LazyOptional.of(() -> this);

    @Override
    public String getSkinUrl() {
        return this.url;
    }

    @Override
    public void setSkin(String url) {
        this.url = url == null ? "" : url;
        // Updates the transparency
        this.isTransparent = SkinConfig.ALLOW_TRANSPARENT_SKIN.get();
    }

    @Override
    public String getModelType() {
        return this.bodyType;
    }

    @Override
    public void setModelType(String bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public boolean isTransparent() {
        return this.isTransparent;
    }

    @Override
    public Tag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        if(this.getSkinUrl() != null) tag.putString(SKIN_TAG, this.getSkinUrl());
        if(this.getModelType() != null) tag.putString(BODY_TYPE_TAG, this.getModelType());
        return tag;
    }

    @Override
    public void deserializeNBT(Tag tag) {
        if(tag instanceof CompoundTag compoundTag) {
            this.setSkin(compoundTag.getString(SKIN_TAG));
            this.setModelType(compoundTag.getString(BODY_TYPE_TAG));
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return SkinCapabilityHandler.SKIN_DATA.orEmpty(cap, holder);
    }
}
