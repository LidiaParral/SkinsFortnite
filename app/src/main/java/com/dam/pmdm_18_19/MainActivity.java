package com.dam.pmdm_18_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.pmdm_18_19.model.Skin;
import com.dam.pmdm_18_19.model.SkinsDetalle;
import com.dam.pmdm_18_19.recycleUtils.SkinsAdapter;
import com.dam.pmdm_18_19.retrofitUtils.APIRestSkinsFortnite;
import com.dam.pmdm_18_19.retrofitUtils.RetrofitSkinsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String CLAVE_DATOS = "DATOS";
    //1.Crear los variables
    RecyclerView rv;
    SkinsAdapter sAdapter;
    //
    LinearLayoutManager llm;
    EditText etRareza;

    //
    SkinsAdapter adapter;

    //
    TextView tvNombreD;
    TextView tvDescD;
    TextView tvRarezaD;
    TextView tvCosteD;
    TextView tvMediaP;
    TextView tvTotalP;
    TextView tvCalidad;
    ImageView imSkin;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2.Referenciar las variables con las IDs de los atributos
        rv = findViewById(R.id.rvCatalogoSkins);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        etRareza = findViewById(R.id.etRareza);

        id = getIntent().getStringExtra(MainActivity.CLAVE_DATOS);

        tvNombreD = findViewById(R.id.tvNombreD);
        tvDescD = findViewById(R.id.tvDescripD);
        tvRarezaD = findViewById(R.id.tvRarezaD);
        tvCosteD = findViewById(R.id.tvCosteD);
        tvMediaP = findViewById(R.id.tvMediaP);
        tvTotalP = findViewById(R.id.tvTotalPuntos);
        tvCalidad = findViewById(R.id.tvCalidadVotos);

        imSkin = findViewById(R.id.imgSkin);



    }

    //4.
    public void consultar(View view) {
        //10.
        String rareza = etRareza.getText().toString();


        //11.
        if(rareza.isEmpty()){
            invocarWSSinRareza(id);
        } else {
            invocarWSConRareza(rareza);
        }


    }

    private void invocarWSConRareza(String rareza) {
        if(isNetworkAvailable()){
            //12.Crear un cliente RETROFIT
            Retrofit r = RetrofitSkinsClient.getClient(APIRestSkinsFortnite.BASE_URL);
            //13.Crear una estancia del servicio
            APIRestSkinsFortnite asf = r.create(APIRestSkinsFortnite.class);

            //14.Una variable que retorne el valor de invocar al método de obtenerSkin;
            Call<ArrayList<Skin>> call = asf.obtenerSkin(rareza);

            //15.
            call.enqueue(new Callback<ArrayList<Skin>>() {

                //
                @Override
                public void onResponse(Call<ArrayList<Skin>> call, Response<ArrayList<Skin>> response) {
                    if(response.isSuccessful()){
                        ArrayList<Skin> listaSkins = response.body();

                        cargarRecycler(listaSkins);
                    } else {
                        Log.i("RespuestaWS", "Error - " + response.code());
                    }

                }

                //
                @Override
                public void onFailure(Call<ArrayList<Skin>> call, Throwable t) {
                    Log.i("onFailure:", "Error - " + t.getMessage());
                    System.out.println(t.getMessage());
                }

            });
        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private void invocarWSSinRareza(String id) {
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

                    SkinsDetalle skinsDetalle = response.body();

                    cargarDatos(skinsDetalle);

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

    private void cargarDatos(SkinsDetalle skinsDetalle) {
        tvNombreD.setText(skinsDetalle.getName());
        tvDescD.setText(skinsDetalle.getDescription());
        tvCosteD.setText(skinsDetalle.getCost());
        tvRarezaD.setText(skinsDetalle.getRarity());

        String media = String.valueOf(skinsDetalle.getRatings().getAvgStars());
        tvMediaP.setText(media);
        String total = String.valueOf(skinsDetalle.getRatings().getTotalPoints());
        tvTotalP.setText(total);
        String cal = String.valueOf(skinsDetalle.getRatings().getNumberVotes());
        tvCalidad.setText(cal);

        String uri = "@drawable/nombre_imagen";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
        imSkin.setImageDrawable(imagen);


    }

    //9.
    private void cargarRecycler(ArrayList<Skin> listaSkins) {
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ArrayList<Skin> skin = new ArrayList<Skin>();


        sAdapter = new SkinsAdapter(listaSkins);
        sAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: ARRANCAR SIGUIENTE ACTIVITY PASÁNDOLE EL ID DE LA SKIN
                int i = rv.indexOfChild(v);
                //Acceder
                String id = listaSkins.get(i).getIdentifier();

                if(!etRareza.getText().toString().equals("")) {
                    Intent intentDatos = new Intent(getApplicationContext(), DatosSkinActivity.class);
                    intentDatos.putExtra(CLAVE_DATOS,id);
                    startActivity(intentDatos);
                } else {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                }
            }
        });
        rv.setAdapter(sAdapter);
    }


    //3.
    private boolean isNetworkAvailable() {
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