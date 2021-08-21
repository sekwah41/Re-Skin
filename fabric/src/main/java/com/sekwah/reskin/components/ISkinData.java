package com.sekwah.reskin.components;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ISkinData extends Component {

    static <T> ISkinData get(T provider) {
        return SkinComponent.SKIN_DATA.get(provider);
    }

    String getSkin();
    void setSkin(String url);
    String getModelType();
    void setModelType(String url);
}
