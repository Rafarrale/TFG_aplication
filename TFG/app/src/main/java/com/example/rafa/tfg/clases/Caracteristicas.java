package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 04/03/2018.
 */

public class Caracteristicas implements Parcelable {
    private Temperatura temperatura;

    public Caracteristicas(Temperatura temperatura) {
        this.temperatura = temperatura;
    }

    protected Caracteristicas(Parcel in) {
        temperatura = in.readParcelable(Temperatura.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(temperatura, flags);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Caracteristicas that = (Caracteristicas) o;

        return temperatura != null ? temperatura.equals(that.temperatura) : that.temperatura == null;
    }

    @Override
    public int hashCode() {
        return temperatura != null ? temperatura.hashCode() : 0;
    }
}
