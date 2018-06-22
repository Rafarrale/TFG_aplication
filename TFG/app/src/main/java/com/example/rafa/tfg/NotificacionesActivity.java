package com.example.rafa.tfg;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.NoficacionesAdapter;
import com.example.rafa.tfg.adapters.NotificacionDispHora;
import com.example.rafa.tfg.adapters.eliminaKeysAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Utilidades;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.rafa.tfg.clases.Constantes.ESTADO_NOTIFICACIONES;
import static com.example.rafa.tfg.clases.Constantes.PREFS_NOTIFICACIONES;

public class NotificacionesActivity extends AppCompatActivity {

    private RecyclerView notificacionesRecycler;
    private NoficacionesAdapter notificacionesAdapter;
    private List<NotificacionDispHora> listNotificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        notificacionesRecycler = findViewById(R.id.recyclerNotificaciones);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NOTIFICACIONES, MODE_PRIVATE);
        listNotificaciones = new ArrayList<>();
        String auxShared = sharedPreferences.getString(ESTADO_NOTIFICACIONES, null);
        if(auxShared != null){
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(auxShared);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Type listType = new TypeToken<ArrayList<NotificacionDispHora>>(){}.getType();
            listNotificaciones = new Gson().fromJson(String.valueOf(jsonArray), listType);
            for(int i = 0; i < listNotificaciones.size(); i++){
                listNotificaciones.get(i).setVisto(true);
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ESTADO_NOTIFICACIONES, new Gson().toJson(listNotificaciones));
        editor.apply();



        notificacionesAdapter = new NoficacionesAdapter(listNotificaciones, NotificacionesActivity.this);
        notificacionesRecycler.setAdapter(notificacionesAdapter);
        notificacionesRecycler.setLayoutManager(new LinearLayoutManager(NotificacionesActivity.this, LinearLayoutManager.VERTICAL, false));

        notificacionesAdapter.setOnItemClickListener(new NoficacionesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificacionDispHora clickedAppointment) {
            }
        });

    }
}
