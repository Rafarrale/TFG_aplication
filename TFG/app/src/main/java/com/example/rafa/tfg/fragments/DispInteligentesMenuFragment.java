package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rafa.tfg.DispInteligentesActivity;
import com.example.rafa.tfg.DispositivosActivity;
import com.example.rafa.tfg.InterruptorActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Constantes;
import com.google.gson.Gson;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;


public class DispInteligentesMenuFragment extends Fragment {

    private Casa casa;
    String seleccion;
    String arrayName[] = {Constantes.DISP_INTERRUPTOR_LABEL, Constantes.DISP_CALEFACCION_LABEL, Constantes.DISP_JARDINERIA_LABEL};
    private OnFragmentInteractionListener mListener;

    public DispInteligentesMenuFragment() {
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

        View view = inflater.inflate(R.layout.fragment_disp_inteligentes_menu, container, false);
        CircleMenu circleMenu = view.findViewById(R.id.circle_menu_disp);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_home_black_menu_24dp, R.drawable.ic_menu_send)
                .addSubMenu((Color.parseColor("#258CFF")), R.drawable.ic_interruptor)
                .addSubMenu((Color.parseColor("#6d4c41")), R.drawable.ic_temperatura)
                .addSubMenu((Color.parseColor("#7CD623")), R.drawable.ic_plantas)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        seleccion = arrayName[index];
                        Toast.makeText(getContext(), "Seleccionaste: " + seleccion, Toast.LENGTH_SHORT).show();
                    }
                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }
            
            @Override
            public void onMenuClosed() {
                if(seleccion == Constantes.DISP_INTERRUPTOR_LABEL){
                    Intent intent = new Intent(getContext(), DispInteligentesActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra(Constantes.CASA_ACTUAL, gson.toJson(casa));
                    startActivity(intent);

                }else if(seleccion == Constantes.DISP_CALEFACCION_LABEL){

                }else if(seleccion == Constantes.DISP_JARDINERIA_LABEL){

                }
            }

        });
        // Inflate the layout for this fragment
        return view;
    }

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
