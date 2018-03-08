package com.example.rafa.tfg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
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
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.example.rafa.tfg.adapters.usuAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.rafa.tfg.clases.Constantes.ESTADO_BOTON;
import static com.example.rafa.tfg.clases.Constantes.PREFS_KEY;

public class MainActivity extends AppCompatActivity {
    Button btn_registrar;
    private View mProgressView;
    private UserLoginTask mUserLoginTask = null;
    private UserLoginTask mAuthTask = null;
    private CasaTask mCasaTask = null;
    private AutoCompleteTextView mUsuarioView;
    private EditText mPasswordView;
    private CheckBox guardar_pass;
    private List<CasaAdapterIni> casasExtra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        mProgressView = findViewById(R.id.login_progress);
        mUsuarioView = findViewById(R.id.logUsu);
        mPasswordView = findViewById(R.id.edt_cod_seg);
        guardar_pass = findViewById(R.id.guardar_pass);
/*
        if(obtener_estado_boton()){
            Intent intent = new Intent(MainActivity.this,NavPrincActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
*/
        //Metodo OnClickListener Registrar
        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this,Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });




        //<editor-fold desc="Pruebas del progressBar">
        /*
        showProgress(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgress(false);
            }
        }, 5000);

        */
        //</editor-fold>
        //<editor-fold desc="Avisamos que la actividad esta creada">
    /*
        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show();
        // La actividad está creada.
    */
        //</editor-fold>
    }

    public void guardar_estado_boton(){
        SharedPreferences settings = getSharedPreferences(PREFS_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean(ESTADO_BOTON, guardar_pass.isChecked());
        editor.apply();
    }

    public boolean obtener_estado_boton(){
        SharedPreferences settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return settings.getBoolean(ESTADO_BOTON,false);
    }
    //<editor-fold desc="ProgressBar">
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
/*
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
*/
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    //</editor-fold>



    //<editor-fold desc="attemptLogin">
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
            mCasaTask = new CasaTask();
            mCasaTask.execute();
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

                Intent intent = new Intent(MainActivity.this, NavPrincActivity.class);
                intent.putExtra("USER", user.toJson());

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("CASAS", (ArrayList<? extends Parcelable>) casasExtra);
                intent.putExtras(bundle);

                guardar_estado_boton();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login Correcto", Toast.LENGTH_SHORT).show();

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

    protected class CasaTask extends AsyncTask<Void,Void,List<CasaAdapterIni>>{

        @Override
        protected List<CasaAdapterIni> doInBackground(Void... params) {
            List<CasaAdapterIni> res = null;
            RestInterface rest = RestImpl.getRestInstance();

            Call<List<CasaAdapterIni>> restCasas = rest.getCasas();

            try{
                Response<List<CasaAdapterIni>> responseCasas = restCasas.execute();
                if(responseCasas.isSuccessful()){
                    res = responseCasas.body();

                }

            }catch(IOException io){

            }

            return res;
        }

        protected void onPostExecute(final List<CasaAdapterIni> casas){
            casasExtra = casas;
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información", Toast.LENGTH_SHORT).show();

        }
    }

    //<editor-fold desc="Fases de vida de la activity">
    /*
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart", Toast.LENGTH_SHORT).show();
        // La actividad está a punto de hacerse visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        // La actividad se ha vuelto visible (ahora se "reanuda").
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show();
        // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show();
        // La actividad ya no es visible (ahora está "detenida")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show();
        // La actividad está a punto de ser destruida.
    }
    */
    //</editor-fold>


}
