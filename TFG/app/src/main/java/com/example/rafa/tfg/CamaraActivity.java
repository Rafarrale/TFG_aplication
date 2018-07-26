package com.example.rafa.tfg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.clases.Constantes;
import com.google.gson.Gson;

    public class CamaraActivity extends Activity {
        WebView browser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_camara);

            // find the WebView by name in the main.xml of step 2
            browser=(WebView)findViewById(R.id.webViewCamera);

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
                    handler.proceed("Rafa", "BeNq_42?");
                }

            });
            // Load the webpage
            browser.loadUrl("https://192.168.2.20:5000/view");

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


    }
