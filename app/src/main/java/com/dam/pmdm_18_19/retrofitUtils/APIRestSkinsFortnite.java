package com.dam.pmdm_18_19.retrofitUtils;

import com.dam.pmdm_18_19.model.Skin;

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

    //
    @GET("skins/")
    Call<ArrayList<Skin>> obtenerSkin(@Query("rarity") String pRarity);

}