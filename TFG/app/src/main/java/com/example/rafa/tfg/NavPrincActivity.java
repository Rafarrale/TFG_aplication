package com.example.rafa.tfg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.CasaAdapterView;
import com.example.rafa.tfg.adapters.NotificacionDispHora;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Configuracion;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.SharedPrefManager;
import com.example.rafa.tfg.clases.Token;
import com.example.rafa.tfg.clases.Utilidades;
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

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.CASA_ACTUAL;
import static com.example.rafa.tfg.clases.Constantes.ESPACIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_CASAS;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_NOTIFICACIONES;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_TOKEN;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_URI_IMAGEN_NAV_HEADER;
import static com.example.rafa.tfg.clases.Constantes.GUARDADAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_NOTIFICACIONES;
import static com.example.rafa.tfg.clases.Constantes.PREFS_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_BOTON;
import static com.example.rafa.tfg.clases.Constantes.PREFS_KEY;
import static com.example.rafa.tfg.clases.Constantes.PREFS_TOKEN;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.PRINCIPAL;
import static com.example.rafa.tfg.clases.Constantes.VALUE_0;

public class NavPrincActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RojoFragment.OnFragmentInteractionListener,
        SeleccionAlarmaFragment.OnFragmentInteractionListener,ContenedorFragment.OnFragmentInteractionListener, DispositivosFragment.OnFragmentInteractionListener{

    private List<Casa> fListCasas = new ArrayList<>();
    private Map<Integer, List<Casa>> fListCasasRes = new HashMap<Integer, List<Casa>>();
    private usuAdapter usuario;
    private TextView tUsuario;
    private TextView tUsuarioEmail;
    private NavigationView navigationView;
    private List<CasaAdapterIni> listaCasas;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> values = new ArrayList<String>();
    private Spinner spinner = null;
    private Token token = new Token();
    private TextView contBadge;
    private ImageView imageBadge;
    List<NotificacionDispHora> listNotificaciones;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView foto_gallery;

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

                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NOTIFICACIONES, MODE_PRIVATE);
                        listNotificaciones = new ArrayList<>();
                        String auxShared = sharedPreferences.getString(ESTADO_NOTIFICACIONES, null);
                        Integer nVistas = 0;
                        if(auxShared != null){
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(auxShared);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Type listType = new TypeToken<ArrayList<NotificacionDispHora>>(){}.getType();
                            listNotificaciones = new Gson().fromJson(String.valueOf(jsonArray), listType);
                            for(int i = 0; i < listNotificaciones.size(); i++){
                                if(!listNotificaciones.get(i).isVisto()){
                                    nVistas++;
                                }
                            }
                        }
                        // Respond when the drawer's position changes
                        if (nVistas > 0) {
                            contBadge.setVisibility(View.VISIBLE);
                            imageBadge.setVisibility(View.VISIBLE);
                            contBadge.setText(String.valueOf(nVistas));

                        } else {
                            contBadge.setVisibility(View.GONE);
                            imageBadge.setVisibility(View.GONE);
                        }
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
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_TOKEN, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String recoveryToken = sharedPreferences.getString(ESTADO_TOKEN, null);
        Token auxToken = gson.fromJson(recoveryToken, Token.class);
        token.setToken(SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken());
        token.setKeyToUse(usuario.getKeyToUse());
        token.setPassCasa(usuario.getPassCasa());
        if(auxToken != null){
            token.set_id(auxToken.get_id());
        }
        RestInterface restInterface = RestImpl.getRestInstance();
        Call<Token> rest = restInterface.anadeToken(token);
        rest.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token = response.body();
                    editor.putString(ESTADO_TOKEN ,gson.toJson(token));
                    editor.apply();

                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("Error", "No se pudo actualizar el token en BBDD");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //actualizaToken(); //TODO actualizar token?¿
        if(usuario.getPassCasa().size() == VALUE_0){
            Intent intent = new Intent(NavPrincActivity.this, MiCasaActivity.class);
            startActivity(intent);
            NavPrincActivity.this.finish();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        Utilidades.iniciaAplicacion = false;
    }

    public void recuperaDatosExtraFromMainActivity() {

        SharedPreferences preferences = getSharedPreferences(PREFS_KEY,MODE_PRIVATE);
        boolean botonSesion = preferences.getBoolean(ESTADO_BOTON, false);
        if(!botonSesion){

            if(Utilidades.regresaMiCasaKey){
                Utilidades.regresaMiCasaKey = false;
                /** Usuario */
                SharedPreferences preferencesMain = getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
                String userJson = preferencesMain.getString(ESTADO_USUARIO, null);
                Gson gson = new Gson();
                usuario = gson.fromJson(userJson, usuAdapter.class);
                /** Casas */
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
            }else{
                String userJson = getIntent().getStringExtra("USER");
                Gson gson = new Gson();
                usuario = gson.fromJson(userJson, usuAdapter.class);
                Bundle bundle = getIntent().getExtras();
                listaCasas = bundle.getParcelableArrayList("CASAS");
            }
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

        contBadge = findViewById(R.id.tv_count_notification);
        imageBadge = findViewById(R.id.iv_count_notification);

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
                    spinner.setSelection(0); /**Selecciona para que muestre el valor 0 por defecto */
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

        foto_gallery = findViewById(R.id.circle_image_nav_header);

        CargaImagen cargaImagen = new CargaImagen(this);
        cargaImagen.execute();

        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            Float rotacionOrigen = getImageRotation(imageUri, getApplicationContext());
            Bitmap rotateBitmap = Utilidades.rotarImagen(imageUri, rotacionOrigen, this);
            GuardaCargaImagen GuardaCargaImagen = new GuardaCargaImagen(rotateBitmap, this);
            GuardaCargaImagen.execute();
        }
    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public float getImageRotation(Uri path, Context context) {
        try {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};

            Cursor cursor = context.getContentResolver().query(path, projection, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            cursor.close();

        } catch (Exception ex) {
            return 0f;
        }

        return 0f;
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
        RestInterface rest = RestImpl.getRestInstance();
        Call<Void> restAddHome = rest.addCasa(new CasaAdapterIni(valor, usuario.getPassCasa().get(usuario.getKeyToUse()).getKey()));
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

                    /** Añadimos token a Casa*/
                    token.setCasa(valor);
                    RestInterface restInterface = RestImpl.getRestInstance();
                    Call<Void> rest = restInterface.anadeTokenCasa(token);
                    rest.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                            }else{
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("Error", "No se pudo actualizar el token en BBDD");
                        }
                    });

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
                        for(int i = 0; i < fListCasas.size(); i++){
                            if(fListCasas.get(i).getHomeUsu().equals(casa)){
                                fListCasas.remove(i);
                            }
                        }
                        if(fListCasas.size() == VALUE_0){
                            fListCasasRes.get(PRINCIPAL).remove(0);
                        }

                        List<Casa> aux = fListCasasRes.get(GUARDADAS);
                        for (int i = 0; i < aux.size(); i++){
                            if(aux.get(i).getHomeUsu().equals(casa)){
                                fListCasasRes.get(GUARDADAS).remove(i);
                            }
                        }
                        // Guardar en prefreencias
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.putString(ESTADO_CASAS, gson.toJson(fListCasas));
                        editor.apply();
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
            intent.putExtra("USER", usuario.toJson());
            startActivity(intent);

        } else if (id == R.id.nav_notificaciones) {
            Intent intent = new Intent(NavPrincActivity.this, NotificacionesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constantes.ESTADO_NOTIFICACIONES, (ArrayList<? extends Parcelable>) listNotificaciones);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_micasa) {
            Intent intent = new Intent(NavPrincActivity.this, MiCasaActivity.class);
            if(fListCasasRes.get(PRINCIPAL) != null && fListCasasRes.size() > 0){
                intent.putExtra(CASA_ACTUAL, fListCasasRes.get(PRINCIPAL).get(VALUE_0).getHomeUsu());
            }
            startActivity(intent);
            NavPrincActivity.this.finish();

        }else if (id == R.id.nav_chat) {

        }else if (id == R.id.cerrar_sesion) {
            //Borra la caracteristica ESTADO_BOTON guardada anteriormente para cerrar sesion
            SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            settings.edit().remove(ESTADO_BOTON).commit();
            Intent intent = new Intent(NavPrincActivity.this, MainActivity.class);
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

    protected class GuardaCargaImagen extends AsyncTask<Void, Void, String>{
        private Bitmap imagen;
        private Activity activity;

        public GuardaCargaImagen(Bitmap imagen, Activity activity) {
            this.imagen = imagen;
            this.activity = activity;

        }

        @Override
        protected String doInBackground(Void... voids) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagen.compress(Bitmap.CompressFormat.WEBP, 0, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            String encoded = Base64.encodeToString(b, Base64.DEFAULT);

            String auxIma =  (String)Utilidades.sharedPreferencesGet(activity, Constantes.PREFS_URI_IMAGEN_NAV_HEADER, Constantes.ESTADO_URI_IMAGEN_NAV_HEADER);
            Map<String, String> saveImagen = new Gson().fromJson(auxIma, HashMap.class);
            if(saveImagen == null){
                saveImagen = new HashMap<>();
            }
            saveImagen.put(usuario.getNombre(), encoded);
            Utilidades.sharedPreferencesSave(activity, Constantes.PREFS_URI_IMAGEN_NAV_HEADER, ESTADO_URI_IMAGEN_NAV_HEADER, saveImagen);
            return encoded;
        }

        @Override
        protected void onPostExecute(String encoded) {
            foto_gallery.setImageBitmap(imagen);
        }
    }

    protected class CargaImagen extends AsyncTask<Void, Void, Bitmap>{
        private Activity activity;

        public CargaImagen(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String auxIma =  (String)Utilidades.sharedPreferencesGet(activity, Constantes.PREFS_URI_IMAGEN_NAV_HEADER, Constantes.ESTADO_URI_IMAGEN_NAV_HEADER);
            Map<String, String> imagenes = new Gson().fromJson(auxIma, HashMap.class);
            Bitmap bitmap = null;
            if(imagenes != null){
                String bytes = imagenes.get(usuario.getNombre());
                if( bytes!= null){
                    byte[] imageAsBytes = Base64.decode(bytes.getBytes(), Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                }
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            foto_gallery.setImageBitmap(bitmap);
        }
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

}

