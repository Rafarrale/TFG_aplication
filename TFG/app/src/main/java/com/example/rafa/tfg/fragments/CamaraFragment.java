package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rafa.tfg.NavPrincActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CamaraFragment extends Fragment {

    Casa casa = null;
    List<DispositivosAdapter> fListCasas = new ArrayList<>();
    RecyclerView recyclerViewCamaras = null;
    SwipeRefreshLayout swipeRefreshLayout = null;
    LinearLayout layoutvacio = null;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CamaraFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavPrincActivity navPrincActivity = (NavPrincActivity) getActivity();
        Map<Integer, List<Casa>> mapData = navPrincActivity.getDataListaCasasFragment();
        if(mapData.size() != 0){
            casa = mapData.get(Constantes.VALUE_1).get(Constantes.VALUE_0);
            CargaCamaras cargaCamaras = new CargaCamaras();
            cargaCamaras.execute();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camara_list, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_camara);
        recyclerViewCamaras = view.findViewById(R.id.recyclerCamara);
        layoutvacio = view.findViewById(R.id.dispCamarasEmpty);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                CargaCamaras cargaCamaras = new CargaCamaras();
                cargaCamaras.execute();
            }
        });



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DispositivosAdapter item);
    }

    protected class CargaCamaras extends AsyncTask<Void, Void, List<DispositivosAdapter>>{

        public CargaCamaras() {
        }

        @Override
        protected List<DispositivosAdapter> doInBackground(Void... voids) {
            RestInterface rest = RestImpl.getRestInstance();
            List<DispositivosAdapter> listCasasRequest = new ArrayList<>();
            if(casa != null){
                Call<List<DispositivosAdapter>> response = rest.getTodosDispositivos(casa.getHomeUsu(), Constantes.DISP_CAMARA);
                try {
                    Response<List<DispositivosAdapter>> listCasas = response.execute();
                    if(listCasas.isSuccessful()){
                        listCasasRequest = listCasas.body();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return listCasasRequest;
        }

        @Override
        protected void onPostExecute(List<DispositivosAdapter> dispositivosAdapters) {
            fListCasas = dispositivosAdapters;
            if(dispositivosAdapters.size() != 0 && dispositivosAdapters.get(0).get_id() != null){
                recyclerViewCamaras.setVisibility(View.VISIBLE);
                layoutvacio.setVisibility(View.GONE);
                recyclerViewCamaras.setAdapter(new MyCamaraRecyclerViewAdapter(fListCasas, mListener));
                recyclerViewCamaras.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                swipeRefreshLayout.setRefreshing(false);
            }else{
                recyclerViewCamaras.setVisibility(View.GONE);
                layoutvacio.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
