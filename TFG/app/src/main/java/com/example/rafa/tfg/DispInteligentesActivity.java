package com.example.rafa.tfg;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapter;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispInteligentesActivity extends AppCompatActivity {

    private RecyclerView mDispositivosRecycler;
    private DispositivosDataAdapter dispositivosDataAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Casa casa;
    private View mainView = null;
    String tipoDisp;
    View emptyDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_disp_inteligentes);
        getSupportActionBar().setTitle(R.string.interruptores);

        /**Recuperamos datos del frament que venimos*/
        String dispActual = getIntent().getStringExtra(Constantes.CASA_ACTUAL);
        tipoDisp = getIntent().getStringExtra(Constantes.TIPO_DISP);
        Gson gson = new Gson();
        casa = gson.fromJson(dispActual, Casa.class);

        if(casa != null){

            mDispositivosRecycler  = findViewById(R.id.recyclerDispositivosInteligentes);
            emptyDisp = findViewById(R.id.dispInteligentesEmpty);
            dispositivosDataAdapter = new DispositivosDataAdapter(getApplicationContext(), new ArrayList<DispositivosAdapter>());
            dispositivosDataAdapter.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DispositivosAdapter clickedAppointment) {
                    Intent intent = null;
                    if(clickedAppointment.getTipo().equals(Constantes.DISP_INTERRUPTOR)){
                        intent = new Intent(getApplicationContext(), InterruptorActivity.class);
                    }else if(clickedAppointment.getTipo().equals(Constantes.DISP_CALEFACCION)){
                        //intent = new Intent(getApplicationContext(), "Clase");

                    }else if(clickedAppointment.getTipo().equals(Constantes.DISP_JARDINERIA)){
                        //intent = new Intent(getApplicationContext(), "Clase");

                    }
                    Gson gson = new Gson();
                    intent.putExtra(Constantes.DISP_SELECCIONADO, gson.toJson(clickedAppointment));
                    startActivity(intent);
                }
            });

            mDispositivosRecycler.setAdapter(dispositivosDataAdapter);
            mDispositivosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_dispositivos_inteligentes);
            swipeRefreshLayout.setRefreshing(true);
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu(), tipoDisp);
            response.enqueue(new Callback<List<DispositivosAdapter>>() {
                @Override
                public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                    if(response.isSuccessful()){
                        List<DispositivosAdapter> aux = response.body();
                        if(aux.size() != 0 && aux.get(0).get_id() != null) {
                            dispositivosDataAdapter.swapItems(response.body());
                            swipeRefreshLayout.setRefreshing(false);
                        }else{
                            swipeRefreshLayout.setRefreshing(false);
                            emptyDisp.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DispositivosAdapter>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else{
            emptyDisp.setVisibility(View.VISIBLE);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(casa != null){
                    swipeRefreshLayout.setRefreshing(true);
                    RestInterface rest = RestImpl.getRestInstance();
                    Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu(), tipoDisp);
                    response.enqueue(new Callback<List<DispositivosAdapter>>() {
                        @Override
                        public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                            if(response.isSuccessful()){
                                List<DispositivosAdapter> aux = response.body();
                                if(aux.get(0).get_id() != null) {
                                    dispositivosDataAdapter.swapItems(response.body());
                                    swipeRefreshLayout.setRefreshing(false);
                                }else{
                                    swipeRefreshLayout.setRefreshing(false);
                                    emptyDisp.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DispositivosAdapter>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Añadir primero una Casa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
