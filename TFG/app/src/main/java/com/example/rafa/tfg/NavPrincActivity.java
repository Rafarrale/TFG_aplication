package com.example.rafa.tfg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.fragments.AmarilloFragment;
import com.example.rafa.tfg.fragments.ContenedorFragment;
import com.example.rafa.tfg.fragments.GreenFragment;
import com.example.rafa.tfg.fragments.RojoFragment;
import com.example.rafa.tfg.rest.usuAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.rafa.tfg.clases.Constantes.ESPACIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_BOTON;
import static com.example.rafa.tfg.clases.Constantes.PREFS_KEY;

public class NavPrincActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RojoFragment.OnFragmentInteractionListener,
        AmarilloFragment.OnFragmentInteractionListener,GreenFragment.OnFragmentInteractionListener,
        ContenedorFragment.OnFragmentInteractionListener {

    private usuAdapter usuario;
    private TextView tUsuario;
    private TextView tUsuarioEmail;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_princ);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(Utilidades.validaPantalla ){
            //--- Establecemos el Contenedor Fragment como vista principal al ejecutarse la activity principal
            Fragment fragment = new ContenedorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            Utilidades.validaPantalla = false;
            //---
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String userJson = getIntent().getStringExtra("USER");
        Gson gson = new Gson();
        usuario = gson.fromJson(userJson, usuAdapter.class);

        añadeCamposCabecera();

    }

    public void añadeCamposCabecera(){
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(usuario.getNombre());
        nombreCompleto.append(ESPACIO);
        nombreCompleto.append(usuario.getApellidos());
        //con esto generamos el usuario y email en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        tUsuario = hView.findViewById(R.id.nav_usuario);
        tUsuarioEmail = hView.findViewById(R.id.nav_usuario_email);
        tUsuario.setText(nombreCompleto);
        tUsuarioEmail.setText(usuario.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_princ, menu);

        Spinner spinner = findViewById(R.id.spinnerMen);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cambia el Color
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                //Cambia el Tamaño
                //((TextView) parent.getChildAt(0)).setTextSize(5);
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView <?> arg0){

            }
        });

        // Elementos en Spinner
        List<String> values = new ArrayList<String>();
        values.add("Bormujos");
        values.add("Playa");
        values.add("Miami");
        values.add(Constantes.añadirCasa);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;

        if (id == R.id.nav_camera) {
            miFragment = new ContenedorFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }else if (id == R.id.nav_share) {

        }else if (id == R.id.nav_send) {

        }else if (id == R.id.cerrar_sesion) {
            //Borra la caracteristica ESTADO_BOTON guardada anteriormente para cerrar sesion
            SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            settings.edit().remove(ESTADO_BOTON).commit();

            Intent intent = new Intent(NavPrincActivity.this,MainActivity.class);
            startActivity(intent);
        }

        if(fragmentSeleccionado == true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
