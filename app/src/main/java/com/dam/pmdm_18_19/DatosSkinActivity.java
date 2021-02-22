package com.dam.pmdm_18_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.pmdm_18_19.model.Ratings;
import com.dam.pmdm_18_19.model.Skin;
import com.dam.pmdm_18_19.model.SkinsDetalle;

import java.util.ArrayList;


public class DatosSkinActivity extends AppCompatActivity {


    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_skin);



        id = getIntent().getStringExtra(MainActivity.CLAVE_DATOS);

        Toast.makeText(this, id, Toast.LENGTH_LONG).show();



    }




}