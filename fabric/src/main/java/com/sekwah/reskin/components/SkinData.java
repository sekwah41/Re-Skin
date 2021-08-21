package com.sekwah.reskin.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SkinData implements ISkinData, Component, PlayerComponent<SkinData> {

    private String url = "";
    private String bodyType = "default";

    private static final String SKIN_TAG = "skin";
    private static final String BODY_TYPE_TAG = "bodyType";

    protected Player owner;

    public SkinData(Player player) {
        this.owner = player;
    }

    @Override
    public String getSkin() {
        return this.url;
    }

    @Override
    public void setSkin(String url) {
        this.url = url == null ? "" : url;
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
    public void readFromNbt(CompoundTag compoundTag) {
        this.setSkin(compoundTag.getString(SKIN_TAG));
        this.setModelType(compoundTag.getString(BODY_TYPE_TAG));
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString(SKIN_TAG, this.getSkin());
        tag.putString(BODY_TYPE_TAG, this.getModelType());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public void copyFrom(SkinData other) {

    }
}
