package com.example.rafa.tfg.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.DispositivosActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapterAnade;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.esp_touch_activity.EsptouchActivity;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.TIPO_ALERT_DIALOG;
import static com.example.rafa.tfg.clases.Utilidades.difTipoDisp;
import static com.example.rafa.tfg.clases.Utilidades.muestraTipo;

public class DispositivosFragment extends Fragment {

    private RecyclerView mDispositivosRecycler;
    private DispositivosDataAdapter dispositivosDataAdapter;
    private List<DispositivosAdapter> listDispositivosDataAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Casa casa;
    View mainView;
    private OnFragmentInteractionListener mListener;

    public DispositivosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DispositivosActivity dispositivosActivity = (DispositivosActivity) getActivity();
        casa = dispositivosActivity.casaActual();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(casa != null){
            // Inflate the layout for this fragment
            mainView =  inflater.inflate(R.layout.fragment_dispositivos, container, false);
            mDispositivosRecycler  = mainView.findViewById(R.id.recyclerDispositivos);

            dispositivosDataAdapter = new DispositivosDataAdapter(getContext(), new ArrayList<DispositivosAdapter>());
            dispositivosDataAdapter.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DispositivosAdapter clickedAppointment) {
                    /**Aqui obtenemos el tipo de objeto con el que trabajaremos segun el tipo de dispositivo
                     * y posteriormente en muestra tipo mostramos la accion a realizar
                     * que puede ser ej:
                     *              contacto --> solo actualizamos
                     *              interruptor --> actualizamos y accionamos*/
                    Object tipo = difTipoDisp(clickedAppointment.getTipo(), getActivity());
                    muestraTipo(clickedAppointment, tipo, dispositivosDataAdapter);
                    /***/

                }
            });

            dispositivosDataAdapter.setOnLongItemClickListener(new DispositivosDataAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(final DispositivosAdapter clickedAppointment) {
                    AlertDialog.Builder builderAlert= new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.item_disp_elimina, null);
                    builderAlert.setView(mView);
                    TextView tv_nom_disp = mView.findViewById(R.id.tv_elimina_disp_nom);
                    TextView tv_nom_hab = mView.findViewById(R.id.tv_elimina_disp_hab);
                    Button bt_elimina_disp = mView.findViewById(R.id.bt_elimina_disp);

                    StringBuilder habit = new StringBuilder();
                    habit.append(Constantes.HABITACION);
                    habit.append(Constantes.DOS_PUNTOS_ESPACIO);
                    habit.append(clickedAppointment.getHabitacion());

                    StringBuilder nom = new StringBuilder();
                    nom.append(Constantes.NOMBRE);
                    nom.append(Constantes.DOS_PUNTOS_ESPACIO);
                    nom.append(clickedAppointment.getName());

                    tv_nom_disp.setText(nom);
                    tv_nom_hab.setText(habit);
                    final AlertDialog alertDialog = builderAlert.create();
                    alertDialog.show();

                    bt_elimina_disp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RestInterface restInterface = RestImpl.getRestInstance();
                            Call<Void> call = restInterface.eliminaDispositivo(clickedAppointment);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        alertDialog.cancel();
                                        listDispositivosDataAdapter.remove(clickedAppointment);
                                        dispositivosDataAdapter.swapItems(listDispositivosDataAdapter);
                                        Toast.makeText(getContext(), "Dispositivo eliminado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(), "No se pudo realizar la operacion", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });

            mDispositivosRecycler.setAdapter(dispositivosDataAdapter);
            mDispositivosRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            swipeRefreshLayout = mainView.findViewById(R.id.swipe_refresh_layout_dispositivos);
            swipeRefreshLayout.setRefreshing(true);
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu(), Constantes.DISP_TODOS);
            response.enqueue(new Callback<List<DispositivosAdapter>>() {
                @Override
                public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                    if(response.isSuccessful()){
                        List<DispositivosAdapter> aux = response.body();
                        if(aux.size() != 0 && aux.get(0).get_id() != null) {
                            listDispositivosDataAdapter = new ArrayList<>(response.body());
                            dispositivosDataAdapter.swapItems(response.body());
                            swipeRefreshLayout.setRefreshing(false);
                        }else{
                            swipeRefreshLayout.setRefreshing(false);
                            setViewLayout(R.layout.fragment_dispositivos_vacio);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DispositivosAdapter>> call, Throwable t) {
                    Toast.makeText(getContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else{
            setViewLayout(R.layout.fragment_dispositivos_vacio);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(casa != null){
                    swipeRefreshLayout.setRefreshing(true);
                    RestInterface rest = RestImpl.getRestInstance();
                    Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu(), Constantes.DISP_TODOS);
                    response.enqueue(new Callback<List<DispositivosAdapter>>() {
                        @Override
                        public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                            if(response.isSuccessful()){
                                List<DispositivosAdapter> aux = response.body();
                                if(aux != null) {
                                    listDispositivosDataAdapter = new ArrayList<>(response.body());
                                    dispositivosDataAdapter.swapItems(response.body());
                                    swipeRefreshLayout.setRefreshing(false);
                                }else{
                                    swipeRefreshLayout.setRefreshing(false);
                                    setViewLayout(R.layout.fragment_dispositivos_vacio);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DispositivosAdapter>> call, Throwable t) {
                            Toast.makeText(getContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Añadir primero una Casa", Toast.LENGTH_SHORT).show();
                }
            }
        });

       listenerAnadeDisp(mainView);

        return mainView;
    }

    private void setViewLayout(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        listenerAnadeDisp(mainView);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);
    }
    private void listenerAnadeDisp(View view){
        view.findViewById(R.id.anadeDisp).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), EsptouchActivity.class);
                /** Paso de casa actual a EspTouchActivity*/
                Bundle bundle = new Bundle();
                bundle.putParcelable("CASA", casa);
                intent.putExtras(bundle);

                getActivity().startActivity(intent);
            }
        });
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
