package com.example.rafa.tfg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.CasaAdapterView;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Configuracion;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.SettingPreferences;
import com.example.rafa.tfg.clases.SharedPrefManager;
import com.example.rafa.tfg.clases.Token;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.fragments.LogDispFragment;
import com.example.rafa.tfg.fragments.SeleccionAlarmaFragment;
import com.example.rafa.tfg.fragments.ContenedorFragment;
import com.example.rafa.tfg.fragments.DispositivosFragment;
import com.example.rafa.tfg.fragments.RojoFragment;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.ESPACIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_BOTON;
import static com.example.rafa.tfg.clases.Constantes.PREFS_KEY;
import static com.example.rafa.tfg.clases.Constantes.PREFS_TOKEN;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_USUARIO;

public class NavPrincActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RojoFragment.OnFragmentInteractionListener,
        SeleccionAlarmaFragment.OnFragmentInteractionListener,LogDispFragment.OnFragmentInteractionListener,
        ContenedorFragment.OnFragmentInteractionListener, DispositivosFragment.OnFragmentInteractionListener{

    private List<Casa> fListCasas = new ArrayList<>();
    private Map<Integer, List<Casa>> fListCasasRes = new HashMap<Integer, List<Casa>>();
    private usuAdapter usuario;
    private TextView tUsuario;
    private TextView tUsuarioEmail;
    private NavigationView navigationView;
    private List<CasaAdapterIni> listaCasas;
    private Token tokenC = new Token();
    private SettingPreferences settingPreferences;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> values = new ArrayList<String>();
    private Spinner spinner = null;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**Responder ante la apertura y cierre del navegation View*/
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        //--- Establecemos el Contenedor Fragment como vista principal al ejecutarse la activity principal
                        Fragment fragment = new ContenedorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                        Utilidades.validaPantalla = false;
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        recuperaDatosExtraFromMainActivity();
        actualizaToken();
    }

    private void actualizaToken(){
        settingPreferences = new SettingPreferences(this.getApplicationContext());
        tokenC = settingPreferences.getToken(PREFS_TOKEN);
        if(listaCasas != null && (!listaCasas.isEmpty() && tokenC == null || tokenC.getToken() == null)){
            for(int i = 0; i < listaCasas.size(); i++) {
                String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
                Token auxToken = new Token();
                auxToken.setToken(token);
                auxToken.setHomeUsu(listaCasas.get(i).getHomeUsu());
                ActualizaToken actualizaToken = new ActualizaToken(auxToken);
                actualizaToken.execute();
            }
        }else {
            String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            tokenC.setToken(token);
            ActualizaToken actualizaToken = new ActualizaToken(tokenC);
            actualizaToken.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilidades.iniciaAplicacion = false;
    }

    private void recuperaDatosExtraFromMainActivity() {

        SharedPreferences preferences = getSharedPreferences(PREFS_KEY,MODE_PRIVATE);
        boolean botonSesion = preferences.getBoolean(ESTADO_BOTON, false);
        if(!botonSesion){
            String userJson = getIntent().getStringExtra("USER");
            Gson gson = new Gson();
            usuario = gson.fromJson(userJson, usuAdapter.class);
            Bundle bundle = getIntent().getExtras();
            listaCasas = bundle.getParcelableArrayList("CASAS");
        }else{
            SharedPreferences preferencesMain = getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
            String userJson = preferencesMain.getString(ESTADO_USUARIO, null);
            Gson gson = new Gson();
            usuario = gson.fromJson(userJson, usuAdapter.class);
            preferencesMain = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
            String casasJson = preferencesMain.getString(ESTADO_CASAS, null);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(casasJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Type listType = new TypeToken<ArrayList<CasaAdapterIni>>(){}.getType();
            listaCasas = new Gson().fromJson(String.valueOf(jsonArray), listType);
        }

        añadeListaFCasa(listaCasas);

        añadeCamposCabecera();
    }

    private void añadeListaFCasa(List<CasaAdapterIni> lista) {
        for(CasaAdapterIni aux : lista){
            Configuracion confAux = new Configuracion();
            if(aux.getConfiguracion() != null) {
                confAux.setEstadoAlarma(aux.getConfiguracion().getEstadoAlarma());
            }
            fListCasas.add(new Casa(aux.get_id(), aux.getHomeUsu(), confAux));
            fListCasasRes.put(0, fListCasas);
        }
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

        spinner = findViewById(R.id.spinnerMen);

        // Elementos en Spinner
        values = new ArrayList<String>();
        if(listaCasas != null && listaCasas.size() != 0){
            for(CasaAdapterIni aux : listaCasas){
                values.add(aux.getHomeUsu());
            }
        }else{
            values.add(Constantes.CASA_VACIO);
        }
        values.add(Constantes.añadirCasa);
        values.add(Constantes.eliminarCasa);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cambia el Color
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                //Cambia el Tamaño
                //((TextView) parent.getChildAt(0)).setTextSize(5);
                // On selecting a spinner item

                String item = parent.getItemAtPosition(position).toString();

                if (item.equals(Constantes.añadirCasa)) {
                    spinner.setSelection(0); //Selecciona para qeu muestre el valor 0 por defecto
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavPrincActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.set_casa, null);
                    final EditText mNombreCasa = mView.findViewById(R.id.etNombreCasa);
                    Button btnAñadeCasa = mView.findViewById(R.id.btAñadeCasa);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    btnAñadeCasa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mNombreCasa.getText().toString().isEmpty()) {
                                compruebaHomeUsu(mNombreCasa.getText().toString());
                            } else {
                                Toast.makeText(NavPrincActivity.this, "Por favor rellena el campo vacío", Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void compruebaHomeUsu(String homeUsu) {
                            RestInterface rest = RestImpl.getRestInstance();
                            Call<Void> restCheckCredentials = rest.compruebaHomeUsu(new CasaAdapterIni(homeUsu));
                            restCheckCredentials.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(NavPrincActivity.this, "El nombre ya existe", Toast.LENGTH_SHORT).show();
                                    } else {
                                        añadeCasa(values, mNombreCasa.getText().toString());
                                        dialog.cancel();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    });
                }else if(item.equals(Constantes.eliminarCasa)) {
                    spinner.setSelection(0); //Selecciona para que muestre el valor 0 por defecto
                    ArrayList<String> cValues = new ArrayList<>(values);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavPrincActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.eliminar_casa, null);
                    cValues.remove(cValues.size() - 1);
                    cValues.remove(cValues.size() - 1);
                    if(listaCasas == null || values.get(0).equals(Constantes.CASA_VACIO)){
                        cValues.remove(cValues.size() - 1);
                        cValues.add(Constantes.CASA_VACIO_ELIMINAR);

                    }
                    ListView lv = mView.findViewById(R.id.lvListaCasas);
                    CasaAdapterView adapter = new CasaAdapterView(NavPrincActivity.this, cValues);
                    lv.setAdapter(adapter);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int pos = position;
                            String eliminaEstaCasa = values.get(pos);
                            values.remove(pos);
                            eliminaCasa(values, eliminaEstaCasa);
                            dialog.cancel();

                        }
                    });
                }else{
                    if(fListCasasRes.get(0) != null) {
                        Iterator<Casa> itr = fListCasasRes.get(0).listIterator();
                        if(itr.hasNext() == false){
                            itr = fListCasasRes.get(1).listIterator();
                        }
                        List<Casa> auxList = new ArrayList<>();
                        List<Casa> auxListRemove = new ArrayList<>();
                        while (itr.hasNext()) {
                            Casa data = itr.next();
                            auxListRemove.add(data);
                            if (data.getHomeUsu().equals(item)) {
                                auxList.add(data);
                                if (fListCasasRes.get(1) != null && fListCasasRes.get(1).size() > 0) {
                                    auxListRemove.add(fListCasasRes.get(1).get(0));
                                }
                                fListCasasRes.put(1, auxList);
                                auxListRemove.remove(auxListRemove.indexOf(data));
                                if(auxListRemove.contains(data)){
                                    auxListRemove.remove(auxListRemove.indexOf(data));
                                }
                            }
                        }

                        fListCasasRes.put(0, auxListRemove);
                    }


                    if(!Utilidades.iniciaAplicacion){
                        Utilidades.iniciaAplicacion = true;
                        //--- Establecemos el Contenedor Fragment como vista principal al ejecutarse la activity principal
                        Fragment fragment = new ContenedorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                        Utilidades.validaPantalla = false;
                        //---
                    }

                }
            }

            public void onNothingSelected(AdapterView <?> arg0){

            }

        });

        return true;
    }

    public void añadeCasa(List<String> lista, final String valor){
        spinner = findViewById(R.id.spinnerMen);
        if(lista.get(0).equals(Constantes.CASA_VACIO)){
            lista.remove(lista.size() - 3);
        }
        lista.add(lista.size() - 2, valor);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
/**
 * Se usa para refrescar el spinner BaseAdapter
 */
/*
        BaseAdapter adapter = (BaseAdapter) spinner.getAdapter();
        adapter.notifyDataSetChanged();
*/
        /**
         * Añadiendo el token del dispositivo
         */
        tokenC = new Token();
        String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        tokenC.setToken(token);
        tokenC.setHomeUsu(valor);
        InsertaToken insertaToken = new InsertaToken(tokenC);
        insertaToken.execute();

        RestInterface rest = RestImpl.getRestInstance();
        Call<Void> restAddHome = rest.addCasa(new CasaAdapterIni(valor, usuario.getPass()));
        restAddHome.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NavPrincActivity.this, "Casa añadida", Toast.LENGTH_SHORT).show();
                    fListCasas.add(new Casa(valor));
                    fListCasasRes.put(0, fListCasas);

                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString(ESTADO_CASAS, gson.toJson(fListCasas));
                    editor.apply();

                    if(fListCasasRes.get(1) == null) {
                        fListCasasRes.put(1, fListCasas);
                    }

                }else{
                    Toast.makeText(NavPrincActivity.this, "No fue posible añadir la casa", Toast.LENGTH_SHORT).show();
                    eliminaCasa(values, valor);
                    values.remove(valor);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    public void eliminaCasa(List<String> lista, final String casa){
        spinner = findViewById(R.id.spinnerMen);
        if(lista.size() <= 2 || lista.equals(null)){
            lista.add(lista.size() - 2, Constantes.CASA_VACIO);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        RestInterface rest = RestImpl.getRestInstance();
        Call<Void> restDropHome = rest.eliminaCasa(new CasaAdapterIni(casa, null));
        restDropHome.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    if(!casa.equals(Constantes.CASA_VACIO)) {
                        Toast.makeText(NavPrincActivity.this, "Casa eliminada", Toast.LENGTH_SHORT).show();
                        settingPreferences = new SettingPreferences(getApplicationContext());
                        settingPreferences.remove(PREFS_TOKEN);
                        List<Casa> fListCasasCopy = new ArrayList<>(fListCasas);
                        Iterator<Casa> itr = fListCasasCopy.listIterator();

                        if(fListCasasCopy.size() == 0){
                            fListCasasRes = new HashMap<>();
                        }
                        while(itr.hasNext()){
                            Casa aux = itr.next();

                            if(fListCasasRes.get(1).get(0).getHomeUsu().equals(aux.getHomeUsu())){
                                fListCasas.remove(aux);
                            }

                            if(aux.getHomeUsu().equals(casa)){
                                fListCasas.remove(aux);
                                if(!fListCasasRes.get(1).get(0).getHomeUsu().equals(fListCasasRes.get(0).get(0).getHomeUsu())){
                                    fListCasasRes.put(0, fListCasas);
                                }
                                if(fListCasasRes.get(1).get(0).getHomeUsu().equals(casa)){
                                    fListCasasRes.put(1,fListCasas);
                                }
                            }
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        if(fListCasas.size() == 0 && fListCasasRes.size() != 0){
                            editor.putString(ESTADO_CASAS, gson.toJson(fListCasasRes.get(1)));
                            editor.apply();
                        }else if(fListCasas.size() != 0){
                            List<Casa> aux = fListCasas;
                            aux.add(fListCasasRes.get(1).get(0));
                            editor.putString(ESTADO_CASAS, gson.toJson(aux));
                            editor.apply();
                        }else{
                            List<Casa> aux = fListCasas;
                            editor.putString(ESTADO_CASAS, gson.toJson(aux));
                            editor.apply();
                        }
                    }
                }else{
                    Toast.makeText(NavPrincActivity.this, "No fue posible eliminar la casa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

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



        if (id == R.id.nav_usuarios) {
            Intent intent = new Intent(NavPrincActivity.this, UsuariosActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("USUARIO_ACTUAL", usuario);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_notificaciones) {

        } else if (id == R.id.nav_micasa) {

        }else if (id == R.id.nav_chat) {

        }else if (id == R.id.cerrar_sesion) {
            //Borra la caracteristica ESTADO_BOTON guardada anteriormente para cerrar sesion
            SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            settings.edit().remove(ESTADO_BOTON).commit();

            Intent intent = new Intent(NavPrincActivity.this,MainActivity.class);
            startActivity(intent);
            NavPrincActivity.this.finish();
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

    public usuAdapter getDataUsuarioFragment(){
        return usuario;
    }

    public Map<Integer, List<Casa>> getDataListaCasasFragment(){
        return fListCasasRes;
    }

    public List<Casa> getListaCasasFragment(){
        return fListCasas;
    }

    protected class ActualizaToken extends AsyncTask<Void,Void,Token>{
        private Token token;

        public ActualizaToken(Token token) {
            this.token = token;
        }

        @Override
        protected Token doInBackground(Void... voids) {

            Token res = null;
            RestInterface rest = RestImpl.getRestInstance();
            if(token != null) {
                Call<Token> tokenRest = rest.actualizaToken(token);
                try {
                    Response<Token> responseToken = tokenRest.execute();
                    if (responseToken.isSuccessful()) {
                        res = responseToken.body();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(Token token) {
            if(token != null) {
                tokenC = token;
                settingPreferences.save(tokenC, PREFS_TOKEN);
            }
        }
    }
    protected class InsertaToken extends AsyncTask<Void,Void,Token>{

        private Token token;

        public InsertaToken(Token token) {
            this.token = token;
        }

        @Override
        protected Token doInBackground(Void... voids) {

            Token res = null;
            RestInterface rest = RestImpl.getRestInstance();
            Call<Token> tokenRest = rest.enviaToken(token);
            try {
                Response<Token> responseToken = tokenRest.execute();
                if(responseToken.isSuccessful()){
                    res = responseToken.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(Token token) {
            tokenC = token;
            settingPreferences.save(tokenC, PREFS_TOKEN);
        }
    }
}

