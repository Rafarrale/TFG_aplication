package com.example.rafa.tfg;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.example.rafa.tfg.clases.ImageSaver;
import com.example.rafa.tfg.clases.Save;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;

public class CamaraActivity extends Activity {
        WebView browser;
        ImageView galeria;
        TextView tvHabCamara;
        private static final int PICK_IMAGE = 100;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebView.enableSlowWholeDocumentDraw();
            }
            setContentView(R.layout.activity_camara);

            String jsonValue = getIntent().getStringExtra(Constantes.DISP_SELECCIONADO);
            DispositivosAdapter dispositivo = new Gson().fromJson(jsonValue, DispositivosAdapter.class);


            // find the WebView by name in the main.xml of step 2
            browser=(WebView)findViewById(R.id.webViewCamera);
            galeria = findViewById(R.id.imageViewCamera);
            tvHabCamara = findViewById(R.id.tvHabitCamara);
            tvHabCamara.setText(dispositivo.getHabitacion());
            // Enable javascript
            browser.getSettings().setJavaScriptEnabled(true);

            // Set WebView client
            browser.setWebChromeClient(new WebChromeClient());

            browser.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override   // TODO: Cambiar a un certificado real
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                    handler.proceed(Constantes.URL_USER, Constantes.URL_PASS);
                }

            });

            String ultimoFichero = new ImageSaver(this).ultimoFichero();

            Bitmap lastBitmap = new ImageSaver(this).
                    setFileName(ultimoFichero
                    ).
                    setExternal(true).
                    setDirectoryName("MotiCasa").
                    load();

            galeria.setImageBitmap(lastBitmap);

            // Load the webpage
            browser.loadUrl(Constantes.URL_CAMARA + dispositivo.get_id());

            galeria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
            });

/* //OPTIONAL Lanza el enlace al navegador
        Uri uri = Uri.parse("https://192.168.2.20:5000/video_feed");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
*/
    }

        @Override
        public void onBackPressed() {
            finish();
            browser.stopLoading();
        }

        public void onClickTakePhoto(View view){
            Bitmap foto = screenshot(browser,0);
            galeria.setImageBitmap(foto);
            permisos();

            //guardar imagen Save.class
            Save savefile = new Save();
            savefile.SaveImage(getApplicationContext(), foto);

            /*
            //guardar imagen ImageSaver.class
            new ImageSaver(this).
                    setFileName("myImage.png").
                    setExternal(true).
                    setDirectoryName("MotiCasa").
                    save(foto);
             */
        }

        /**
         * WevView screenshot
         *
         * @param webView
         * @return
         */
        public static Bitmap screenshot(WebView webView, float scale11) {
            try {
                float scale = webView.getScale();
                int height = (int) (webView.getContentHeight() * scale + 0.5);
                Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void permisos(){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }
