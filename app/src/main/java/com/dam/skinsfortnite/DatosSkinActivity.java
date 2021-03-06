package com.dam.skinsfortnite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.skinsfortnite.model.SkinsDetalle;
import com.dam.skinsfortnite.retrofitUtils.APIRestSkinsFortnite;
import com.dam.skinsfortnite.retrofitUtils.RetrofitSkinsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DatosSkinActivity extends AppCompatActivity {


    TextView tvNombreD;
    TextView tvDescD;
    TextView tvRarezaD;
    TextView tvCosteD;
    TextView tvMediaP;
    TextView tvTotalP;
    TextView tvCalidad;
    ImageView imSkin;

    String id = "";

    SkinsDetalle details;

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


        id = getIntent().getStringExtra(MainActivity.CLAVE_DATOS);

        //Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        invocarWS();

    }

    private void invocarWS() {
        if(isNetworkAvailable()){
            //5.Crear un cliente RETROFIT
            Retrofit r = RetrofitSkinsClient.getClient(APIRestSkinsFortnite.BASE_URL);
            //6.Crear una estancia del servicio
            APIRestSkinsFortnite asf = r.create(APIRestSkinsFortnite.class);

            //7.Una variable que retorne el valor de invocar al método de obtenerSkin;
            Call<SkinsDetalle> call = asf.obtenerID(id);

            //8.
            call.enqueue(new Callback<SkinsDetalle>() {


                @Override
                public void onResponse(Call<SkinsDetalle> call, Response<SkinsDetalle> response) {
                    if(response.isSuccessful()){
                        details = response.body();

                        cargarDatos(details);
                    } else {
                        Log.i("RespuestaWS", "Error - " + response.code());
                    }

                }

                @Override
                public void onFailure(Call<SkinsDetalle> call, Throwable t) {
                    Log.i("onFailure:", "Error - " + t.getMessage());
                    System.out.println(t.getMessage());
                }
            });
            } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private void cargarDatos(SkinsDetalle details) {
        tvNombreD.setText(details.getName());
        tvDescD.setText(details.getDescription());
        String cost = String.valueOf(details.getCost());
        tvCosteD.setText(cost);
        //tvCosteD.setText(String.valueOf(details.getCost()));
        tvRarezaD.setText(details.getRarity());

        String media = String.valueOf(details.getRatings().getAvgStars());
        tvMediaP.setText(media);
        String total = String.valueOf(details.getRatings().getTotalPoints());
        tvTotalP.setText(total);
        String cal = String.valueOf(details.getRatings().getNumberVotes());
        tvCalidad.setText(cal);

        if(details.getImage() != null && !details.getImage().isEmpty()){
            String uri = "@drawable/" + details.getImage();
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
            imSkin.setImageDrawable(imagen);
        }

    }

    private boolean isNetworkAvailable(){
        boolean isAvailable = false;

        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(MainActivity.CONNECTIVITY_SERVICE);

        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;

    }






}