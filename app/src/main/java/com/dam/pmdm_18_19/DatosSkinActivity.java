package com.dam.pmdm_18_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DatosSkinActivity extends AppCompatActivity {

    TextView tvNombreD;
    TextView tvDescD;
    TextView tvRarezaD;
    TextView tvCosteD;
    TextView tvMediaP;
    TextView tvTotalP;
    TextView tvCalidad;

    ImageView imSkin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_skin);

        tvNombreD = findViewById(R.id.tvNombreD);
        tvDescD = findViewById(R.id.tvDescripD);
        tvRarezaD = findViewById(R.id.tvRarezaD);
        tvCosteD = findViewById(R.id.tvCosteD);
        tvMediaP = findViewById(R.id.tvMediaP);
        tvTotalP = findViewById(R.id.tvTotalPuntos);
        tvCalidad = findViewById(R.id.tvCalidadVotos);

        imSkin = findViewById(R.id.imgSkin);
        

    }




}