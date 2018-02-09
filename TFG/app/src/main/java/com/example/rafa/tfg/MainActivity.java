package com.example.rafa.tfg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.example.rafa.tfg.rest.usuAdapter;

import java.io.IOException;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btn_registrar;
    private View mProgressView;
    private UserLoginTask mUserLoginTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        mProgressView = findViewById(R.id.login_progress);

        //Metodo OnClickListener Registrar
        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this,registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });

        //Pruebas del progressBar
        showProgress(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgress(false);
            }
        }, 5000);





        //<editor-fold desc="Avisamos que la actividad esta creada">
    /*
        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show();
        // La actividad está creada.
    */
        //</editor-fold>
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



   /* //<editor-fold desc="attemptLogin">
    *//**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     *//*

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    //</editor-fold>*/




    //Metodo click del kogin
    public void onClickLog(View view) {
        //Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
        //MainActivity.this.startActivity(intent);

        mUserLoginTask = new UserLoginTask("Rafael","miprimeraprueba");
        mUserLoginTask.execute();

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
            if(user != null) {
                Toast.makeText(getApplicationContext(), "Se recibio", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "No Se recibio", Toast.LENGTH_SHORT).show();
            }
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
