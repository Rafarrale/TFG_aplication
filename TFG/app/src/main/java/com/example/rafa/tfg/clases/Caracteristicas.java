package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Caracteristicas implements Parcelable {
    private Temperatura temperatura;
    private boolean activa;

    public Caracteristicas() {
    }

    public Caracteristicas(Temperatura temperatura, boolean activa) {
        this.temperatura = temperatura;
        this.activa = activa;
    }

    protected Caracteristicas(Parcel in) {
        temperatura = in.readParcelable(Temperatura.class.getClassLoader());
        activa = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(temperatura, flags);
        dest.writeByte((byte) (activa ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Caracteristicas> CREATOR = new Creator<Caracteristicas>() {
        @Override
        public Caracteristicas createFromParcel(Parcel in) {
            return new Caracteristicas(in);
        }

        @Override
        public Caracteristicas[] newArray(int size) {
            return new Caracteristicas[size];
        }
    };

    public Temperatura getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Temperatura temperatura) {
        this.temperatura = temperatura;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
