package com.example.rafa.tfg.clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.InterruptorActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapter;
import com.example.rafa.tfg.adapters.DispositivosLogDataAdapter;
import com.example.rafa.tfg.adapters.NotificacionDispHora;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.DISP_CALEFACCION;
import static com.example.rafa.tfg.clases.Constantes.DISP_CONTACTO;
import static com.example.rafa.tfg.clases.Constantes.DISP_INTERRUPTOR;
import static com.example.rafa.tfg.clases.Constantes.DISP_JARDINERIA;
import static com.example.rafa.tfg.clases.Constantes.TIPO_ALERT_DIALOG;
import static com.example.rafa.tfg.clases.Constantes.TIPO_INTENT;

/**
 * Created by Rafael on 20/02/2018.
 */

public class Utilidades {
    private static View mView;
    private static Activity mActivity;

    public static int rotacion = 0;
    public static boolean validaPantalla = true;
    public static int CasaSeleccionada = 0;
    public static boolean iniciaAplicacion = false;
    public static boolean radioButton = false;
    public static boolean regresaDisp = false;
    public static boolean regresaMiCasaKey = false;
    private static boolean mAccion = false;
    private static List<DispositivosAdapter> listDisp;
    public static Integer badge = 0;
    public static List<NotificacionDispHora> listNotificaciones = new ArrayList<>();


    public static Object difTipoDisp(String tipo, Activity activity, boolean accion){
        Object res = null;
        mActivity = activity;
        mAccion = accion;
        if(tipo.equals(DISP_CONTACTO)){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            mView = activity.getLayoutInflater().inflate(R.layout.item_actualiza_dispositivo, null);
            builder.setView(mView);
            res = builder;
        }else if(tipo.equals(DISP_INTERRUPTOR) && accion){
            Intent intent = new Intent(activity, InterruptorActivity.class);
            res = intent;
        }else if(tipo.equals(DISP_JARDINERIA) && accion){
            /** Paso a clase jardineria*/
            //Intent intent = new Intent(activity, "clase aqui");
            //res = intent;
        }else if(tipo.equals(DISP_CALEFACCION) && accion){
            /** Paso a clase calefaccion*/
            //Intent intent = new Intent(activity, "clase aqui");
            //res = intent;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            mView = activity.getLayoutInflater().inflate(R.layout.item_actualiza_dispositivo, null);
            builder.setView(mView);
            res = builder;
        }
        return res;
    }

    public static void muestraTipo(final DispositivosAdapter dispositivo, Object objTipo, final DispositivosLogDataAdapter dispositivosLogDataAdapter){


        if(objTipo.getClass().getName().equals(TIPO_ALERT_DIALOG) && !mAccion && (dispositivo.getTipo().equals(DISP_CONTACTO) || dispositivo.getTipo().equals(DISP_INTERRUPTOR)
                || dispositivo.getTipo().equals(DISP_JARDINERIA))){

            AlertDialog.Builder builder = (AlertDialog.Builder)objTipo;
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            final TextView edt_nombre_disp_act = mView.findViewById(R.id.edt_nombre_disp_act);
            final TextView edt_hab_disp_act = mView.findViewById(R.id.edt_hab_disp_act);
            Button btn_actualiza_disp = mView.findViewById(R.id.btn_actualiza_disp);
            btn_actualiza_disp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = edt_nombre_disp_act.getText().toString();
                    String habitacion = edt_hab_disp_act.getText().toString();
                    edt_nombre_disp_act.setError(null);
                    edt_hab_disp_act.setError(null);
                    boolean cancel = false;
                    View focusView = null;
                    if(TextUtils.isEmpty(nombre)){
                        edt_nombre_disp_act.setError(mView.getContext().getString(R.string.error_field_required));
                        focusView = edt_nombre_disp_act;
                        cancel = true;
                    }else if(TextUtils.isEmpty(habitacion)){
                        edt_hab_disp_act.setError(mView.getContext().getString(R.string.error_field_required));
                        focusView = edt_hab_disp_act;
                        cancel = true;
                    }

                    if(cancel){
                        focusView.requestFocus();
                    }
                    if(!cancel){
                        dispositivo.setName(nombre);
                        dispositivo.setHabitacion(habitacion);
                        LogDispActivity logDispActivity = (LogDispActivity)mActivity;
                        logDispActivity.actualizaPresentacionDisp(dispositivo);
                        RestInterface rest = RestImpl.getRestInstance();
                        Call<Void> call = rest.actualizaDispositivoCasa(dispositivo);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(mView.getContext(), "Actualización realizada satisfactoriamente", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(mView.getContext(), "No se pudo realizar la operación", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else if(objTipo.getClass().getName().equals(TIPO_INTENT) && dispositivo.getTipo().equals(DISP_INTERRUPTOR)){
            Intent intent = (Intent) objTipo;
            Gson gson = new Gson();
            intent.putExtra(Constantes.DISP_SELECCIONADO, gson.toJson(dispositivo));
            mActivity.startActivity(intent);
        }
    }
}
