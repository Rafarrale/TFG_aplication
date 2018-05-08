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
import retrofit2.Callback;
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
    private DispositivosDataAdapter dispositivosDataAdapter;
    private DispositivosDataAdapterAnade dispositivosDataAdapterAnade;
    private SwipeRefreshLayout swipeRefreshLayoutDispNuevo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Casa casa;
    View mainView;

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
        if(casa != null){
            // Inflate the layout for this fragment
            mainView =  inflater.inflate(R.layout.fragment_dispositivos, container, false);
            mDispositivosRecycler  = mainView.findViewById(R.id.recyclerDispositivos);

            dispositivosDataAdapter = new DispositivosDataAdapter(getContext(), new ArrayList<DispositivosAdapter>());
            dispositivosDataAdapter.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DispositivosAdapter clickedAppointment) {
                    Toast.makeText(getContext(), "¿Que Hacemos? " + clickedAppointment.get_id(), Toast.LENGTH_SHORT).show();
                }
            });

            mDispositivosRecycler.setAdapter(dispositivosDataAdapter);
            mDispositivosRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            swipeRefreshLayout = mainView.findViewById(R.id.swipe_refresh_layout_dispositivos);
            swipeRefreshLayout.setRefreshing(true);
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu());
            response.enqueue(new Callback<List<DispositivosAdapter>>() {
                @Override
                public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                    if(response.isSuccessful()){
                        List<DispositivosAdapter> aux = response.body();
                        if(aux.get(0).get_id() != null) {
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
                    Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu());
                    response.enqueue(new Callback<List<DispositivosAdapter>>() {
                        @Override
                        public void onResponse(Call<List<DispositivosAdapter>> call, Response<List<DispositivosAdapter>> response) {
                            if(response.isSuccessful()){
                                List<DispositivosAdapter> aux = response.body();
                                if(aux.get(0).get_id() != null) {
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
}
