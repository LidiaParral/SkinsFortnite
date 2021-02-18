package com.dam.pmdm_18_19;

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

import com.dam.pmdm_18_19.model.Skin;
import com.dam.pmdm_18_19.recycleUtils.SkinsAdapter;
import com.dam.pmdm_18_19.retrofitUtils.APIRestSkinsFortnite;
import com.dam.pmdm_18_19.retrofitUtils.RetrofitSkinsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //1.Crear los variables
    RecyclerView rv;
    SkinsAdapter sAdapter;
    //
    LinearLayoutManager llm;
    EditText etRareza;

    //
    SkinsAdapter adapter;
    ArrayList<Skin> listaSkins = new ArrayList<Skin>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2.Referenciar las variables con las IDs de los atributos
        rv = findViewById(R.id.rvCatalogoSkins);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

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


                        adapter = new SkinsAdapter(listaSkins);

                        adapter.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //int i = rv.getChildAdapterPosition(v);

                                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                               /*Intent intentDatos = new Intent(MainActivity.this, DatosSkinActivity.class);
                                startActivity(intentDatos);*/

                            }
                        });

                        rv.setAdapter(adapter);


                        //cargarRecycler(listaSkins);
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

                //
                @Override
                public void onResponse(Call<ArrayList<Skin>> call, Response<ArrayList<Skin>> response) {
                    if(response.isSuccessful()){
                        ArrayList<Skin> listaSkins = response.body();

                        adapter = new SkinsAdapter(listaSkins);


                        adapter.setListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //int i = rv.getChildAdapterPosition(v);

                                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                /*Intent intentDatos = new Intent(MainActivity.this, DatosSkinActivity.class);
                                startActivity(intentDatos);*/

                            }
                        });

                        rv.setAdapter(adapter);

                        //cargarRecycler(listaSkins);
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

    //9.
    private void cargarRecycler(ArrayList<Skin> listaSkins) {
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        sAdapter = new SkinsAdapter(listaSkins);
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