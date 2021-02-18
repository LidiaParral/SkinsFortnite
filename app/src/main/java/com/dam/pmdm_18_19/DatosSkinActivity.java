package com.dam.pmdm_18_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.pmdm_18_19.model.Ratings;
import com.dam.pmdm_18_19.model.SkinsDetalle;


public class DatosSkinActivity extends AppCompatActivity {

    TextView tvNombreD;
    TextView tvDescD;
    TextView tvRarezaD;
    TextView tvCosteD;
    TextView tvMediaP;
    TextView tvTotalP;
    TextView tvCalidad;

    ImageView imSkin;

    SkinsDetalle skin;
    Ratings ratings;

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

        tvNombreD.setText(skin.getName());
        tvDescD.setText(skin.getDescription());
        tvRarezaD.setText(skin.getRarity());
        tvCosteD.setText(skin.getCost());

        tvMediaP.getText(ratings.getAvgStars());
        tvTotalP.setText(ratings.getTotalPoints());
        tvCalidad.setText(ratings.getNumberVotes());

        String uri = "@drawable/nombre_imagen";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
        imSkin.setImageDrawable(imagen);

    }




}