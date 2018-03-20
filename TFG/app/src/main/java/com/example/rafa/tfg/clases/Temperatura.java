package com.example.rafa.tfg.clases;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafael on 19/03/2018.
 */

public class Temperatura implements Parcelable {
    private String temperatura;
    private String humedad;
    private String tipo;
    private String intensidad;

    public Temperatura(String temperatura, String humedad, String tipo, String intensidad) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.tipo = tipo;
        this.intensidad = intensidad;
    }

    protected Temperatura(Parcel in) {
        temperatura = in.readString();
        humedad = in.readString();
        tipo = in.readString();
        intensidad = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(temperatura);
        dest.writeString(humedad);
        dest.writeString(tipo);
        dest.writeString(intensidad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Temperatura> CREATOR = new Creator<Temperatura>() {
        @Override
        public Temperatura createFromParcel(Parcel in) {
            return new Temperatura(in);
        }

        @Override
        public Temperatura[] newArray(int size) {
            return new Temperatura[size];
        }
    };

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperatura that = (Temperatura) o;

        if (temperatura != null ? !temperatura.equals(that.temperatura) : that.temperatura != null)
            return false;
        if (humedad != null ? !humedad.equals(that.humedad) : that.humedad != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;
        return intensidad != null ? intensidad.equals(that.intensidad) : that.intensidad == null;
    }

    @Override
    public int hashCode() {
        int result = temperatura != null ? temperatura.hashCode() : 0;
        result = 31 * result + (humedad != null ? humedad.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (intensidad != null ? intensidad.hashCode() : 0);
        return result;
    }
}
