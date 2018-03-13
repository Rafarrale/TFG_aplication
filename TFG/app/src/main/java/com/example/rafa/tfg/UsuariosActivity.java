package com.example.rafa.tfg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.adapters.usuDataAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rafael on 13/03/2018.
 */


public class UsuariosActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private usuDataAdapter usuDataAdapter;
    private RecyclerView mUsuariosRecycler;

    usuAdapter usuario = new usuAdapter();
    ArrayList<usuAdapter> res = new ArrayList<usuAdapter>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_usuarios);

        usuario.setNombre("Rafa");
        usuario.setApellidos("Arroyo aleman");
        res.add(usuario);


        //Inicialización RecyclerView
        mUsuariosRecycler = findViewById(R.id.recyclerUsuarios);

        final usuDataAdapter adaptador = new usuDataAdapter(res);

        mUsuariosRecycler.setAdapter(adaptador);
        mUsuariosRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_usuarios);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

            }
        });

    }



}
