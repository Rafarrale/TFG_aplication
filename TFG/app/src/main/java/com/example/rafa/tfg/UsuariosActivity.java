package com.example.rafa.tfg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.adapters.usuDataAdapter;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Rafael on 13/03/2018.
 */


public class UsuariosActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private usuDataAdapter usuDataAdapter;
    private RecyclerView mUsuariosRecycler;

    usuAdapter usuario = new usuAdapter();
    ArrayList<usuAdapter> listUsuariosF = new ArrayList<usuAdapter>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_usuarios);

        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);
        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        listUsuariosF.add(usuario);


        //Inicialización RecyclerView
        mUsuariosRecycler = findViewById(R.id.recyclerUsuarios);

        usuDataAdapter = new usuDataAdapter(this, listUsuariosF);
        usuDataAdapter.setOnItemClickListener(new usuDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(usuAdapter clickedAppointment) {
                // TODO: Codificar acciones de click en items
            }

            @Override
            public void onCancelAppointment(usuAdapter canceledAppointment) {

            }

        });
        mUsuariosRecycler.setAdapter(usuDataAdapter);

        mUsuariosRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_usuarios);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                usuariosExecute usuariosExecute = new usuariosExecute();
                usuariosExecute.execute();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    protected class usuariosExecute extends AsyncTask<Void,Void,ArrayList<usuAdapter>>{

        @Override
        protected ArrayList<usuAdapter> doInBackground(Void... voids) {
            ArrayList<usuAdapter> res = new ArrayList<usuAdapter>();

            RestInterface rest = RestImpl.getRestInstance();
            Call<ArrayList<usuAdapter>> restUsu = rest.getTodosUsuarios();

            try{
                Response<ArrayList<usuAdapter>> responseUsuarios = restUsu.execute();
                if(responseUsuarios.isSuccessful()){
                    res = responseUsuarios.body();

                }

            }catch(IOException io){

            }

            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<usuAdapter> usuAdapters) {
            super.onPostExecute(usuAdapters);
            usuDataAdapter.swapItems(usuAdapters);
            swipeRefreshLayout.setRefreshing(false);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información", Toast.LENGTH_SHORT).show();

        }
    }
}
