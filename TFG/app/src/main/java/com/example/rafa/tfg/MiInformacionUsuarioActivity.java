package com.example.rafa.tfg;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.Utilidades;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;

public class MiInformacionUsuarioActivity extends AppCompatActivity {

    TextView nomUsu;
    TextView apellUsu;
    TextView claveCasaActualUsu;
    TextView usu;
    TextView adminUsu;
    TextView emailUsu;
    TextView passUsu;
    usuAdapter usuarioActual;
    ImageView foto_usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_informacion_usuario);
        getSupportActionBar().setTitle(R.string.MisDatos);

        String auxUsuario = (String)Utilidades.sharedPreferencesGet(this, Constantes.PREFS_USUARIO, Constantes.ESTADO_USUARIO);
        CargaImagen cargaImagen = new CargaImagen(this);
        cargaImagen.execute();

        usuarioActual = new Gson().fromJson(auxUsuario, usuAdapter.class);

        nomUsu = findViewById(R.id.tvNomUsuarioInfo);
        apellUsu = findViewById(R.id.tvApellidosUsuarioInfo);
        claveCasaActualUsu = findViewById(R.id.tvUsuarioClaveActual);
        usu = findViewById(R.id.tvUsuarioInfo);
        adminUsu = findViewById(R.id.tvAdminInfo);
        emailUsu = findViewById(R.id.tvEmailInfo);
        passUsu = findViewById(R.id.tvContraseñaInfo);
        foto_usu = findViewById(R.id.circle_image_nav_header_usuario_info);

        StringBuilder usuario = new StringBuilder();
        usuario.append(Constantes.USUARIO);
        usuario.append(Constantes.DOS_PUNTOS_ESPACIO);
        usuario.append(usuarioActual.getUser());

        StringBuilder nom = new StringBuilder();
        nom.append(Constantes.NOMBRE);
        nom.append(Constantes.DOS_PUNTOS_ESPACIO);
        nom.append(usuarioActual.getNombre());

        StringBuilder apell = new StringBuilder();
        apell.append(Constantes.APELLIDOS);
        apell.append(Constantes.DOS_PUNTOS_ESPACIO);
        apell.append(usuarioActual.getApellidos());

        StringBuilder pass = new StringBuilder();
        pass.append(Constantes.CONTRASEÑA_ACCESO);
        pass.append(Constantes.DOS_PUNTOS_ESPACIO);
        pass.append(usuarioActual.getPass());

        StringBuilder claveCasaActual = new StringBuilder();
        claveCasaActual.append(Constantes.CLAVE_CASA);
        claveCasaActual.append(Constantes.DOS_PUNTOS_ESPACIO);
        claveCasaActual.append(usuarioActual.getPassCasa().get(usuarioActual.getKeyToUse()).getKey());

        StringBuilder admin = new StringBuilder();
        admin.append(Constantes.ADMIN);
        admin.append(Constantes.DOS_PUNTOS_ESPACIO);
        admin.append(usuarioActual.getAdmin());

        StringBuilder email = new StringBuilder();
        email.append(Constantes.EMAIL);
        email.append(Constantes.DOS_PUNTOS_ESPACIO);
        email.append(usuarioActual.getEmail());

        nomUsu.setText(nom);
        apellUsu.setText(apell);
        claveCasaActualUsu.setText(claveCasaActual);
        usu.setText(usuario);
        adminUsu.setText(admin);
        emailUsu.setText(email);
        passUsu.setText(pass);

    }

    protected class CargaImagen extends AsyncTask<Void, Void, Bitmap> {
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
                String bytes = imagenes.get(usuarioActual.getNombre());
                if( bytes!= null){
                    byte[] imageAsBytes = Base64.decode(bytes.getBytes(), Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                }
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            foto_usu.setImageBitmap(bitmap);
        }
    }
}
