package com.example.rafa.tfg.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rafa.tfg.clases.Configuracion;
import com.google.gson.Gson;

import java.util.Objects;

/**
 * Created by Rafael on 04/03/2018.
 */

public class CasaAdapterIni implements Parcelable {
    private String _id;
    private String homeUsu;
    private String passCasa;
    private Configuracion configuracion;


    public CasaAdapterIni(String homeUsu){
        this.homeUsu = homeUsu;
    }

    public CasaAdapterIni(String homeUsu, String passCasa){
        this.homeUsu = homeUsu;
        this.passCasa = passCasa;

    }

    protected CasaAdapterIni(Parcel in) {
        _id = in.readString();
        homeUsu = in.readString();
        passCasa = in.readString();
        configuracion = in.readParcelable(Configuracion.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(homeUsu);
        dest.writeString(passCasa);
        dest.writeParcelable(configuracion, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CasaAdapterIni> CREATOR = new Creator<CasaAdapterIni>() {
        @Override
        public CasaAdapterIni createFromParcel(Parcel in) {
            return new CasaAdapterIni(in);
        }

        @Override
        public CasaAdapterIni[] newArray(int size) {
            return new CasaAdapterIni[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHomeUsu() {
        return homeUsu;
    }

    public void setHomeUsu(String homeUsu) {
        this.homeUsu = homeUsu;
    }

    public String getPassCasa() {
        return passCasa;
    }

    public void setPassCasa(String passCasa) {
        this.passCasa = passCasa;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaAdapterIni that = (CasaAdapterIni) o;
        return Objects.equals(_id, that._id) &&
                Objects.equals(homeUsu, that.homeUsu) &&
                Objects.equals(passCasa, that.passCasa) &&
                Objects.equals(configuracion, that.configuracion);
    }

    @Override
    public int hashCode() {

        return Objects.hash(_id, homeUsu, passCasa, configuracion);
    }

    public static  usuAdapter buildFromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString,usuAdapter.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
