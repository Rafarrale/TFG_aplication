package com.example.rafa.tfg;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;
import com.example.rafa.tfg.adapters.usuAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {
    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnReg = findViewById(R.id.btn_reg_alta);

        final EditText edt_usu = findViewById(R.id.edt_usu);
        final EditText edt_pass = findViewById(R.id.edt_pass);
        final EditText edt_nombre = findViewById(R.id.edt_nombre);
        final EditText edt_apellidos = findViewById(R.id.edt_apellidos);
        final EditText edt_email = findViewById(R.id.edt_email);
        final EditText edt_pass_casa = findViewById(R.id.edt_pass_casa);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                RestInterface rest = RestImpl.getRestInstance();
                Call<Void> restCheckCredentials = rest.addUser(new usuAdapter(edt_usu.getText().toString(),edt_nombre.getText().toString(),edt_apellidos.getText().toString(),edt_pass.getText().toString(),edt_email.getText().toString()));
                restCheckCredentials.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Registro.this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                            Snackbar.make(v, "Nuevo dato añadido", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Registro.this.finish();
                        } else {
                            Snackbar.make(v, "Se ha producido un error al añadir el dato", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Snackbar.make(v, "Se ha producido un Failure", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
    }
}
