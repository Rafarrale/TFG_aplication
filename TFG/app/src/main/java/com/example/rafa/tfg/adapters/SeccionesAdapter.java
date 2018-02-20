package com.example.rafa.tfg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 19/02/2018.
 */

public class SeccionesAdapter extends FragmentStatePagerAdapter{

    private final List<Fragment> listaFragments = new ArrayList<>();
    private final List<String> listaTitulos = new ArrayList<>();

    public SeccionesAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String titulo){
        listaFragments.add(fragment);
        listaTitulos.add(titulo);
    }

    @Override //Asiganmos los titulos de la s pestañas
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }

    @Override //Switch case que me arroja las instancias de los fragmentos
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override //Cuantos fragmentos vamos a tener para mostrar con pestañas
    public int getCount() {
        return listaFragments.size();
    }
}
