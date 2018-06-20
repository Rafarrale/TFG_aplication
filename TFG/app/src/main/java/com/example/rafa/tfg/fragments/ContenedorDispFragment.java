package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.SeccionesAdapter;
import com.example.rafa.tfg.clases.Utilidades;

/**
 * Created by Rafael on 20/03/2018.
 */

    public class ContenedorDispFragment extends Fragment{


    private OnFragmentInteractionListener mListener;

    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;
    private SeccionesAdapter adapter;
    View vista;

    public ContenedorDispFragment() {
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
        vista = inflater.inflate(R.layout.fragment_disp_contenedor, container, false);


        if(Utilidades.rotacion == 0){
            View parent = (View) container.getParent();
            if (appBar == null || Utilidades.regresaDisp){
                Utilidades.regresaDisp = false;
                appBar = parent.findViewById(R.id.appBar);
                pestañas = new TabLayout(getActivity());
                pestañas.setTabTextColors(Color.parseColor("#FFFFFFFF"),Color.parseColor("#FFFFFFFF"));
                appBar.addView(pestañas);

                viewPager = vista.findViewById(R.id.idViewPagerDispInformacion);
                llenarViewPager(viewPager);


                pestañas.setupWithViewPager(viewPager);
                pestañas.setTabGravity(TabLayout.GRAVITY_FILL);

            }else{
                Utilidades.rotacion = 1;
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment currentFragment = adapter.getItem(position);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        adapter = new SeccionesAdapter(getFragmentManager());

        adapter.addFragment(new DispInteligentesMenuFragment(),"Dispositivos Inteligentes");
        adapter.addFragment(new DispositivosFragment(),"Gestión e Información");

        viewPager.setAdapter(adapter);
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
