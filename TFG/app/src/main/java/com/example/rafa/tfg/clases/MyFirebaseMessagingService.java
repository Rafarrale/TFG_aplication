package com.example.rafa.tfg.clases;

/**
 * Created by Rafael on 02/04/2018.
 */

import com.example.rafa.tfg.MainActivity;
import com.example.rafa.tfg.NavPrincActivity;
import com.example.rafa.tfg.adapters.NotificacionDispHora;
import com.google.firebase.messaging.FirebaseMessagingService;


        import android.content.Intent;
        import android.util.Log;
        import com.google.firebase.messaging.RemoteMessage;

        import org.json.JSONException;
        import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static com.example.rafa.tfg.clases.Constantes.*;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            /** Creamos los mensajes de notificacion y los añadimos a una lista de notificaciones*/
            MensajesNotificacionesActivity(title, message);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void MensajesNotificacionesActivity(String title, String message) {
        /** Cambiamos el valor de la variable badge para el contador*/
        Utilidades.badge++;
        String[] auxTitle;
        String[] auxMessage;
        String estado;
        String casa;
        String sensor;
        String habitacion;
        String hora;

        auxTitle = title.split(ESPACIO);
        auxMessage = message.split(ESPACIO);

        estado = auxTitle[VALUE_0];
        casa = auxTitle[VALUE_1];
        sensor = auxMessage[VALUE_1];
        habitacion = auxMessage[VALUE_5];

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss 'del' dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        hora = dateFormat.format(date);

        NotificacionDispHora notificacionDispHora = new NotificacionDispHora();
        notificacionDispHora.setEstado(estado);
        notificacionDispHora.setCasa(casa);
        notificacionDispHora.setName(sensor);
        notificacionDispHora.setHabitacion(habitacion);
        notificacionDispHora.setHora(hora);

        Utilidades.listNotificaciones.add(0, notificacionDispHora);
        if(Utilidades.listNotificaciones.size() > 50){
            Utilidades.listNotificaciones.remove(Utilidades.listNotificaciones.size() - 1);
        }
    }

}