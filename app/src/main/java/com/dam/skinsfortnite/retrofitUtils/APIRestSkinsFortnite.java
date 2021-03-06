package com.dam.skinsfortnite.retrofitUtils;

import com.dam.skinsfortnite.model.Skin;
import com.dam.skinsfortnite.model.SkinsDetalle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestSkinsFortnite {
    //1.
    public static final String BASE_URL = "http://10.0.2.2:3000/";

    //2.
    @GET("skins/")
    Call<ArrayList<Skin>> obtenerSkin();

    //3.Añadir método que busque por la rareza del personaje
    @GET("skins/")
    Call<ArrayList<Skin>> obtenerSkin(@Query("rarity") String pRarity);

    //4.Añadir método que busque personajes por la id pasada
    @GET("skinsDetalles/{id_skin}")
    Call<SkinsDetalle> obtenerID(@Path("id_skin") String id);

}