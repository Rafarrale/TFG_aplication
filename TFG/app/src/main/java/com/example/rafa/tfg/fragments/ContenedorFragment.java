package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.SeccionesAdapter;
import com.example.rafa.tfg.clases.Utilidades;


public class ContenedorFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;
    View vista;

    public ContenedorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_cotenedor, container, false);


        if(Utilidades.rotacion == 0){
            View parent = (View) container.getParent();
            if (appBar == null || Utilidades.regresaDisp){
                Utilidades.regresaDisp = false;
                appBar = parent.findViewById(R.id.appBar);
                pestañas = new TabLayout(getActivity());
                pestañas.setTabTextColors(Color.parseColor("#FFFFFFFF"),Color.parseColor("#FFFFFFFF"));
                appBar.addView(pestañas);

                viewPager = vista.findViewById(R.id.idViewPagerInformacion);
                llenarViewPager(viewPager);

                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                pestañas.setupWithViewPager(viewPager);
                pestañas.setTabGravity(TabLayout.GRAVITY_FILL);

            }else{
                Utilidades.rotacion = 1;
            }
        }



        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter adapter = new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new SeleccionAlarmaFragment(),"Control");
        adapter.addFragment(new CamaraFragment(),"Cámaras");
        //adapter.addFragment(new RojoFragment(),"Historial Uso"); //TODO comletar el historial


        viewPager.setAdapter(adapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Utilidades.rotacion == 0){
            appBar.removeView(pestañas);
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
