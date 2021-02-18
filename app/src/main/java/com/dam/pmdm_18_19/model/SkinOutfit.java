package com.dam.pmdm_18_19.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkinOutfit {

    @SerializedName("skins")
    @Expose
    private List<Skin> skins = null;
    @SerializedName("skinsDetalles")
    @Expose
    private List<SkinsDetalle> skinsDetalles = null;

    public List<Skin> getSkins() {
        return skins;
    }

    public void setSkins(List<Skin> skins) {
        this.skins = skins;
    }

    public List<SkinsDetalle> getSkinsDetalles() {
        return skinsDetalles;
    }

    public void setSkinsDetalles(List<SkinsDetalle> skinsDetalles) {
        this.skinsDetalles = skinsDetalles;
    }
}
