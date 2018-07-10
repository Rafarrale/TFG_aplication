package com.example.rafa.tfg.clases;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosLogDataAdapter;
import com.example.rafa.tfg.esp_touch_activity.EsptouchActivity;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.DISP_SELECCIONADO;
import static com.example.rafa.tfg.clases.Utilidades.difTipoDisp;
import static com.example.rafa.tfg.clases.Utilidades.muestraTipo;

public class LogDispActivity extends AppCompatActivity {

    private RecyclerView mDispositivosRecycler;
    private DispositivosLogDataAdapter dispositivosLogDataAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Map<Integer, List<Casa>> listaCasas;
    private Casa casa;
    private DispositivosAdapter datosDispActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_disp);

        getSupportActionBar().setTitle(R.string.Disp);

        Bundle bundle = getIntent().getExtras();
        datosDispActual = bundle.getParcelable(DISP_SELECCIONADO);

        actualizaPresentacionDisp(datosDispActual);

        Utilidades.regresaDisp = true;
        mDispositivosRecycler = findViewById(R.id.recyclerLogDispositivos);
        dispositivosLogDataAdapter = new DispositivosLogDataAdapter(getApplicationContext(), new ArrayList<LogDispositivos>());

        mDispositivosRecycler.setAdapter(dispositivosLogDataAdapter);
        mDispositivosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_log_dispositivos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                LogDispActivity.actualizaLogDispositivo dispDataTask = new LogDispActivity.actualizaLogDispositivo(datosDispActual);
                dispDataTask.execute();
            }
        });

        dispositivosLogDataAdapter.setOnItemClickListener(new DispositivosLogDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LogDispositivos clickedAppointment) {
                // Aqui accion si se desea para log
            }
        });
        LogDispActivity.actualizaLogDispositivo dispDataTask = new LogDispActivity.actualizaLogDispositivo(datosDispActual);
        dispDataTask.execute();

    }


    /**
     * Metodo para el seteo de los campos del dispositivo
     * @param dispositivo
     */
    public void actualizaPresentacionDisp(DispositivosAdapter dispositivo){
        TextView tvNomDispositivoTodosLog = findViewById(R.id.tvNomDispositivoTodosLog);
        TextView tvHabitDispositivoTodosLog = findViewById(R.id.tvHabitDispositivoTodosLog);
        TextView tvIdDispositivoTodos = findViewById(R.id.tvIdDispositivoTodos);

        StringBuilder id = new StringBuilder();
        id.append(Constantes.ID);
        id.append(Constantes.DOS_PUNTOS_ESPACIO);
        id.append(dispositivo.get_id());

        StringBuilder habit = new StringBuilder();
        habit.append(Constantes.HABITACION);
        habit.append(Constantes.DOS_PUNTOS_ESPACIO);
        habit.append(dispositivo.getHabitacion());

        StringBuilder nom = new StringBuilder();
        nom.append(Constantes.NOMBRE);
        nom.append(Constantes.DOS_PUNTOS_ESPACIO);
        nom.append(dispositivo.getName());

        tvNomDispositivoTodosLog.setText(nom);
        tvHabitDispositivoTodosLog.setText(habit);
        tvIdDispositivoTodos.setText(id);
    }

    /**
     * Aqui obtenemos el tipo de objeto con el que trabajaremos segun el tipo de dispositivo
     * y posteriormente en muestra tipo mostramos la accion a realizar
     * que puede ser ej:
     *              contacto --> solo actualizamos
     *              interruptor --> actualizamos y accionamos*
     * @param view
     * @author Rafael
     */
    public void onClickAccionDisp(View view){
        Object tipo = difTipoDisp(datosDispActual.getTipo(), this, false);
        muestraTipo(datosDispActual, tipo, dispositivosLogDataAdapter);
    }

    public void onClickDispInterruptor(View view){
        Object tipo = difTipoDisp(datosDispActual.getTipo(), this, true);
        muestraTipo(datosDispActual, tipo, dispositivosLogDataAdapter);
    }

    protected class actualizaLogDispositivo extends AsyncTask<Void, Void, List<LogDispositivos>>{
        private DispositivosAdapter dispositivo;

        public actualizaLogDispositivo(DispositivosAdapter dispositivosAdapter){
            dispositivo = dispositivosAdapter;
        }

        @Override
        protected List<LogDispositivos> doInBackground(Void... voids) {
            List<LogDispositivos> res = new ArrayList<>();
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<LogDispositivos>> call = rest.getLogDisp(dispositivo);
            try {
                Response<List<LogDispositivos>> response = call.execute();
                if(response.isSuccessful()){
                    res = response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<LogDispositivos> logDispositivos) {
            super.onPostExecute(logDispositivos);
            if(logDispositivos.size() > 0){
                dispositivosLogDataAdapter.swapItems(logDispositivos);
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        protected void onCancelled(List<LogDispositivos> logDispositivos) {
            super.onCancelled(logDispositivos);
            Toast.makeText(LogDispActivity.this, "No se puede realizar la operacion", Toast.LENGTH_SHORT).show();
        }
    }
}
