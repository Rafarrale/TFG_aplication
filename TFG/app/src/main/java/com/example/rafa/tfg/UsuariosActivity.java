package com.example.rafa.tfg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.CasaAdapterView;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.adapters.usuDataAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Utilidades;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Rafael on 13/03/2018.
 */


public class UsuariosActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private usuDataAdapter usuDataAdapter;
    private RecyclerView mUsuariosRecycler;
    private usuariosExecute usuariosExecute;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_usuarios);
        //Inicialización RecyclerView
        mUsuariosRecycler = findViewById(R.id.recyclerUsuarios);

        usuDataAdapter = new usuDataAdapter(this, new ArrayList<usuAdapter>(0));
        usuDataAdapter.setOnItemClickListener(new usuDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(usuAdapter clickedAppointment) {
            //    Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelAppointment(usuAdapter canceledAppointment) {
            //    Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onModAppointment(usuAdapter ModAppointment) {
            //    Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();

                final usuAdapter res = ModAppointment;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UsuariosActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_usuario,null);
                final EditText edtUsuAct = mView.findViewById(R.id.tvUsuarioActualiza);
                final EditText edtNomAct = mView.findViewById(R.id.tvNombreUsuarioActualiza);
                final EditText edtPassAct = mView.findViewById(R.id.tvPasswordActualiza);
                final RadioButton rbAdmnActSi = mView.findViewById(R.id.rbAdminUsuSi);
                final RadioButton rbAdmnActNo = mView.findViewById(R.id.rbAdminUsuNo);
                final EditText edtEmailAct = mView.findViewById(R.id.tvEmailActualiza);
                Button btActualiza = mView.findViewById(R.id.btUsuActualiza);

                if(ModAppointment.getAdmin().equals(Constantes.SI)){
                    rbAdmnActSi.setChecked(true);
                }else if(ModAppointment.getAdmin().equals(Constantes.NO)){
                    rbAdmnActNo.setChecked(true);
                }


                edtUsuAct.setText(ModAppointment.getUser());
                edtNomAct.setText(ModAppointment.getNombre());
                edtPassAct.setText(ModAppointment.getPass());
                edtEmailAct.setText(ModAppointment.getEmail());

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                rbAdmnActNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rbAdmnActSi.isChecked()){
                            rbAdmnActSi.setChecked(false);
                            res.setAdmin(Constantes.NO);
                        }

                        if(Utilidades.radioButton == false){
                            Utilidades.radioButton = true;
                            rbAdmnActSi.setChecked(false);
                            res.setAdmin(Constantes.NO);
                        }
                    }
                });

                rbAdmnActSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rbAdmnActNo.isChecked()){
                            rbAdmnActNo.setChecked(false);
                            res.setAdmin(Constantes.SI);
                        }
                        if(Utilidades.radioButton == true){
                            Utilidades.radioButton = false;
                            rbAdmnActNo.setChecked(false);
                            res.setAdmin(Constantes.SI);
                        }
                    }
                });

                btActualiza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsuariosActivity.this, "Por favor rellena el campo vacío", Toast.LENGTH_SHORT).show();
                        if (!edtUsuAct.getText().toString().isEmpty()) {
                            res.setUser(edtUsuAct.getText().toString());
                            res.setNombre(edtNomAct.getText().toString());
                            res.setPass(edtPassAct.getText().toString());
                            res.setEmail(edtEmailAct.getText().toString());
                            //compruebaHomeUsu(mNombreCasa.getText().toString());
                        } else {
                            Toast.makeText(UsuariosActivity.this, "Por favor rellena el campo vacío", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        mUsuariosRecycler.setAdapter(usuDataAdapter);

        mUsuariosRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_usuarios);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                usuariosExecute = new usuariosExecute();
                usuariosExecute.execute();
            }
        });
        usuariosExecute = new usuariosExecute();
        usuariosExecute.execute();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }


    public void refrescaEstaPagina(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    protected class usuariosExecute extends AsyncTask<Void,Void,ArrayList<usuAdapter>>{

        @Override
        protected ArrayList<usuAdapter> doInBackground(Void... voids) {
            ArrayList<usuAdapter> res = new ArrayList<usuAdapter>();

            RestInterface rest = RestImpl.getRestInstance();
            Call<ArrayList<usuAdapter>> restUsu = rest.getTodosUsuarios();

            try{
                Response<ArrayList<usuAdapter>> responseUsuarios = restUsu.execute();
                if(responseUsuarios.isSuccessful()){
                    res = responseUsuarios.body();

                }

            }catch(IOException io){

            }

            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<usuAdapter> usuAdapters) {
            super.onPostExecute(usuAdapters);
            usuDataAdapter.swapItems(usuAdapters);
            swipeRefreshLayout.setRefreshing(false);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "No se ha podido recuperar la información", Toast.LENGTH_SHORT).show();

        }
    }
}
