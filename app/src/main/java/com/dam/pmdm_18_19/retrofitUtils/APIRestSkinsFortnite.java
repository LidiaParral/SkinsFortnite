package com.dam.pmdm_18_19.retrofitUtils;

import com.dam.pmdm_18_19.model.Skin;
import com.dam.pmdm_18_19.model.SkinsDetalle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIRestSkinsFortnite {
    //8.
    public static final String BASE_URL = "http://10.0.2.2:3000/";

    //9.
    @GET("skins/")
    Call<ArrayList<Skin>> obtenerSkin();

    //Añadir método que busque por la rareza del personaje
    @GET("skins/")
    Call<ArrayList<Skin>> obtenerSkin(@Query("rarity") String pRarity);

    //Añadir método que busque personajes por la id pasada
    @GET("skinDetalles/{id}")
    Call<SkinsDetalle> obtenerID(@Query("id") String pid);

}