package com.example.rafa.tfg.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rafa.tfg.DispositivosActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapterAnade;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.esp_touch_activity.EsptouchActivity;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DispositivosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DispositivosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispositivosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mDispositivosRecycler;
    private RecyclerView mDispositivosAnadeRecycler;
    private DispositivosDataAdapter dispositivosDataAdapter;
    private DispositivosDataAdapterAnade dispositivosDataAdapterAnade;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout swipeRefreshLayoutDispNuevo;
    private Casa casa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DispositivosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DispositivosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DispositivosFragment newInstance(String param1, String param2) {
        DispositivosFragment fragment = new DispositivosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        DispositivosActivity dispositivosActivity = (DispositivosActivity) getActivity();
            casa = dispositivosActivity.casaActual();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dispositivos, container, false);

        Utilidades.regresaDisp = true;

        mDispositivosRecycler = v.findViewById(R.id.recyclerDispositivos);
        v.findViewById(R.id.anadeDisp).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), EsptouchActivity.class);
                getActivity().startActivity(intent);

                /** AlertDialog para agregar dispositivos*/
                if (false) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.anade_dispositivos, null);
                    mDispositivosAnadeRecycler = mView.findViewById(R.id.recyclerDispositivosAnade);
                    swipeRefreshLayoutDispNuevo = mView.findViewById(R.id.swipe_refresh_layout_dispositivos_nuevos);
                    swipeRefreshLayoutDispNuevo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            swipeRefreshLayoutDispNuevo.setRefreshing(true);
                            DispDataTaskNuevosDispositivos dispDataTaskNuevosDispositivos = new DispDataTaskNuevosDispositivos();
                            dispDataTaskNuevosDispositivos.execute();
                        }
                    });

                    alertBuilder.setView(mView);
                    AlertDialog alert = alertBuilder.create();

                    dispositivosDataAdapterAnade = new DispositivosDataAdapterAnade(getContext(), new ArrayList<DispositivosAdapter>());
                    mDispositivosAnadeRecycler.setAdapter(dispositivosDataAdapterAnade);
                    mDispositivosAnadeRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                    dispositivosDataAdapterAnade.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DispositivosAdapter clickedAppointment) {
                            Toast.makeText(getContext(), "Añadimos el disp" + clickedAppointment.get_id(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    DispDataTaskNuevosDispositivos dispDataTaskNuevosDispositivos = new DispDataTaskNuevosDispositivos();
                    dispDataTaskNuevosDispositivos.execute();
                    alert.show();
                }
            }
        });



        dispositivosDataAdapter = new DispositivosDataAdapter(getContext(), new ArrayList<DispositivosAdapter>());
        dispositivosDataAdapter.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DispositivosAdapter clickedAppointment) {
                Toast.makeText(getContext(), "¿Que Hacemos? " + clickedAppointment.get_id(), Toast.LENGTH_SHORT).show();

            /** Aqui va la funcionalidad del dispositivo como apertura de nuevo fragment
            Fragment fragment = new LogDispFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            transaction.addToBackStack(null); //PAraque vuelva al fragment anterior
            transaction.commit();
            */
            }
        });

        mDispositivosRecycler.setAdapter(dispositivosDataAdapter);
        mDispositivosRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout_dispositivos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                DispDataTask dispDataTask = new DispDataTask(casa.getHomeUsu());
                dispDataTask.execute();
            }
        });
        DispDataTask dispDataTask = new DispDataTask(casa.getHomeUsu());
        dispDataTask.execute();

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class DispDataTask extends AsyncTask<Void,Void,List<DispositivosAdapter>>{

        private String homeUsu;

        public DispDataTask(String homeUsu) {
            this.homeUsu = homeUsu;
        }

        @Override
        protected List<DispositivosAdapter> doInBackground(Void... voids) {
            List<DispositivosAdapter> res = new ArrayList<>();
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(homeUsu);

            try{
                Response<List<DispositivosAdapter>> resp = response.execute();
                if(resp.isSuccessful()){
                    res = resp.body();
                }
            }catch(IOException e){

            }

            return res;
        }

        @Override
        protected void onPostExecute(List<DispositivosAdapter> dispositivosAdapters) {
            super.onPostExecute(dispositivosAdapters);
            dispositivosDataAdapter.swapItems(dispositivosAdapters);
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onCancelled(List<DispositivosAdapter> dispositivosAdapters) {
            super.onCancelled(dispositivosAdapters);
            Toast.makeText(getContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
        }
    }

    public class DispDataTaskNuevosDispositivos extends AsyncTask<Void,Void,List<DispositivosAdapter>>{

        @Override
        protected List<DispositivosAdapter> doInBackground(Void... voids) {
            List<DispositivosAdapter> res = new ArrayList<>();
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivosNuevos();

            try{
                Response<List<DispositivosAdapter>> resp = response.execute();
                if(resp.isSuccessful()){
                    res = resp.body();
                }
            }catch(IOException e){

            }

            return res;
        }

        @Override
        protected void onPostExecute(List<DispositivosAdapter> dispositivosAdapters) {
            super.onPostExecute(dispositivosAdapters);
            dispositivosDataAdapterAnade.swapItems(dispositivosAdapters);
            swipeRefreshLayoutDispNuevo.setRefreshing(false);
        }

        @Override
        protected void onCancelled(List<DispositivosAdapter> dispositivosAdapters) {
            super.onCancelled(dispositivosAdapters);
            Toast.makeText(getContext(), "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
        }
    }
}
