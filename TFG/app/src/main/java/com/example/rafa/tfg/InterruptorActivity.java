package com.example.rafa.tfg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.clases.Caracteristicas;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase dedicada a la conexion del estado del dispositivo ON OFF
 * @author Rafael
 */
public class InterruptorActivity extends AppCompatActivity {

    DispositivosAdapter dispositivo;
    ToggleButton boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interruptor);

        Gson gson = new Gson();
        String disp = getIntent().getStringExtra(Constantes.DISP_SELECCIONADO);
        dispositivo = gson.fromJson(disp, DispositivosAdapter.class);

        TextView nombre = findViewById(R.id.tv_nom_disp);
        TextView habitacion = findViewById(R.id.tv_hab_disp);
        TextView id = findViewById(R.id.tv_id_disp);

        nombre.setText(dispositivo.getName());
        habitacion.setText(dispositivo.getHabitacion());
        id.setText(dispositivo.get_id());
        if(dispositivo.getCaracteristicas() != null){
            boton.setChecked(dispositivo.getCaracteristicas().isActiva());
        }

        boton = findViewById(R.id.toggleButtonDisp);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dispositivo.getCaracteristicas() == null){
                    Caracteristicas caracteristicas = new Caracteristicas();
                    dispositivo.setCaracteristicas(caracteristicas);
                }
                if(boton.isChecked()){
                    Toast.makeText(InterruptorActivity.this, Constantes.ON, Toast.LENGTH_SHORT).show();
                    dispositivo.getCaracteristicas().setActiva(true);
                    conecta(Constantes.ON);

                }else if(!boton.isChecked()){
                    Toast.makeText(InterruptorActivity.this, Constantes.OFF, Toast.LENGTH_SHORT).show();
                    dispositivo.getCaracteristicas().setActiva(false);
                    conecta(Constantes.OFF);
                }

            }
        });

    }

    private void conecta(String mensaje){
        RestInterface rest = RestImpl.getRestInstance();
        Call<Void> call = rest.interruptorDispositivoCasa(dispositivo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.i("Entregado", "Se entrego correctamente");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(InterruptorActivity.this, "No se puedo realizar la operación", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
