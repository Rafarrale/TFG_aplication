package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rafa.tfg.DispositivosActivity;
import com.example.rafa.tfg.NavPrincActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.estadoAlarmaAdapter;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Configuracion;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

import static com.example.rafa.tfg.clases.Constantes.ESTADO_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_CASAS;


public class SeleccionAlarmaFragment extends Fragment implements View.OnClickListener{

    private CardView card1,card2,card3,card4,card5;
    private int vale1,vale2,vale3,vale4,vale5 = 0;
    usuAdapter recibeUsu = new usuAdapter();
    Map<Integer, List<Casa>> fListCasasRes = new HashMap<Integer, List<Casa>>();
    List<Casa> listCasasRes = new ArrayList<>();
    int pulsadorAlarma = 0;
    private NavPrincActivity navPrincActivity;



    private OnFragmentInteractionListener mListener;

    public SeleccionAlarmaFragment() {
        // Required empty public constructor
    }


    public static SeleccionAlarmaFragment newInstance(String param1, String param2) {
        SeleccionAlarmaFragment fragment = new SeleccionAlarmaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        navPrincActivity = (NavPrincActivity) getActivity();
        recibeUsu = navPrincActivity.getDataUsuarioFragment();
        fListCasasRes = navPrincActivity.getDataListaCasasFragment();
        listCasasRes = navPrincActivity.getListaCasasFragment();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_alarma, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        card1 = getView().findViewById(R.id.card1);
        card2 = getView().findViewById(R.id.card2);
        card3 = getView().findViewById(R.id.card3);
        card4 = getView().findViewById(R.id.card4);
        card5 = getView().findViewById(R.id.card5);

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getActivity(), DispositivosActivity.class);
                if(fListCasasRes.size() != 0 && fListCasasRes.get(1).size() != 0){
                    Bundle bundle = new Bundle();
                    Casa casa = fListCasasRes.get(1).get(0);
                    bundle.putParcelable("CASA", casa);
                    intent.putExtras(bundle);
                }
                if(listCasasRes.size() != 0){
                    getActivity().startActivity(intent);
                }else{
                    Snackbar.make(getView(), "Primero se debe añadir una casa", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        // Add click listener to the cards
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card5.setOnClickListener(this);
        try {
            if (fListCasasRes.get(1) != null && fListCasasRes.get(1).get(0).getConfiguracion() != null) {
                if (fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma() != null && fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma().equals(Constantes.DESARMAR)) {
                    activaBoton(R.id.card1);
                }

                if (fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma() != null && fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma().equals(Constantes.ARMAR)) {
                    activaBoton(R.id.card2);
                }

                if (fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma() != null && fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma().equals(Constantes.CASA)) {
                    activaBoton(R.id.card3);
                }

                if (fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma() != null && fListCasasRes.get(1).get(0).getConfiguracion().getEstadoAlarma().equals(Constantes.ALARMA)) {
                    activaBoton(R.id.card5);
                }
            }
        }catch(IndexOutOfBoundsException e){

            }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        activaBoton(v.getId());
    }

public void activaBoton(Integer v){
        if(listCasasRes.size() != 0){
            switch(v){
                case R.id.card1:
                    if(vale1 == 0) {
                        card1.setBackgroundColor(Color.parseColor("#8C7C4DFF"));
                        card2.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card3.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card5.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        vale1 = 1;
                        vale2 = 0;
                        vale3 = 0;
                        vale5 = 0;
                        CasaTask casaTask = new CasaTask(Constantes.DESARMAR);
                        casaTask.execute();

                    }else{
                        if(vale1 != 1){
                            card1.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                            vale1 = 0;
                        }
                    }
                    //i = new Intent(this,MisDatosActivity.class);
                    //startActivity(i);
                    break;

                case R.id.card2:

                    if(vale2 == 0) {
                        card2.setBackgroundColor(Color.parseColor("#8CFF4081"));
                        card1.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card3.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card5.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        vale2 = 1;
                        vale1 = 0;
                        vale3 = 0;
                        vale5 = 0;
                        CasaTask casaTask = new CasaTask(Constantes.ARMAR);
                        casaTask.execute();
                    }else{
                        if(vale2 != 1){
                            card2.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                            vale2 = 0;
                        }
                    }

                /*
                i = new Intent(this,);
                startActivity(i);
                */
                    break;
                case R.id.card3:

                    if(vale3 == 0) {
                        card3.setBackgroundColor(Color.parseColor("#8C00BFA5"));
                        //i = new Intent(PrincipalActivity.this,sensorActivity.class);
                        card2.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card1.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card5.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        vale3 = 1;
                        vale1 = 0;
                        vale2 = 0;
                        vale5 = 0;
                        CasaTask casaTask = new CasaTask(Constantes.CASA);
                        casaTask.execute();
                    }else{
                        if(vale3 != 1){
                            card3.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                            vale3 = 0;
                        }
                    }

                /*
                i = new Intent(this,);
                startActivity(i);
                */
                    break;

                case R.id.card5:
                    new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            pulsadorAlarma = 0;
                        }
                    }.start();

                    pulsadorAlarma = pulsadorAlarma + 1;
                    if(vale5 == 0 && pulsadorAlarma == 2) {
                        Toast.makeText(navPrincActivity, "Alarma Activada", Toast.LENGTH_SHORT).show();
                        card5.setBackgroundColor(Color.parseColor("#8CFA0008"));
                        card2.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card3.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        card1.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                        vale5 = 1;
                        vale1 = 0;
                        vale3 = 0;
                        vale2 = 0;
                        CasaTask casaTask = new CasaTask(Constantes.ALARMA);
                        casaTask.execute();
                    }else{
                        if(vale5 != 1){
                            card5.setBackgroundColor(Color.parseColor("#80fcfcfc"));
                            vale5 = 0;
                        }
                    }
                /*
                i = new Intent(this,);
                startActivity(i);
                */
                    break;
                default:
                    break;
            }
        }else{
            Snackbar.make(getView(), "Primero se debe añadir una casa", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
}

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    protected class CasaTask extends AsyncTask<Void,Void,estadoAlarmaAdapter> {
        private String estadoAlarma;

        public CasaTask(String estadoAlarma) {
            this.estadoAlarma = estadoAlarma;
        }

        @Override
        protected estadoAlarmaAdapter doInBackground(Void... params) {
            final estadoAlarmaAdapter[] res = {null};
            final RestInterface rest = RestImpl.getRestInstance();
            if(fListCasasRes.get(1).get(0).getIdPlaca() == null){
                RestInterface restCasa = RestImpl.getRestInstance();
                Call<List<CasaAdapterIni>> callRestCasas = restCasa.getCasa(fListCasasRes.get(1).get(0).getHomeUsu());
                callRestCasas.enqueue(new Callback<List<CasaAdapterIni>>() {
                    @Override
                    public void onResponse(Call<List<CasaAdapterIni>> call, Response<List<CasaAdapterIni>> response) {
                        if(response.isSuccessful()){
                            for(CasaAdapterIni casa : response.body()){
                                if(casa.getHomeUsu().equals(fListCasasRes.get(1).get(0).getHomeUsu())){
                                    fListCasasRes.get(1).get(0).setIdPlaca(casa.getIdPlaca());
                                }
                            }
                            Call<estadoAlarmaAdapter> restCasas = rest.estadoAlarmaCasa(estadoAlarma, fListCasasRes.get(1).get(0).getHomeUsu(), fListCasasRes.get(1).get(0).getIdPlaca());
                            restCasas.enqueue(new Callback<estadoAlarmaAdapter>() {
                                @Override
                                public void onResponse(Call<estadoAlarmaAdapter> call, Response<estadoAlarmaAdapter> response) {
                                    if(response.isSuccessful()){
                                        res[0] = response.body();
                                    }
                                }

                                @Override
                                public void onFailure(Call<estadoAlarmaAdapter> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CasaAdapterIni>> call, Throwable t) {

                    }
                });
            }else{
                try{
                    Call<estadoAlarmaAdapter> restCasas = rest.estadoAlarmaCasa(estadoAlarma, fListCasasRes.get(1).get(0).getHomeUsu(), fListCasasRes.get(1).get(0).getIdPlaca());
                    Response<estadoAlarmaAdapter> responseCasas = restCasas.execute();
                    if(responseCasas.isSuccessful()){
                        res[0] = responseCasas.body();

                    }

                }catch(IOException io){

                }
            }


            return res[0];
        }

        protected void onPostExecute(final estadoAlarmaAdapter estadoAlarmaCasa){
            if(estadoAlarmaCasa != null){
                Configuracion confAux = estadoAlarmaCasa.getConfiguracion();
                fListCasasRes.get(1).get(0).setConfiguracion(confAux);
                String auxHomeUsu = fListCasasRes.get(1).get(0).getHomeUsu();
                for(Casa val: listCasasRes){
                    if(val.getHomeUsu() == auxHomeUsu){
                        val.setConfiguracion(confAux);
                    }
                }
                /***/
                SharedPreferences sharedPreferences = navPrincActivity.getSharedPreferences(PREFS_CASAS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.putString(ESTADO_CASAS ,gson.toJson(listCasasRes));
                editor.apply();
            }
        }

        @Override
        protected void onCancelled() {


        }
    }


}
