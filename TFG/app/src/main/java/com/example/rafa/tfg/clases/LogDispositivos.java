package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class LogDispositivos implements Parcelable{

    private String fecha;
    private String hora;
    private String suceso;

    public LogDispositivos(String fecha, String hora, String suceso) {
        this.fecha = fecha;
        this.hora = hora;
        this.suceso = suceso;
    }

    protected LogDispositivos(Parcel in) {
        fecha = in.readString();
        hora = in.readString();
        suceso = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(suceso);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogDispositivos> CREATOR = new Creator<LogDispositivos>() {
        @Override
        public LogDispositivos createFromParcel(Parcel in) {
            return new LogDispositivos(in);
        }

        @Override
        public LogDispositivos[] newArray(int size) {
            return new LogDispositivos[size];
        }
    };

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSuceso() {
        return suceso;
    }

    public void setSuceso(String suceso) {
        this.suceso = suceso;
    }
}
