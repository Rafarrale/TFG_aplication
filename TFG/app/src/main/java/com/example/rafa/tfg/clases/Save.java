package com.example.rafa.tfg.clases;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {
    public static final String TAG = "logcat";
    private Context TheThis;
    private String NameOfFolder = "/MotiCasa";
    private String NameOfFile = "imagen";
    File directorio;

    public void SaveImage(Context context, Bitmap ImageToSave) {
        TheThis = context;
        if(isExternalStorageWritable()){
            Log.d(TAG, "El almacenamiento externo esta disponible :)");
        }else{
            Log.e(TAG, "El almacenamiento externo no esta disponible :(");
        }
        crearDirectorioPublico(NameOfFolder);
        String CurrentDateAndTime = getCurrentDateAndTime();
        File file = new File(directorio, NameOfFile + CurrentDateAndTime + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }
        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }
    }
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }
    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public File crearDirectorioPublico(String nombreDirectorio) {
        //Crear directorio público en la carpeta Pictures.
        directorio = new File(Environment.getExternalStorageDirectory().toString(), nombreDirectorio);
        if (!directorio.isDirectory()) {
            //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
            if (!directorio.mkdir())
                Log.e(TAG, "Error: No se creo el directorio público");
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
        }
        return directorio;
    }
}