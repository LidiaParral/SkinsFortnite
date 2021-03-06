package com.dam.skinsfortnite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.skinsfortnite.model.Skin;
import com.dam.skinsfortnite.recycleUtils.SkinsAdapter;
import com.dam.skinsfortnite.retrofitUtils.APIRestSkinsFortnite;
import com.dam.skinsfortnite.retrofitUtils.RetrofitSkinsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    static final String CLAVE_DATOS = "id";
    //1.Crear los variables
    RecyclerView rv;
    SkinsAdapter sAdapter;
    //
    LinearLayoutManager llm;
    EditText etRareza;

    //
    SkinsAdapter adapter;



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


    }

    //4.
    public void consultar(View view) {
        //10.
        String rareza = etRareza.getText().toString();


        //11.
        if(rareza.isEmpty()){
            invocarWSSinRareza();
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

    private void invocarWSSinRareza() {
        if(isNetworkAvailable()){
            //5.Crear un cliente RETROFIT
            Retrofit r = RetrofitSkinsClient.getClient(APIRestSkinsFortnite.BASE_URL);
            //6.Crear una estancia del servicio
            APIRestSkinsFortnite asf = r.create(APIRestSkinsFortnite.class);

            //7.Una variable que retorne el valor de invocar al método de obtenerSkin;
            Call<ArrayList<Skin>> call = asf.obtenerSkin();

            //8.
            call.enqueue(new Callback<ArrayList<Skin>>() {


                @Override
                public void onResponse(Call<ArrayList<Skin>> call, Response<ArrayList<Skin>> response) {
                    ArrayList<Skin> listaSkins = response.body();

                    cargarRecycler(listaSkins);
                }

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


    //9.
    private void cargarRecycler(ArrayList<Skin> listaSkins) {
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ArrayList<Skin> skin = new ArrayList<Skin>();


        sAdapter = new SkinsAdapter(listaSkins);
        sAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ARRANCAR SIGUIENTE ACTIVITY PASÁNDOLE EL ID DE LA SKIN
                //Para poder acceder a todas las posiciones que están dentro del Scroll.
                //getChildAdapterPosition:
                Skin skinSeleccionado = listaSkins.get(rv.getChildAdapterPosition(v));

                Intent i = new Intent(MainActivity.this, DatosSkinActivity.class);

                i.putExtra(CLAVE_DATOS,skinSeleccionado.getIdentifier());

                startActivity(i);
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