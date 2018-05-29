package com.example.rafa.tfg;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaPass;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.example.rafa.tfg.adapters.usuAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {
    Button btnReg;
    EditText edt_usu;
    EditText edt_pass;
    EditText edt_nombre;
    EditText edt_apellidos;
    EditText edt_email;
    EditText edt_pass_casa;
    private List<CasaPass> listaPass = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnReg = findViewById(R.id.btn_reg_alta);

        edt_usu = findViewById(R.id.edt_usu);
        edt_pass = findViewById(R.id.edt_pass);
        edt_nombre = findViewById(R.id.edt_nombre);
        edt_apellidos = findViewById(R.id.edt_apellidos);
        edt_email = findViewById(R.id.edt_email);
        edt_pass_casa = findViewById(R.id.edt_pass_casa);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(attemptLogin()) {
                    CasaPass casaPass = new CasaPass(edt_pass_casa.getText().toString());
                    listaPass.add(casaPass);
                    RestInterface rest = RestImpl.getRestInstance();
                    Call<Void> restCheckUser = rest.compruebaUser(edt_usu.getText().toString());
                    final Call<Void> restCheckCredentials = rest.addUser(new usuAdapter(edt_usu.getText().toString(), edt_nombre.getText().toString(), edt_apellidos.getText().toString()
                            , edt_pass.getText().toString(), edt_email.getText().toString(), Integer.parseInt(Constantes.PRIMERA_CERO), listaPass)); //TODO
                    restCheckUser.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                restCheckCredentials.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(Registro.this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                                            Snackbar.make(v, "Nuevo dato añadido", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Registro.this.finish();
                                        } else {
                                            Snackbar.make(v, "La clave de producto no es válida", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Snackbar.make(v, "No se a podido realizar la operación", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                });
                            }else{
                                Snackbar.make(v, "El usuario ya existe", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Snackbar.make(v, "No se a podido realizar la operación", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }
            }
        });
    }

    private boolean attemptLogin() {

        boolean res = false;
        // Reset errors.
        edt_usu.setError(null);
        edt_pass.setError(null);
        edt_nombre.setError(null);
        edt_apellidos.setError(null);
        edt_email.setError(null);
        edt_pass_casa.setError(null);


        // Store values at the time of the login attempt.
        String usuario = edt_usu.getText().toString();
        String password = edt_pass.getText().toString();
        String nombre = edt_nombre.getText().toString();
        String apellidos = edt_apellidos.getText().toString();
        String email = edt_email.getText().toString();
        String passCasa = edt_pass_casa.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            edt_pass.setError(getString(R.string.error_field_required));
            focusView = edt_pass;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        else if (!isPasswordValid(password)) {
            edt_pass.setError(getString(R.string.error_password_length));
            focusView =edt_pass;
            cancel = true;
        }
        // Check for a valid usuario
        if (TextUtils.isEmpty(usuario)) {
            edt_usu.setError(getString(R.string.error_field_required));
            focusView = edt_usu;
            cancel = true;
        }
        // Check for a valid nombre
        if (TextUtils.isEmpty(nombre)) {
            edt_nombre.setError(getString(R.string.error_field_required));
            focusView = edt_nombre;
            cancel = true;
        }

        // Check for a valid apellidos
        if (TextUtils.isEmpty(apellidos)) {
            edt_apellidos.setError(getString(R.string.error_field_required));
            focusView = edt_apellidos;
            cancel = true;
        }

        // Check for a valid email
        if (TextUtils.isEmpty(email)) {
            edt_email.setError(getString(R.string.error_field_required));
            focusView = edt_email;
            cancel = true;
        }else if(!validaEmail(email)){
            edt_email.setError(getString(R.string.formato_email));
            focusView = edt_email;
            cancel = true;
        }

        // Check for a valid passCasa
        if (TextUtils.isEmpty(passCasa)) {
            edt_pass_casa.setError(getString(R.string.error_field_required));
            focusView = edt_pass_casa;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            res = true;
        }
        return res;
    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
    private boolean validaEmail(String email){
        boolean res = false;
        int numArr = 0;
        for(int i = 0; i < email.length(); i++){
            if(email.charAt(i) == '@'){
                numArr = 1;
            }
        }
        if(numArr == 1){
            res = true;
        }
        return res;
    }
}