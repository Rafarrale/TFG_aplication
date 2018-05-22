package com.example.rafa.tfg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.ESTADO_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_CASAS;
import static com.example.rafa.tfg.clases.Constantes.PREFS_USUARIO;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_BOTON;
import static com.example.rafa.tfg.clases.Constantes.PREFS_KEY;
import static com.example.rafa.tfg.clases.Constantes.ESTADO_USUARIO;

public class MainActivity extends AppCompatActivity {
    Button btn_registrar;
    private View mProgressView;
    private UserLoginTask mUserLoginTask = null;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mUsuarioView;
    private EditText mPasswordView;
    private CheckBox guardar_pass;
    private List<CasaAdapterIni> casasExtra;
    private usuAdapter auxUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        mProgressView = findViewById(R.id.login_progress);
        mUsuarioView = findViewById(R.id.logUsu);
        mPasswordView = findViewById(R.id.edt_cod_seg);
        guardar_pass = findViewById(R.id.guardar_pass);

        if(obtener_estado_boton()){
            Intent intent = new Intent(MainActivity.this,NavPrincActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

        //Metodo OnClickListener Registrar
        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this,Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
    }

    public void guardar_estado_boton(){
        SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean(ESTADO_BOTON, guardar_pass.isChecked());
        editor.apply();
    }

    public boolean obtener_estado_boton(){
        SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return settings.getBoolean(ESTADO_BOTON,false);
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsuarioView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String usuario = mUsuarioView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid usuario
        if (TextUtils.isEmpty(usuario)) {
            mUsuarioView.setError(getString(R.string.error_field_required));
            focusView = mUsuarioView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(usuario, password);
            mAuthTask.execute((Void) null);


        }
    }
    //</editor-fold>

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }



    //Metodo click del login
    public void onClickLog(View view) {
        /**Se pone esta variable a true si estando en la pantalla principal le damos a volver a esta pantalla
         * para que aplique el contenedor Fragment*/
        Utilidades.validaPantalla = true;
        Utilidades.iniciaAplicacion = false;
        attemptLogin();
    }

    // Metodo click de recodar contraseña
    public void onClickRecordar(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
        View mViewRecuperaPass = getLayoutInflater().inflate(R.layout.recupera_pass, null);
        final EditText emailRecupera = mViewRecuperaPass.findViewById(R.id.edt_recupera_contraseña);
        emailRecupera.setError(null);
        Button btnRecupar = mViewRecuperaPass.findViewById(R.id.btn_recupera_pass);
        alertBuilder.setView(mViewRecuperaPass);
        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        btnRecupar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestInterface rest = RestImpl.getRestInstance();
                Call<Void> apiRest = rest.getPass(emailRecupera.getText().toString());
                apiRest.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Revise su correo electrónico para recuperar su contraseña", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                        }else{
                            Toast.makeText(MainActivity.this, "El email es incorrecto", Toast.LENGTH_SHORT).show();
                            emailRecupera.setError(getString(R.string.email_incorrecto));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "No se pudo realizar la operacion", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected class UserLoginTask extends AsyncTask<Void,Void,usuAdapter>{

        private final String usuario;
        private final String password;

        public UserLoginTask(String usuario, String password){
            this.usuario = usuario;
            this.password = password;
        }

        @Override
        protected usuAdapter doInBackground(Void... params) {
            usuAdapter res = null;
            RestInterface rest = RestImpl.getRestInstance();

            Call<usuAdapter> restCheckCredentials = rest.checkLogin(usuario,password);

            try{
                Response<usuAdapter> response = restCheckCredentials.execute();
                if(response.isSuccessful()){
                    res = response.body();

                }

            }catch(IOException io){

            }

            return res;
        }

        protected void onPostExecute(final usuAdapter user){
            mAuthTask = null;
            showProgress(false);

            if(user != null) {
                auxUsuario = user;
                passNavPrinc();
            }else{
                mPasswordView.setError(getString(R.string.error_incorrect_password_Usu));
                mPasswordView.requestFocus();
                Toast.makeText(getApplicationContext(), "Login Incorrecto", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void passNavPrinc() {
        List<CasaAdapterIni> res = null;
        RestInterface rest = RestImpl.getRestInstance();

        Call<List<CasaAdapterIni>> restCasas = rest.getCasas(auxUsuario.getPassCasa());

        restCasas.enqueue(new Callback<List<CasaAdapterIni>>() {
            @Override
            public void onResponse(Call<List<CasaAdapterIni>> call, Response<List<CasaAdapterIni>> response) {
                if (response.isSuccessful()) {
                    casasExtra = response.body();
                    Intent intent = new Intent(MainActivity.this, NavPrincActivity.class);
                    intent.putExtra("USER", auxUsuario.toJson());
                    /* Guardar datos para "recordar inicio sesion" */
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_USUARIO, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ESTADO_USUARIO, auxUsuario.toJson());
                    editor.apply();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("CASAS", (ArrayList<? extends Parcelable>) casasExtra);
                    intent.putExtras(bundle);

                    sharedPreferences = getSharedPreferences(PREFS_CASAS, MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString(ESTADO_CASAS, gson.toJson(casasExtra));
                    editor.apply();

                    guardar_estado_boton();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Correcto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CasaAdapterIni>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
