package com.example.rafa.tfg;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.fragments.ContenedorDispFragment;
import com.example.rafa.tfg.fragments.DispInteligentesMenuFragment;
import com.example.rafa.tfg.fragments.DispositivosFragment;

public class DispositivosActivity extends AppCompatActivity implements ContenedorDispFragment.OnFragmentInteractionListener, DispositivosFragment.OnFragmentInteractionListener,
        DispInteligentesMenuFragment.OnFragmentInteractionListener{

    private Casa casa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            casa = bundle.getParcelable("CASA");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragment = new ContenedorDispFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_disp, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Casa casaActual(){
        return casa;
    }
}
