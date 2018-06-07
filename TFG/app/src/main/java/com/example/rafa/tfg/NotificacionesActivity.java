package com.example.rafa.tfg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.NoficacionesAdapter;
import com.example.rafa.tfg.adapters.NotificacionDispHora;
import com.example.rafa.tfg.adapters.eliminaKeysAdapter;
import com.example.rafa.tfg.clases.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class NotificacionesActivity extends AppCompatActivity {

    private RecyclerView notificacionesRecycler;
    private NoficacionesAdapter notificacionesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        notificacionesRecycler = findViewById(R.id.recyclerNotificaciones);

        /** Añade las claves del usuario */
        notificacionesAdapter = new NoficacionesAdapter(Utilidades.listNotificaciones, NotificacionesActivity.this);
        notificacionesRecycler.setAdapter(notificacionesAdapter);
        notificacionesRecycler.setLayoutManager(new LinearLayoutManager(NotificacionesActivity.this, LinearLayoutManager.VERTICAL, false));

        notificacionesAdapter.setOnItemClickListener(new NoficacionesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificacionDispHora clickedAppointment) {
            }
        });

    }
}
