package com.example.rafa.tfg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.CasaPass;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapterAnade;
import com.example.rafa.tfg.adapters.eliminaKeysAdapter;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Token;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.esp_touch_activity.EsptouchActivity;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.ESTADO_CASAS;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_TOKEN;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.NO_DISPONIBLE;
import static com.example.rafa.tfg.clases.Constantes.PREFS_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_TOKEN;
import static com.example.rafa.tfg.clases.Constantes.PREFS_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.VALUE_0;
import static com.example.rafa.tfg.clases.Constantes.VALUE_403;
import static com.example.rafa.tfg.clases.Constantes.VALUE_NEGATIVO_1;
import static com.example.rafa.tfg.rest.RestImpl.getRestInstance;

public class MiCasaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> bankDatos= new ArrayList<>();
    TextView tvInformacionClaves;
    usuAdapter usuario = new usuAdapter();
    Button btn_reg_clave;
    Button btn_elimina_clave;
    EditText edt_nuevaClave;
    TextView tvMiCasa;
    Spinner spinner;
    List<CasaPass> auxListPassCasa = new ArrayList<>();
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;
    Token auxToken;
    RecyclerView keysRecyclerElimina;
    eliminaKeysAdapter eliminaKeysAdapter;
    List<CasaAdapterIni> resCasas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_casa);
        /** Editor usuario*/
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        recuperaDatos();
        spinner = findViewById(R.id.sp_MisClaves);
        btn_reg_clave = findViewById(R.id.bt_reg_clave);
        btn_elimina_clave = findViewById(R.id.bt_elimina_clave);
        tvMiCasa = findViewById(R.id.tvMiCasa);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bankDatos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String casaActual = getIntent().getStringExtra(Constantes.CASA_ACTUAL);
        tvMiCasa.setText(casaActual);
    }

    public void recuperaDatos(){
        SharedPreferences preferencesMain = getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
        String userJson = preferencesMain.getString(ESTADO_USUARIO, null);
        Gson gson = new Gson();
        usuario = gson.fromJson(userJson, usuAdapter.class);

        /** Recupera token*/
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_TOKEN, MODE_PRIVATE);
        String recoveryToken = sharedPreferences.getString(ESTADO_TOKEN, null);
        auxToken = gson.fromJson(recoveryToken, Token.class);

        recargaDatos();
    }

    public void recargaDatos(){
        auxListPassCasa = usuario.getPassCasa();
        bankDatos.clear();
        bankDatos.add(Constantes.VER_KEYS); /** Campo 0 del spinner*/
        for(int i = 0; i < auxListPassCasa.size(); i++){
            bankDatos.add(auxListPassCasa.get(i).getKey());
        }
    }
    //Metodo click de informacion claves
    public void onClickInformacionClaves(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MiCasaActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.informacion_key, null);
        builder.setView(mView);
        alertDialog = builder.create();
        alertDialog.show();
    }
    //Metodo click de elimina clave
    public void onClickEliminaClave(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MiCasaActivity.this);
        View mView;
        if(usuario.getPassCasa().size() > 0){
            mView = getLayoutInflater().inflate(R.layout.elimina_key, null);
            keysRecyclerElimina = mView.findViewById(R.id.recyclerKeyElimina);
            /** Añade las claves del usuario */
            eliminaKeysAdapter = new eliminaKeysAdapter(usuario.getPassCasa(), MiCasaActivity.this);
            keysRecyclerElimina.setAdapter(eliminaKeysAdapter);
            keysRecyclerElimina.setLayoutManager(new LinearLayoutManager(MiCasaActivity.this, LinearLayoutManager.VERTICAL, false));

            /** Muestra el PopUp */
            builder.setView(mView);
            final AlertDialog alertDialogElimina = builder.create();
            alertDialogElimina.show();

            /** Conexion para la operacion*/
            eliminaKeysAdapter.setOnItemClickListener(new eliminaKeysAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final CasaPass clickedAppointment) {
                    RestInterface apiRest = getRestInstance();
                    Call<Void> rest = apiRest.eliminaKey(clickedAppointment.getKey(), auxToken.get_id(), usuario.getUser());
                    rest.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                List<CasaPass> auxPassCasa;
                                int indexKey = usuario.getPassCasa().indexOf(clickedAppointment);
                                auxPassCasa = usuario.getPassCasa();
                                auxPassCasa.remove(indexKey);
                                usuario.setPassCasa(auxPassCasa);
                                if(indexKey == usuario.getKeyToUse()){
                                    usuario.setKeyToUse(VALUE_NEGATIVO_1);
                                    resCasas = new ArrayList<>();
                                    tvMiCasa.setText(NO_DISPONIBLE);
                                }
                                actualizarDatosLocal();
                                recargaDatos();
                                /** Refrescamos el sppiner*/
                                BaseAdapter baseAdapter = (BaseAdapter) spinner.getAdapter();
                                baseAdapter.notifyDataSetChanged();
                                /***/
                                alertDialogElimina.cancel();
                                Toast.makeText(MiCasaActivity.this, "La clave se eliminó correctamente", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MiCasaActivity.this, "No se elimino la clave", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MiCasaActivity.this, "Falló el proceso de eliminación de clave", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }else{
            mView = getLayoutInflater().inflate(R.layout.keys_vacio, null);
            builder.setView(mView);
            final AlertDialog alertDialogElimina = builder.create();
            alertDialogElimina.show();
        }
    }

    private void actualizarDatosLocal(){
        /** Usuario*/
        editor.putString(ESTADO_USUARIO, usuario.toJson());
        editor.apply();
        /** Casas*/
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
        SharedPreferences.Editor editorCasas = sharedPreferences.edit();
        Gson gson = new Gson();
        editorCasas.putString(ESTADO_CASAS, gson.toJson(resCasas));
        editorCasas.apply();
    }

    public void onClickAñadeKey(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MiCasaActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.anade_elimina_key, null);
        edt_nuevaClave = mView.findViewById(R.id.edt_nueva_clave);
        builder.setView(mView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    //Metodo click de Registro claves
    public void onClickRegistraClave(final View view) {
        final String aux = edt_nuevaClave.getText().toString();
        edt_nuevaClave.setError(null);
        if(TextUtils.isEmpty(aux)){
            edt_nuevaClave.setError(getString(R.string.error_field_required));
        }else{
            RestInterface rest = getRestInstance();
            Call<Void> respRest = rest.actualizaTokenUsuario(aux, auxToken.get_id(), usuario.getUser());
            respRest.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        CasaPass auxCasaPass = new CasaPass(aux);
                        auxListPassCasa.add(auxCasaPass);
                        usuario.setPassCasa(auxListPassCasa);
                        actualizarDatosLocal();
                        HideKeyboard(view);
                        alertDialog.cancel();
                        recargaDatos();
                        Snackbar.make(getWindow().getDecorView().getRootView(), "La clave se registró correctamente", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }else if(response.code() == VALUE_403){
                        Toast.makeText(MiCasaActivity.this, "La clave no es válida", Toast.LENGTH_SHORT).show();
                        edt_nuevaClave.setText("");
                        edt_nuevaClave.setError(getString(R.string.error_password));
                    } else {
                        Toast.makeText(MiCasaActivity.this, "La clave ya existe", Toast.LENGTH_SHORT).show();
                        edt_nuevaClave.setError(getString(R.string.error_password));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar.make(view, "No se pudo registrar la nueva clave", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

        if(bankDatos.get(position) != Constantes.VER_KEYS) {
            usuario.setKeyToUse(position - 1); /** Elegimos aqui la key que se va a usar */

            RestInterface restInterface = getRestInstance();
            Call<Void> recovery = restInterface.actualizaKeyToUse(usuario.getUser(), usuario.getKeyToUse());
            recovery.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        editor.putString(ESTADO_USUARIO, usuario.toJson());
                        editor.apply();
                        Snackbar.make(view, "La clave de registro se cambió correctamente", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        /** Recupereamos las casas*/
                        RestInterface rest = getRestInstance();
                        Call<List<CasaAdapterIni>> restCasas = rest.getCasas(usuario.getPassCasa().get(usuario.getKeyToUse()).getKey());
                        restCasas.enqueue(new Callback<List<CasaAdapterIni>>() {
                            @Override
                            public void onResponse(Call<List<CasaAdapterIni>> call, Response<List<CasaAdapterIni>> response) {
                                if (response.isSuccessful()) {
                                    resCasas = response.body();
                                    actualizarDatosLocal();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CasaAdapterIni>> call, Throwable t) {

                            }
                        });
                    } else {
                        Snackbar.make(view, "La clave de registro no se pudo cambiar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        spinner.setSelection(0);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar.make(view, "No se pudo registrar el cambio de nueva clave", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        if(usuario.getKeyToUse() == VALUE_NEGATIVO_1 && auxListPassCasa.size() != 0){
            new AlertDialog.Builder(MiCasaActivity.this)
                    .setMessage("Se tiene que seleccionar primero una clave de uso")
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        }else if(auxListPassCasa.size() == 0){
                new AlertDialog.Builder(MiCasaActivity.this)
                        .setMessage("Se tiene que añadir una clave para continuar")
                        .setNegativeButton(R.string.cancelar, null)
                        .show();
        }else{
            Utilidades.regresaMiCasaKey = true;
            Intent intent = new Intent(MiCasaActivity.this, NavPrincActivity.class);
            startActivity(intent);
            MiCasaActivity.this.finish();
        }
    }

    //*****************************************************
    // Ocultar el teclado virtual
    //*****************************************************
    private void HideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
